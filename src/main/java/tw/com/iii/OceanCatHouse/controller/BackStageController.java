package tw.com.iii.OceanCatHouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.com.iii.OceanCatHouse.model.OrderDetailBean;
import tw.com.iii.OceanCatHouse.model.OrdersBean;
import tw.com.iii.OceanCatHouse.model.ProductBean;
import tw.com.iii.OceanCatHouse.repository.OrderDetailRepository;
import tw.com.iii.OceanCatHouse.repository.OrdersRepository;
import tw.com.iii.OceanCatHouse.repository.ProductRepository;
import tw.com.iii.OceanCatHouse.repository.service.ProductService;

import java.util.*;

@Controller
@RequestMapping("/backstage")

public class BackStageController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @RequestMapping("/home")
    public String home(){
        return "/views/backstage/backstage";
    }

    @RequestMapping("/time")
    public String tiem(){
        return "views/backstage/time";
    }


    @RequestMapping("/product")
    public String prod(){
        return "views/backstage/product";
    }
    @RequestMapping("/order")
    public String order(){
        return "views/backstage/order";
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//讀取商品資訊 和分頁
    @RequestMapping("/product/{pag}")
    @ResponseBody
    public List<ProductBean> product(@PathVariable("pag") Integer p){
        System.out.println("*****讀取商品資訊 *****");
        Page<ProductBean> page = productRepository.findAll(PageRequest.of(p-1, 20));
        List<ProductBean> result = page.getContent();
        return result;
    }
    //讀取最多頁數
    @RequestMapping("/product/data/{page}")
    @ResponseBody
    public Integer page(@PathVariable("page") Integer p){
        System.out.println("*****讀取最多頁數 *****");
        Page<ProductBean> page = productRepository.findAll(PageRequest.of(p-1, 20));
        Integer result = page.getTotalPages();
        return result;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//修改商品
    @RequestMapping("/updata/{productid}")
    public String updata(ProductBean bean, @PathVariable("productid") Integer productid,Model model){
        System.out.println("*****修改商品 *****");
        System.out.println(bean);
        bean.setProductid(productid);
        productService.insert(bean);
        model.addAttribute("pag",1);
        return "/views/backstage/product";
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//新增商品
    @RequestMapping("/updata/")
    public String updata(ProductBean bean, Model model){
        System.out.println("*****修改商品 *****");
        //使有輸入的資料能返回
        model.addAttribute("productmodel", bean.getProductmodel());
        model.addAttribute("productname", bean.getProductname());
        model.addAttribute("producttext", bean.getProducttext());
        model.addAttribute("purchaseprice", bean.getPurchaseprice());
        model.addAttribute("sellingprice", bean.getSellingprice());
        model.addAttribute("stocks", bean.getStocks());
        model.addAttribute("productspecifications", bean.getProductspecifications());
        model.addAttribute("vendorid", bean.getVendorid());
        model.addAttribute("productcategoryid", bean.getProductcategoryid());
        model.addAttribute("productstatus", bean.getStocks());
        // 判斷欄位輸入
        Map<String, String> errors = new HashMap<>();
        model.addAttribute("errors", errors);

        if (bean.getProductmodel() == null || bean.getProductmodel().length() == 0) {
                errors.put("productmodel", "需要商品號");
        }
        if (bean.getProductname() == null || bean.getProductname().length() == 0) {
                errors.put("productname", "需要名稱");
        }
        if (bean.getProducttext() == null || bean.getProducttext().length() == 0) {
                errors.put("producttext", "需要詳細描述");
        }
        if (bean.getPurchaseprice() == null || bean.getPurchaseprice() == 0) {
                errors.put("purchaseprice", "需要進價");
        }
        if (bean.getSellingprice() == null || bean.getSellingprice() == 0) {
                errors.put("sellingprice", "需要售價");
        }
        if (bean.getStocks() == null || bean.getStocks() == 0) {
                errors.put("stocks", "需要庫存量");
        }
        if (bean.getProductspecifications() == null || bean.getProductspecifications().length() == 0) {
                errors.put("productspecifications", "需要商品規格");
        }
        if (bean.getVendorid() == null || bean.getVendorid() == 0) {
                errors.put("vendorid", "需要廠商號");
        }
        if (bean.getProductcategoryid() == null || bean.getProductcategoryid() == 0) {
                errors.put("productcategoryid", "需要商品總類");
        }
        if (bean.getStocks() == null || bean.getStocks() == 0) {
                          errors.put("productstatus", "需要狀態");
        }

        if (errors != null && !errors.isEmpty()) return "/views/backstage/product";


        System.out.println(bean);
        productService.insert(bean);
        return "/views/backstage/product";
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //讀取商品資訊 和分頁
    @RequestMapping("/selectproduct/{name}")
    @ResponseBody
    public List<ProductBean> product(@PathVariable("name") String name){
        System.out.println("*****搜索商品資訊 *****");

        List<ProductBean> result = productRepository.findByProductnameLike("%"+name+"%");
        return result;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //讀取訂單
    @RequestMapping("/selectorder")
    @ResponseBody
    public List<OrdersBean> selectorder(){
        System.out.println("*****搜索訂單資訊 *****");

        List<OrdersBean> result = ordersRepository.findAll();
        System.out.println(result);
        return result;
    }
    //訂單細節


    @RequestMapping("/orderDetail/{id}")
    @ResponseBody
    public List<Map<String, String>> orderDetail(@PathVariable("id") Integer orderid){
        System.out.println("*****搜索訂單細節 *****");
        List<OrderDetailBean> list =  orderDetailRepository.findByorderId(orderid);
        List<Map<String, String>> result = new ArrayList<>();
        for(OrderDetailBean odb : list){
            Map<String, String> map =new HashMap<>();
            map.put("orderId", odb.getOrderId()+"");
            Optional<ProductBean> op = productRepository.findById(odb.getProductId());
            ProductBean b = op.get();
            map.put("productname", b.getProductname());
            map.put("SellingPrice",b.getSellingprice()+"");
            map.put("Unit" , odb.getUnit()+"");
            result.add(map);
        }

        System.out.println(result);
        return result;
    }



}

