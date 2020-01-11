package com.imooc.sell.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


/**
 * 类目
 * @author 徐太平
 *
 */

@Entity
@DynamicUpdate
@Data
@DynamicInsert
public class ProductCategory{
	
	/** 类目id */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer categoryId;
	
	/** 类目名称 */
	private String categoryName;
	
	/** 类目编号 */
	private Integer categoryType;

	private Date createTime ;
	
	private Date updateTime ;
	
}
