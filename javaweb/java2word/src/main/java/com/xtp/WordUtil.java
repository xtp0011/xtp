package com.xtp;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordUtil {



    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceText(para, params);
        }
    }



    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private  void replaceText(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = matcher(runText);
                if (matcher.find()) {
                    while ((matcher = matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    System.out.println("runText <<<<<<"+runText);
                    para.removeRun(i);
                    //重新插入run里内容格式可能与原来模板的格式不一致
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }
    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceText(para, params);
                    }
                }
            }
        }
    }


    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    public void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    public void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("${id}","23432fsfwrwrew");
        params.put("${name}", "李四");
        params.put("${phone}", "1334332424224");


        WordUtil xwpfTUtil = new WordUtil();

        XWPFDocument doc;
        String filePath = "需求意向书 最新模板.docx";
        InputStream is;
        is = new FileInputStream(filePath);
        doc = new XWPFDocument(is);

        xwpfTUtil.replaceInPara(doc, params);
//        //替换表格里面的变量
//        xwpfTUtil.replaceInTable(doc, params);


        OutputStream os = new FileOutputStream("需求意向书 最新模板副本.docx");
        doc.write(os);

        xwpfTUtil.close(os);
        xwpfTUtil.close(is);

        os.flush();
        os.close();

    }

}
