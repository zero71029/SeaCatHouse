package tw.com.iii.OceanCatHouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import tw.com.iii.OceanCatHouse.model.*;
import tw.com.iii.OceanCatHouse.repository.RecipeMaterialRepository;
import tw.com.iii.OceanCatHouse.repository.RecipeStepRepository;
import tw.com.iii.OceanCatHouse.repository.service.RecipeCategoryService;
import tw.com.iii.OceanCatHouse.repository.service.RecipeMainService;
import tw.com.iii.OceanCatHouse.repository.service.RecipeMaterialService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/createRecipe")

public class CreateRecipeController {

    @Autowired
    private RecipeCategoryService recipeCategoryService;

    @Autowired
    private RecipeMainService recipeMainService;

    @Autowired
    private RecipeMaterialRepository materialRepositoryDao;

    @Autowired
    private RecipeStepRepository recipeStepDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");


    @PostMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView save(HttpSession session,
                             MultipartHttpServletRequest request) throws ParseException {
        // 獲取檔案文件
        String recipeDetail = request.getParameter("recipeDetail");
        Map<String, MultipartFile> fileMap = request.getFileMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String recCreated = simpleDateFormat.format(new Date());
        Map<String, Object> map =null;
        try {
            // 解析JSON資料
            ObjectMapper om = new ObjectMapper();
            map = om.readValue(recipeDetail, new TypeReference<HashMap<String, Object>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        UserBean user = (UserBean) session.getAttribute("user");
        RecipeMainBean main = (RecipeMainBean) session.getAttribute("main");
        RecipeMainBean recipeMainBean = new RecipeMainBean();
        recipeMainBean.setCategoryId(Integer.parseInt((String)map.get("CategoryId")));
        recipeMainBean.setRecTitle((String)map.get("RecTitle"));
        recipeMainBean.setRecText((String)map.get("RecText"));
        recipeMainBean.setRecTime((String)map.get("RecTime"));
        recipeMainBean.setRecNum((String)map.get("RecNum"));
        recipeMainBean.setRecCreated(recCreated);
        recipeMainBean.setUserId(user.getUserid());

        // 圖片儲存
        // 1. 改名
        String format = sdf.format(new Date())+".jpg";
        try {
            // 2. 儲存圖片到資料夾
            if(fileMap.get("fileMain") != null){
                fileMap.get("fileMain").transferTo(
                        new File("/Users/louisjian/大專/OceanCatHouse/src/main/resources/static/images/mainpic/"+format));
            // 3. 儲存檔案名稱到資料庫
            recipeMainBean.setRecPic(format);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 這裡判斷是更新食譜, 還是新增食譜
        Boolean isUpdate = false;
        RecipeMainBean mainBean;
        Integer recid = 0;
        if(main != null){
            isUpdate = true;
            recid = main.getRecId();
            recipeMainBean.setRecId(recid);
            mainBean = recipeMainService.insert(recipeMainBean);
        }else {
            mainBean = recipeMainService.insert(recipeMainBean);
            recid = mainBean.getRecId();
        }
        // 刪除資料夾舊封面照片
        if(isUpdate){
            FileSystemUtils.deleteRecursively(new File("/Users/louisjian/大專/OceanCatHouse/src/main/resources/static/images/mainpic/" + main.getRecPic()));
        }
        // 刪除舊食材表
        RecipeMaterialBean materialBeanList = (RecipeMaterialBean) session.getAttribute("materialList");
        if(materialBeanList != null){
            materialRepositoryDao.deleteAllByRecId(main.getRecId());
        }
        // 食材表 儲存資料庫
        RecipeMaterialBean recipeMaterialBean ;
        for(Map<String, String> food : (ArrayList<Map<String, String>>)(map.get("foodsArrayList"))){
            recipeMaterialBean = new RecipeMaterialBean();
            recipeMaterialBean.setMaterialName(food.get("MaterialName"));
            recipeMaterialBean.setUnitNum(food.get("UnitNum"));
            recipeMaterialBean.setGp("食材");
            recipeMaterialBean.setRecId(recid);
            materialRepositoryDao.save(recipeMaterialBean);
        }
        // 刪除步驟資料夾的舊照片
        List<RecipeStepBean> stepBeanList = (List<RecipeStepBean>) session.getAttribute("stepList");
        if(stepBeanList != null){
            for(int i=0;i<stepBeanList.size();i++){
                if(stepBeanList.get(i) != null && stepBeanList.get(i).getStepPic() != null) {
                    FileSystemUtils.deleteRecursively(new File("/Users/louisjian/大專/OceanCatHouse/src/main/resources/static/images/stepPic/" + stepBeanList.get(i).getStepPic()));
                }
            }
            // 刪除舊的步驟表
            recipeStepDao.deleteAllByRecId(main.getRecId());
        }
        // 步驟表 儲存資料庫(步驟, 步驟說明)
        RecipeStepBean recipeStepBean = null;
        String stepPicName = null;
        List<String> stepList = (List<String>)(map.get("StepTextArray"));
        for(int i=0;i<stepList.size();i++){
            recipeStepBean = new RecipeStepBean();
            recipeStepBean.setStep(String.valueOf(i+1));
            recipeStepBean.setStepText(stepList.get(i));
            if (fileMap.get("file"+i) != null){
                try {
                    stepPicName = sdf.format(new Date())+".jpg";
                    fileMap.get("file"+i).transferTo(
                            new File("/Users/louisjian/大專/OceanCatHouse/src/main/resources/static/images/stepPic/"+i+stepPicName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            recipeStepBean.setStepPic(stepPicName);
            recipeStepBean.setRecId(recid);
            recipeStepDao.save(recipeStepBean);
        }
        System.out.println("mainBean:"+mainBean);
        return null;
    }

    // 新增食譜 詳細頁
    @GetMapping("/add")
    public ModelAndView add(@RequestParam("RecTitle") String RecTitle,
                            @RequestParam("CategoryId") String CategoryId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("CategoryId", CategoryId);
        modelAndView.addObject("RecTitle", RecTitle);
        modelAndView.setViewName("views/user/createRecipeDetail");
        System.out.println(CategoryId);

        return modelAndView;
    }

    // 新增食譜 分類頁
    @RequestMapping("/start")
    public ModelAndView createRecipeDetail(){
        ModelAndView modelAndView = new ModelAndView();
        List<RecipeCategoryBean> categoryList = recipeCategoryService.list();
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.setViewName("/views/user/createRecipe");
        System.out.println(categoryList);

        return modelAndView;
    }
}
