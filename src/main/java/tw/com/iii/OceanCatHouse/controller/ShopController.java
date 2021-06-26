package tw.com.iii.OceanCatHouse.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.iii.OceanCatHouse.Tool.ZeroTools;
import tw.com.iii.OceanCatHouse.model.*;
import tw.com.iii.OceanCatHouse.repository.*;
import tw.com.iii.OceanCatHouse.repository.service.OrdersService;


@Controller
public class ShopController {

    @Autowired
    private UserPaymentMethodRepository userPaymentMethodDao;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ZeroTools zTools;
    @Autowired
    private RecipeMaterialRepository recipeMaterialRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @RequestMapping(path = {"/views/ShoppingMall"})
    public String ShoppingMall() {
        return "views/shop/ShoppingMall";
    }

    @RequestMapping(
            path = {"/complete"}
    )
    public String complete() {
        return "/views/shop/complete";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 購物車 處理
    @SuppressWarnings("finally")
    @RequestMapping("/cat/{id}")
    public String cat(@PathVariable("id") Integer id, @RequestParam("num") Integer num, HttpSession session,
                      Model model) {
        System.out.println("*****cat*****" + id);
        Optional<ProductBean> op = productRepository.findById(id);
        ProductBean bean = op.get();
        model.addAttribute("productname", bean.getProductname());
        model.addAttribute("producttext", bean.getProducttext());
        model.addAttribute("sellingprice", bean.getSellingprice());
        model.addAttribute("productspecifications", bean.getProductspecifications());
        model.addAttribute("id", id);

        Map<String, Integer> cat = new HashMap<>();
        // 取資料
        Map<String, Integer> icat = (Map<String, Integer>) session.getAttribute("cat");
        if (icat != null)
            cat = icat;
        Integer SSS = null;// 紀錄的數量

        try {
            // 判斷有沒有當筆資料
            SSS = (Integer) icat.get(Integer.toString(id));
//            System.out.println("SSS" + SSS);
            num = SSS + num;
        } catch (Exception e) {
//			沒資料 把數量加入
            System.out.println(e);
        } finally {
            System.out.println("num:" + num);
            cat.put(Integer.toString(id), num);
            session.setAttribute("cat", cat);
            return "redirect:/Details/" + id;
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 商品細節資料
    @RequestMapping("/Details/{id}")
    public String Details(@PathVariable("id") Integer id, Model model) {
        System.out.println("/Details******************************************************************" + id);
        Optional<ProductBean> op = productRepository.findById(id);
        ProductBean bean = op.get();
        model.addAttribute("productname", bean.getProductname());
        model.addAttribute("producttext", bean.getProducttext());
        model.addAttribute("sellingprice", bean.getSellingprice());
        model.addAttribute("productspecifications", bean.getProductspecifications());
        model.addAttribute("id", bean.getProductid());

        // 同類商品
        Page<ProductBean> productBeanPage = productRepository.findByProductcategoryid(bean.getProductcategoryid(), PageRequest.of(0, 4));
        System.out.println(productBeanPage.getContent());
        model.addAttribute("SimilarProducts",productBeanPage.getContent());
        // 推薦食譜
        System.out.println("********************推薦食譜*********************" );
        Page<Integer> page = recipeMaterialRepository.findRecId(bean.getProductkey(),PageRequest.of(0, 6));
        int rand = (int) (Math.random()*page.getTotalPages());
        page = recipeMaterialRepository.findRecId(bean.getProductkey(),PageRequest.of(rand, 6));
        System.out.println("page : "+ page);
        if(page.getNumberOfElements() == 0){
            page = recipeMaterialRepository.findRecId("醬油",PageRequest.of(rand, 6));
        }
        List<Integer> set = page.getContent();
        System.out.println(set+" set ");
        List<RecipeMainBean> result = new ArrayList<>();
        for(Integer i : set){
            Optional<RecipeMainBean> recipeOP =  recipeRepository.findById(i);
            System.out.println(" recipeOP "+recipeOP);
            RecipeMainBean recipe = recipeOP.get();
            System.out.println(" recipeOP "+recipe);
            result.add(recipe);
        }
        model.addAttribute("recommend", result);

        return "views/shop/Details";
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //去輸入位置 資料轉換
    @RequestMapping("/toAddress")
    public String toAddress(HttpSession session,Model model) {
        System.out.println("*****toAddress*****");
        UserBean user = (UserBean) session.getAttribute("user");
        if(session.getAttribute("user") == null){
            model.addAttribute("id",0);}
        else {
            model.addAttribute("id", user.getUserid());
        }
        Map<String, Integer> cat = (Map<String, Integer>) session.getAttribute("cat");
        System.out.println(cat);

        if(cat == null ||cat.size() == 0){
            model.addAttribute("cat",0);}
        else {
            model.addAttribute("cat",  session.getAttribute("cat"));
        }
        if(session.getAttribute("state") == null){
            model.addAttribute("state",0);}
        else {
            model.addAttribute("state",session.getAttribute("state"));
        }
        // Jian新增, 付款方式呈現
        List<UserCreditCardBean> userCreditCardBeans = user.getUserCreditCardBeans();
        System.out.println(userCreditCardBeans);
        if(userCreditCardBeans != null){
            for (UserCreditCardBean uccb : userCreditCardBeans){
                // UseCard 等於1代表上次結帳用這張
                if(uccb.getUseCard() != null && uccb.getUseCard() == 1){
                    model.addAttribute("uccb", uccb);
                    System.out.println(uccb);
                    break;
                }
            }
        }
        return "/views/shop/address";
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 存訂單
    @RequestMapping("/saveOrder/{id}")
    public String saveOrder(@PathVariable("id") Integer userId, @RequestParam("address") String address,HttpSession session,Model model,@RequestParam("g-recaptcha-response") String token) {
        System.out.println("*****存訂單*****");
        OrdersBean bean = new OrdersBean();
        //儲存錯誤
        Map<String, String> errors = new HashMap<>();
        model.addAttribute("errors", errors);
        // 機器人判斷
        if (token == "" || !zTools.recaptcha(token)) {
            System.out.println("errors.put(\"recaptcha\", \"需要驗證\");");
            errors.put("recaptcha", "需要驗證");
        }
        // 地址判斷
        if (address == null || address.length() == 0) {
            errors.put("address", "需要輸入地址");
        }
            if (errors != null && !errors.isEmpty())
                return "views/shop/address";
        //取得購物車內容
        Map<String, Integer> cat = (Map<String, Integer>) session.getAttribute("cat");
        if (cat != null){
//          OrdersBean save =  ordersService.insert( userId , cat);
            System.out.println("*****存訂單成功*****");
            cat.clear();
//            model.addAttribute("orderId", save.getOrderId());
              session.setAttribute("orderId", 18);
            session.setAttribute("address", "台中市南屯區公益路二段51號18樓");
        }
        return "redirect:/complete";
    }

}
