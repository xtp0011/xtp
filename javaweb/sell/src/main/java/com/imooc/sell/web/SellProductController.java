package com.imooc.sell.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.from.ProductFrom;
import com.imooc.sell.service.CategroyService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 卖家端商品
 * @author 徐太平
 *
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellProductController {
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@Autowired
	private CategroyService categroyService;
	/**
	 * 列表
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value="page",defaultValue="1") Integer page,
			@RequestParam(value="size",defaultValue="10") Integer size,Map<String,Object> map) {
		
		PageRequest pageRequest = PageRequest.of(page-1, size);
		Page<ProductInfo> list = productInfoService.findAll(pageRequest);
		map.put("productInfoPage", list);
		map.put("currentPage", page);
		map.put("size", size);
		return new ModelAndView("product/list",map);
		
	}
	
	/**
	 * 上架
	 * @param productId
	 * @param map
	 * @return
	 */
	@GetMapping("/on_sale")
	public ModelAndView onSale(@RequestParam("productId") String productId,
			Map<String,Object> map) {
		try {
			productInfoService.onSale(productId);
		} catch (SellException e) {
			log.error("【商品上架】 发生异常{}",e);
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/list");
			return new ModelAndView("common/error",map);
		}
		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success",map);
	}
	
	/**
	 * 上架
	 * @param productId
	 * @param map
	 * @return
	 */
	@GetMapping("/off_sale")
	public ModelAndView offSale(@RequestParam("productId") String productId,
			Map<String,Object> map) {
		try {
			productInfoService.offSale(productId);
		} catch (SellException e) {
			log.error("【商品下架】 发生异常{}",e.getMessage());
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/list");
			return new ModelAndView("common/error",map);
		}
		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success",map);
	}
	
	
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value="productId",required=false) String productId,
			Map<String,Object> map) {
		if(!StringUtils.isEmpty(productId)) {
			ProductInfo productInfo = productInfoService.findByProductId(productId);
			map.put("productInfo", productInfo);
		}
		//查询所有类目
		List<ProductCategory> list = categroyService.findAll();
		map.put("categoryList", list);
		return new ModelAndView("product/index",map);
	}
	
	/**
	 * 保存更新
	 * @param productFrom
	 * @return
	 */
	@PostMapping("/save")
	//@CachePut(cacheNames="product",key="123")
	//@CacheEvict(cacheNames="product",key="123")
	public ModelAndView save(@Valid ProductFrom productFrom,
			BindingResult bindingResult,Map<String,Object> map) {
		if(bindingResult.hasErrors()) {
			log.error("【增加商品】 发生异常{}",bindingResult.getFieldError().getDefaultMessage());
			map.put("msg",bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/sell/seller/product/index");
			return new ModelAndView("common/error",map);
		}
		ProductInfo productInfo = new ProductInfo();
		try {			
			//如果productId为空，说明是新增
			if(!StringUtils.isEmpty(productFrom.getProductId())) {
				productInfo = productInfoService.findByProductId(productFrom.getProductId());				
			}else {
				productFrom.setProductId(KeyUtil.genUniqueKey());
			}
			BeanUtils.copyProperties(productFrom, productInfo);
			productInfoService.save(productInfo);
		} catch (SellException e) {
			log.error("【增加商品】 发生异常{}",e.getMessage());
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/index");
			return new ModelAndView("common/error",map);
		}
		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success",map);
	}
}
