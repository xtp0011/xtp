package com.imooc.sell.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.sell.VO.ResultVO;

/**
 * 前端返回数据处理
 * @author 徐太平
 *
 */

public class ResultVOUtil {
	
	public static final int SUCCESS=0;
	public static final int FALIED=1;
	
	public static ResultVO success(Object object) {
		ResultVO resultVO = new ResultVO();
		resultVO.setData(object);
		resultVO.setCode(SUCCESS);
		resultVO.setMessage("SUCCESS");
		return resultVO;
	}
	
	public static ResultVO success() {
		return success();
	}
	
	public static ResultVO error(Integer code, String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(code);
		resultVO.setMessage(msg);
		return resultVO;
	}
	
	public static ResultVO erroe() {
		return error(FALIED,"ERROR");
	}
}
