package com.imooc.o2o.service;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;

public class AreaServiceTest extends BaseTest{
	@Autowired
	private AreaService areaService ;
	
	@Test
	public void getAreaList() {
		List<Area> areaList;
		try {
			areaList = areaService.getAreaList();
			System.out.println(areaList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
