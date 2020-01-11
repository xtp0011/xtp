package com.xtp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;

public class DocUtli {

    /**
     * 替换段落中的指定文字
     * @param document
     * @param map
     */
    private static void replaceDoc(XWPFDocument document, Map<String, String> map){
        // 替换段落中的指定文字
        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();//获得word中段落
        while (itPara.hasNext()) {       //遍历段落
            XWPFParagraph paragraph = itPara.next();
            Set<String> set = map.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                List<XWPFRun> run=paragraph.getRuns();
                for(int i=0;i<run.size();i++)
                {
                    if(run.get(i).getText(run.get(i).getTextPosition())!=null &&
                            run.get(i).getText(run.get(i).getTextPosition()).equals(key))
                    {
                        /**
                         * 参数0表示生成的文字是要从哪一个地方开始放置,设置文字从位置0开始
                         * 就可以把原变量替换掉
                         */
                        run.get(i).setText(map.get(key),0);
                    }
                }
            }
        }
        // 替换表格中的指定文字
        Iterator<XWPFTable> itTable = document.getTablesIterator();//获得Word的表格
        while (itTable.hasNext()) { //遍历表格
            XWPFTable table = itTable.next();
            int count = table.getNumberOfRows();//获得表格总行数
            for (int i = 0; i < count; i++) { //遍历表格的每一行
                XWPFTableRow row = table.getRow(i);//获得表格的行
                List<XWPFTableCell> cells = row.getTableCells();//在行元素中，获得表格的单元格
                for (XWPFTableCell cell : cells) {   //遍历单元格
                    for (Map.Entry<String, String> e : map.entrySet()) {
                        if (cell.getText().equals(e.getKey())) {//如果单元格中的变量和‘键’相等，就用‘键’所对应的‘值’代替。
                            cell.removeParagraph(0);//所以这里就要求每一个单元格只能有唯一的变量。
                            cell.setText(e.getValue());
                        }
                    }
                }
            }
        }

    }

    public static String createPoiDoc(String templatFileName,String createFilename,Map<String,String> params){
        if(createFilename==null){
            return null;
        }
        InputStream inputStream = null ;
        OutputStream outputStream = null;
        File templateFile = new File(templatFileName);
        try {
            inputStream = new FileInputStream(templateFile);
            XWPFDocument document = new XWPFDocument(inputStream);
            replaceDoc(document,params);
            File createFile = new File(createFilename);
            outputStream = new FileOutputStream(createFile);
            document.write(outputStream);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null) {
                    inputStream.close();
                }
                if(outputStream!=null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  createFilename;
    }

    public static String creatFMDoc(String tmplateFilePath,String templatFileName,String createFilePath,String createFilename,Map<String,String> params){
        String fileName = createFilePath+File.separator+createFilename;
        if(fileName==null){
            return null;
        }
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(tmplateFilePath));
            Template template = configuration.getTemplate(templatFileName);
            File crateFile = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(crateFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"utf-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            template.process(params,bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
