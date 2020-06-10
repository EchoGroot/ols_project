package com.ols.ols_project.common.utils;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;


import java.io.*;

/**
 * 获取word文本
 *
 * @author Steven
 *
 */
public class WordUtil {
    /**
     * 读取word文件内容
     *
     * @param path
     * @return buffer
     */


    public String readWord(String path) {
        String buffer = "";
        try {
            if (path.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(path));

                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
            } else if (path.endsWith("docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            } else if (path.endsWith("pdf")){
                PDDocument document=PDDocument.load(new File(path));
                PDFTextStripper stripper=new PDFTextStripper();
                stripper.setSortByPosition(false);
                buffer=stripper.getText(document);
                document.close();
            }else if (path.endsWith("txt")){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));// 构造一个BufferedReader类来读取文件
                String s = null;
                while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                    buffer = buffer + "\n" + s;
                }
                br.close();
            }
            else {
                System.out.println("此文件不是word文件！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer;
    }
}
