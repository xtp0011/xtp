package com.imooc.sell.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.serializer.DateToLongSerializer;
import com.imooc.sell.util.EnumUtil;

import lombok.Data;

@Data
//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
	/**  订单id  */
	private String orderId;
	
	/**  买家名称  */
	private String buyerName;
	
	/**  买家电话  */
	private String buyerPhone;
	
	/**  买家地址  */
	private String buyerAddress;
	
	/**  买家微信id  */
	private String buyerOpenid;
	
	/**  订单总金额  */
	private BigDecimal orderAmount;
	
	/**  订单状态   默认新订单  */
	private Integer orderStatus;
	
	/**  支付状态  默认未支付  */
	private Integer payStatus;
	
	/**  创建时间  */
	@JsonSerialize(using=DateToLongSerializer.class)
	private Date createTime;
	
	/**  修改时间  */
	@JsonSerialize(using=DateToLongSerializer.class)
	private Date updateTime;
	
	@Transient
	private List<OrderDetail> orderDetailList ;
	
	@JsonIgnore
	public OrderStatusEnum getOrderStatusEnum() {
		return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class); 
	}
	
	@JsonIgnore
	public PayStatusEnum getPayStatusEnum() {
		return EnumUtil.getByCode(payStatus,PayStatusEnum.class); 
	}
}
