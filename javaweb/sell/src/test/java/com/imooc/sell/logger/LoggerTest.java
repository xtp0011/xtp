 package com.imooc.sell.logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Slf4j
public class LoggerTest {
	private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
	@Test
	public void test1() {
		logger.debug("debug...");
		logger.info("info...");
		logger.error("error...");;
	}
	
	@Test
	public void test2() {
		String name = "imooc";
		String password = "12345";
		logger.info("name {},password {}",name,password);
	}


}
