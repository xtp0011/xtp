package com.imooc.o2o.dto;

import java.util.List;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;

public class ShopExcution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//店铺数量
	private int count;
	//操作的Shop
	private Shop shop;
	//shop列表
	private List<Shop> shopList;
	
	public ShopExcution() {}
	
	//店铺操作失败的时候使用的构造器
	public ShopExcution(ShopStateEnum sateEnum) {
		this.state=sateEnum.getState();
		this.stateInfo=sateEnum.getStateInfo();
	}
	
	//成功构造器（单个shop）
	public ShopExcution(ShopStateEnum sateEnum,Shop shop) {
		this.state=sateEnum.getState();
		this.stateInfo=sateEnum.getStateInfo();
		this.shop=shop;
	}
	
	//成功构造器（shop列表）
	public ShopExcution(ShopStateEnum sateEnum,List<Shop> shopList) {
		this.state=sateEnum.getState();
		this.stateInfo=sateEnum.getStateInfo();
		this.shopList=shopList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	
	
}
