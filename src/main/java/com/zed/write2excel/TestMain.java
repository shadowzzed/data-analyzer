package com.zed.write2excel;

import com.zed.write2excel.utils.FileUtils;
import com.zed.write2excel.writer.Write2Excel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zeluo
 * @date 2019/11/11 10:37
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        MyRegx myRegx = new MyRegx();
        Write2Excel writer = new Write2Excel();
        String[] paths = FileUtils.getFiles("D:\\logs\\data");
        for (String path:paths) {
            String[] content = FileUtils.getContent(path);
            for (String str: content) {
                HashMap<String, HashMap<String, String>> map = myRegx.analyzeString(str);
                writer.write(map);
            }
        }
    }
}
