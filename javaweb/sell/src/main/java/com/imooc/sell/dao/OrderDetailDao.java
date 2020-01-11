package com.imooc.sell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.sell.entity.OrderDetail;

public interface OrderDetailDao extends JpaRepository<OrderDetail, String>{
	List<OrderDetail> findByOrderId(String orderId);
}
