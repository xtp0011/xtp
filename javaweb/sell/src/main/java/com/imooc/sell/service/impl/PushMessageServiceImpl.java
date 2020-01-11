package com.imooc.sell.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.sell.config.WechatAccountConfig;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.service.PushMessageService;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService{

	@Autowired
	private WxMpService wxMpService;
	
	@Autowired
	private WechatAccountConfig accountConfig;
	@Override
	public void orderStatus(OrderDto orderDto) {
		WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
		wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
		wxMpTemplateMessage.setToUser(orderDto.getBuyerOpenid());
		List<WxMpTemplateData> data = Arrays.asList(
				new WxMpTemplateData("first","亲，请记得收货！"),
				new WxMpTemplateData("keyword1","微信点餐"),
				new WxMpTemplateData("keyword2","15313695933"),
				new WxMpTemplateData("keyword3",orderDto.getOrderId()),
				new WxMpTemplateData("keyword4",orderDto.getOrderStatusEnum().getMessage()),
				new WxMpTemplateData("keyword5","￥"+orderDto.getOrderAmount()),
				new WxMpTemplateData("remark","欢迎再次光临！"));
		wxMpTemplateMessage.setData(data);
		try {
			wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
		} catch (WxErrorException e) {
			log.error("【微信模板消息】 发送失败，{}",e);
		}
		
		
	}

}
