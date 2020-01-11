package com.imooc.sell.VO;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.sell.util.ResultVOUtil;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @author 徐太平
 *
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**错误码*/
	private Integer code;
	
	/**提示信息*/
	private String message;
	
	/**具体内容*/
	private T data;
	
	public ResultVO() {}
	
	public ResultVO(Integer code , T data) {
		this.code=code;
		switch(code) {
			case ResultVOUtil.SUCCESS :
				this.message="SUCCESS";
				this.data=data;
				break;
			case ResultVOUtil.FALIED :
				this.message="ERROR";
				break;
		}
	}
}
