package com.imooc.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;

@Controller
@RequestMapping("/superadmin")
public class AreaContoller {
	
	@Autowired
	private AreaService areaService ;
	
	@RequestMapping(value="/listarea",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> listArea(){
		Map<String,Object> modeMap = new HashMap<String,Object>();
		List<Area> areaList=new ArrayList<Area>();
		try {
			 areaList = areaService.getAreaList();
			 modeMap.put("row", areaList);
			 modeMap.put("total", areaList.size());
		} catch (Exception e) {
			e.printStackTrace();
			modeMap.put("success", false);
			modeMap.put("errMsg", e.toString());	
		}
		return modeMap;
	}

}
