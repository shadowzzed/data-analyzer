package com.zed.write2excel.writer;

import com.zed.write2excel.ConstentConfig;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zeluo
 * @date 2019/11/8 17:35
 */
@Component
public class Write2Excel {

    private static String sheetName = "test";

//    private static String url = "D:\\logs\\test1.xls";

    private static int count = 0;

    public void write(HashMap<String,HashMap<String, Float>> map) throws IOException {
        FileInputStream fs;
        fs = new FileInputStream(ConstentConfig.WRITEPATH);
        POIFSFileSystem ps=new POIFSFileSystem(fs); //使用POI提供的方法得到excel的信息
        HSSFWorkbook wb=new HSSFWorkbook(ps);

        HSSFSheet sheet=wb.getSheetAt(0); //获取到工作表，因为一个excel可能有多个工作表
        if (sheet == null)
            sheet = wb.createSheet(sheetName);
//        count++;
        boolean flag = true;
        for (Map.Entry<String, HashMap<String, Float>> entry : map.entrySet()) {
            Row row = sheet.createRow(count);
            Cell cell = row.createCell(0);
            cell.setCellValue(entry.getKey());
            HashMap<String, Float> entryValueMap = entry.getValue();
            for (Map.Entry<String, Float> entryValue: entryValueMap.entrySet()) {
//                Row row1 = sheet.getRow(count++);
//                if (row1 == null)
                Row row1;
                if (flag) {
                    flag = false;
                    row1 = sheet.getRow(count++);
                }
                else row1 = sheet.createRow(count++);
//                Cell test = row1.getCell(3);
                cell = row1.createCell(1);
                cell.setCellValue(entryValue.getKey());
                cell = row1.createCell(2);
                cell.setCellValue(entryValue.getValue());
            }
        }
        FileOutputStream out=new FileOutputStream(ConstentConfig.WRITEPATH);
        wb.write(out);


    }

}
