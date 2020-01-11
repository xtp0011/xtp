package com.imooc.sell.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.from.CategoryFrom;
import com.imooc.sell.service.CategroyService;

import lombok.extern.slf4j.Slf4j;

/**
 * 卖家类目
 * @author 徐太平
 *
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellCategroyController {
	
	@Autowired
	private CategroyService categroyService;
	
	@GetMapping("/list")
	public ModelAndView list(Map<String,Object> map) {
		List<ProductCategory> list = categroyService.findAll();
		map.put("categoryList", list);
		return new ModelAndView("category/list",map);
	}
	
	/**
	 * 展示
	 * @param categroyId
	 * @param map
	 * @return
	 */
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value="categoryId",required=false) Integer categoryId,
			Map<String,Object> map) {
		if(categoryId!=null) {
			ProductCategory category = categroyService.findById(categoryId);
			map.put("category", category);
		}
		return new ModelAndView("category/index",map);
	}
	
	/**
	 * 增加类目
	 * @param from
	 * @param bindingResult
	 * @param map
	 * @return
	 */
	@PostMapping("/save")
	public ModelAndView save(@Valid CategoryFrom from,
			BindingResult bindingResult,Map<String,Object> map) {
		if(bindingResult.hasErrors()) {
			log.error("【增加类目】 发生异常{}",bindingResult.getFieldError().getDefaultMessage());
			map.put("msg",bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/sell/seller/category/index");
			return new ModelAndView("common/error",map);
		}
		
		ProductCategory category = new ProductCategory();
		try {
			if(from.getCategoryId()!=null) {
				category = categroyService.findById(from.getCategoryId());
			}
			BeanUtils.copyProperties(from, category);
			categroyService.save(category);
		} catch (Exception e) {
			log.error("【增加类目】 发生异常{}",bindingResult.getFieldError().getDefaultMessage());
			map.put("msg",bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/sell/seller/category/index");
			return new ModelAndView("common/error",map);
		}
		map.put("url", "/sell/seller/category/list");
		return new ModelAndView("common/success",map);
	}
}
