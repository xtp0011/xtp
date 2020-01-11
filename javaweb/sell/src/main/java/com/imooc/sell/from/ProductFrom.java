package com.imooc.sell.from;

import java.math.BigDecimal;
import lombok.Data;
@Data
public class ProductFrom {
	
	private String productId;
	
	/**名字*/
	private String productName;
	
	/**价格*/
	private BigDecimal productPrice;
	
	/**库存*/
	private Integer productStock;
	
	/**描述*/
	private String productDescription;
	
	/**小图*/
	private String productIcon;
	
	/**类目编号*/
	private Integer categoryType;
	
}
