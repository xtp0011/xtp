package com.imooc.sell.dto;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
/**
 * 商品(包含类目)
 * @author 徐太平
 *
 */
@Data
public class ProductDto {
	
	@JsonProperty("name")
	private String categroyName;
	
	@JsonProperty("type")
	private Integer categroyType;
	
	@JsonProperty("foods")
	private List<ProductInfoDto> productInfoDtos;
}
