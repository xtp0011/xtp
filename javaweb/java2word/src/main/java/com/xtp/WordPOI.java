package com.xtp;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordPOI {
        // 返回Docx中需要替换的特殊字符，没有重复项
        // 推荐传入正则表达式参数"\\$\\{[^{}]+\\}"
        public ArrayList<String> getReplaceElementsInWord(String filePath, String regex) {
                    ArrayList<String> al = new ArrayList<String>();
                    XWPFDocument document = null;
                    try {
                        document = new XWPFDocument(POIXMLDocument.openPackage(filePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 遍历段落
                    Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
                    while (itPara.hasNext()) {
                        XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                        String paragraphString = paragraph.getText();
                        CharSequence cs = paragraphString.subSequence(0,paragraphString.length());
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(cs);
                        int startPosition = 0;
                        while (matcher.find(startPosition)) {
                            if (!al.contains(matcher.group())) {
                                al.add(matcher.group());
                            }
                            startPosition = matcher.end();
                        }
                    }
                    // 遍历表
                    Iterator<XWPFTable> itTable = document.getTablesIterator();
                    while (itTable.hasNext()) {
                        XWPFTable table = (XWPFTable) itTable.next();
                        int rcount = table.getNumberOfRows();
                        for (int i = 0; i < rcount; i++) {
                            XWPFTableRow row = table.getRow(i);
                            List<XWPFTableCell> cells = row.getTableCells();
                            for (XWPFTableCell cell : cells) {
                                String cellText = "";
                                cellText = cell.getText();
                                CharSequence cs = cellText.subSequence(0, cellText.length());
                                Pattern pattern = Pattern.compile(regex);
                                Matcher matcher = pattern.matcher(cs);
                                int startPosition = 0;
                                while (matcher.find(startPosition)) {
                                    if (!al.contains(matcher.group())) {
                                        al.add(matcher.group());
                                    }
                                    startPosition = matcher.end();
                                }
                            }
                        }
                    }
                    return al;

        }
        // 替换word中需要替换的特殊字符
        public static boolean replaceAndGenerateWord(String srcPath, String destPath, Map<String, String> map) {
                    try {
                        XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
                        // 替换段落中的指定文字
                        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
                        while (itPara.hasNext()) {
                            XWPFParagraph paragraph = itPara.next();
                            List<XWPFRun> runs = paragraph.getRuns();
                            for (int i = 0; i < runs.size(); i++) {
                                String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
                                for (Map.Entry<String, String> entry : map.entrySet()) {
                                    oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
                                }
                                runs.get(i).setText(oneparaString, 0);
                            }
                        }

                        // 替换表格中的指定文字
                        Iterator<XWPFTable> itTable = document.getTablesIterator();
                        while (itTable.hasNext()) {
                            XWPFTable table = (XWPFTable) itTable.next();
                            int rcount = table.getNumberOfRows();
                            for (int i = 0; i < rcount; i++) {
                                XWPFTableRow row = table.getRow(i);
                                List<XWPFTableCell> cells = row.getTableCells();
                                for (XWPFTableCell cell : cells) {
                                    String cellTextString = cell.getText();
                                    for (Map.Entry<String, String> e : map.entrySet()) {
                                        if (cellTextString.contains(e.getKey()))
                                            cellTextString = cellTextString.replace(e.getKey(), e.getValue());
                                    }
                                    cell.removeParagraph(0);
                                    cell.setText(cellTextString);
                                }
                            }
                        }
                        FileOutputStream outStream = null;
                        outStream = new FileOutputStream(destPath);
                        document.write(outStream);
                        outStream.close();
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }

                }



        public static void main(String[] args) {
            // TODO Auto-generated method stub
            String filepathString = "D:/test.docx";
            String destpathString = "D:/test1.doc";
            Map<String, String> map = new HashMap<String, String>();
            map.put("${NAME}", "王五王五啊柯乐义的辣味回答侯何问起网");
            map.put("${NsAME}", "王五王五啊王力味回答侯何问起网");
            map.put("${NAMaE}", "王五王五啊柯乐义侯何问起网");
            map.put("${NArME}", "王五王五啊柯乐义的辣味回答东拉网");
            map.put("${NwAME}", "王五王五啊王的辣味回答侯何问起网");
            map.put("${NA4ME}", "王五王五啊王力侯何问起网");
            map.put("${N5AME}", "王五王五辣味回答侯何问起网");
            map.put("${NAadwME}", "王五力宏的辣味回答侯何问起网");
            System.out.println(replaceAndGenerateWord(filepathString,destpathString, map));
        }
    }

