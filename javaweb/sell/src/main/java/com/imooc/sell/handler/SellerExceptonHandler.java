package com.imooc.sell.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.exception.SellerAuthorizeException;
import com.imooc.sell.util.ResultVOUtil;

@ControllerAdvice
public class SellerExceptonHandler {
	
	@Autowired
	private ProjectUrlConfig projectUrlConfig;
	//拦截登陆异常
	@ExceptionHandler(value=SellerAuthorizeException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ModelAndView handlerAuthorizeException() {
		return new ModelAndView("redirect:".concat(projectUrlConfig.getWechatOpenAuthorize())
				.concat("/sell/sell/wechat/qrAuthorize").concat("?returnUrl=")
				.concat(projectUrlConfig.getSell()).concat("sell/seller/login"));
	}
	
	@ExceptionHandler(value=SellException.class)
	@ResponseBody
	public ResultVO hanlerSellerExcepton(SellException e){
		return ResultVOUtil.error(e.getCode(), e.getMessage());
	}
}
