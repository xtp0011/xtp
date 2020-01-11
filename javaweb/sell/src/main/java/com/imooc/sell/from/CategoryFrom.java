package com.imooc.sell.from;

import lombok.Data;

@Data
public class CategoryFrom {
	
	private Integer categoryId;
	
	/** 类目名称 */
	private String categoryName;
	
	/** 类目编号 */
	private Integer categoryType;
}
