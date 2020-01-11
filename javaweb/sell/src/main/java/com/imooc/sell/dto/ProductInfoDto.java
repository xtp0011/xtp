package com.imooc.sell.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 商品详情
 * @author 徐太平
 *
 */
@Data
public class ProductInfoDto {
	
	@JsonProperty("id")
	private String productId;
	
	@JsonProperty("name")
	private String productName;
	
	@JsonProperty("price")
	private BigDecimal productPrice;
	
	@JsonProperty("description")
	private String productDescription; 
	
	@JsonProperty("icon")
	private String productIcon;
}
