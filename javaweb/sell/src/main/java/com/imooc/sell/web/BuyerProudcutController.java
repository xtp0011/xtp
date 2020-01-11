package com.imooc.sell.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dto.ProductDto;
import com.imooc.sell.dto.ProductInfoDto;
import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.service.CategroyService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.util.ResultVOUtil;

@RestController
@RequestMapping("/bubye/product")
public class BuyerProudcutController {
	
	@Autowired
	private ProductInfoService infoService;
	
	@Autowired
	private CategroyService categroyService;
	
	@GetMapping("/list")
	//@Cacheable(cacheNames="product",key="123")
	@Cacheable(cacheNames="product",key="#sellerId",
	condition="#sellerId.length()>3",unless="#result.getCode()!=0")
	public ResultVO<ProductDto> list(@RequestParam("sellerId")String sellerId) {
		
		//1.查询所有上架的商品
		List<ProductInfo> infoList = infoService.findUpAll();
		
		//2.查询类目
		
		//传统方法
		//List<Integer> categroyTypeList = new ArrayList<Integer>();
		//		for (ProductInfo productInfo : infoList) {
		//			categroyTypeList.add(productInfo.getCategoryType());		
		//		}
		
		//精简方法(java8,lambda)
		List<Integer> categroyTypeList = infoList.stream().map(e->e.getCategoryType()).collect(Collectors.toList());
		List<ProductCategory> productCategoryList = categroyService.findByCategroyTypeIn(categroyTypeList);
		
		//3.数据拼装
		List<ProductDto> productDtoList = new ArrayList<ProductDto>();
		
		for (ProductCategory productCategory : productCategoryList) {
			ProductDto productDto = new ProductDto() ;
			productDto.setCategroyType(productCategory.getCategoryType());
			productDto.setCategroyName(productCategory.getCategoryName());
			List<ProductInfoDto> productInfoDtoList = new ArrayList<ProductInfoDto>();
			for (ProductInfo info : infoList) {
				if(info.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoDto productInfoDto = new ProductInfoDto();
					BeanUtils.copyProperties(info, productInfoDto);
					productInfoDtoList.add(productInfoDto);
				}
			}
			productDto.setProductInfoDtos(productInfoDtoList);
			productDtoList.add(productDto);
		}
		return ResultVOUtil.success(productDtoList);
	}
}
