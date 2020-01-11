package com.imooc.sell.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class OrderMaster {
	
	/**  订单id  */
	@Id
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
	private Integer orderStatus=OrderStatusEnum.NEW.getCode();
	
	/**  支付状态  默认未支付  */
	private Integer payStatus=PayStatusEnum.WAIT.getCode();
	
	/**  创建时间  */
	private Date createTime;
	
	/**  修改时间  */
	private Date updateTime;
	
//	@Transient
//	private List<OrderDetail> OrderDetail ;
}
