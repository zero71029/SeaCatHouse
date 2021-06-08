package tw.com.iii.OceanCatHouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;


import tw.com.iii.OceanCatHouse.model.UserBean;
import tw.com.iii.OceanCatHouse.model.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping("/signup")
	public String signup(UserBean bean, BindingResult bindingResult, Model model, Locale locale) {
		System.out.println("*****signup******");
		System.out.println(bean);
		// 接收資料
		// 轉換資料
		Map<String, String> errors = new HashMap<>();
		model.addAttribute("errors", errors);

		if (bindingResult != null && bindingResult.hasFieldErrors()) {
			if (bindingResult.hasFieldErrors("Email")) {
				errors.put("Email", messageSource.getMessage("product.id.format", null, locale));
			}
			if (bindingResult.hasFieldErrors("Username")) {
				errors.put("Username", messageSource.getMessage("product.price.format", null, locale));
			}
			if (bindingResult.hasFieldErrors("Userpassword")) {
				errors.put("UserPassword", messageSource.getMessage("product.make.format", null, locale));
			}

		}
//		//驗證資料
//				if(prodaction!=null) {
//					if(prodaction.equals("Insert") || prodaction.equals("Update") || prodaction.equals("Delete")) {
//						if(bean==null || bean.getId()==null) {
//							errors.put("id",
//									messageSource.getMessage("product.id.required", new String[] {prodaction}, locale));
//						}
//					}
//				}
//				
//				if(errors!=null && !errors.isEmpty()) {
//					return "/pages/product";
//				}
//				
//		//呼叫Model，根據Model執行結果導向View
//				if(prodaction!=null && prodaction.equals("Select")) {
//					List<ProductBean> result = productService.select(bean);
//					System.out.println(result);
//					model.addAttribute("select", result);
//					return "/pages/display";
//				} else if(prodaction!=null && prodaction.equals("Insert")) {
//					ProductBean result = productService.insert(bean);
//					if(result==null) {
//						errors.put("action", "Insert fail");
//					} else {
//						model.addAttribute("insert", result);
//					}
//					return "/pages/product";
//				} else if(prodaction!=null && prodaction.equals("Update")) {
//					ProductBean result = productService.update(bean);
//					if(result==null) {
//						errors.put("action", "Update fail");
//					} else {
//						model.addAttribute("update", result);
//					}
//					return "/pages/product";
//				} else if(prodaction!=null && prodaction.equals("Delete")) {
//					boolean result = productService.delete(bean);
//					if(!result) {
//						model.addAttribute("delete", 0);
//					} else {
//						model.addAttribute("delete", 1);
//					}
//					return "/pages/product";
//				} else  {
//					errors.put("action", "Unknown Action:"+prodaction);
//					return "/pages/product";
//				}
		return "redirect:/views/signup.jsp";

	}
}