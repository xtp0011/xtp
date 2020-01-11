package com.imooc.sell.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.util.EnumUtil;

import lombok.Data;

//import lombok.Data;
/**
 * 商品
 * @author 徐太平
 *
 */
@Entity
@DynamicUpdate
@Data
@DynamicInsert
public class ProductInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
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
	
	/**状态 0正常，1下架*/
	private Integer productStatus = ProductStatusEnum.UP.getCode();
	
	/**类目编号*/
	private Integer categoryType;
	
	private Date createTime ;
	
	private Date updateTime ;
	
	@JsonIgnore
	public ProductStatusEnum getProductStatusEnum() {
		return EnumUtil.getByCode(productStatus,ProductStatusEnum.class); 
	}
	

}
