package com.xtp;

import java.util.HashMap;
import java.util.Map;

public class TestMain {
    public static void main(String[] args) {
        Map<String,String> params = new HashMap<String, String>();
        params.put("acctNo","1234567890");
        params.put("acctName","村镇银行信贷业务限额校验");
        params.put("userName","李四");
        params.put("date","2019-05-09");
        params.put("phone","13568493423");
        //WordPOI.replaceAndGenerateWord("需求意向书 最新模板.docx","需求意向书 最新模板副本.docx",params);
       // DocUtli.createPoiDoc("需求意向书 最新模板.docx","需求意向书 最新模板副本.docx",params);
       // DocUtli.creatFMDoc("D:\\word","test.ftl","D:\\test","test.docx",params);


    }
}
