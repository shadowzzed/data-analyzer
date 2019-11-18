package com.zed.write2excel.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Zeluo
 * @date 2019/11/8 17:11
 */
@Component
@Slf4j
public class FileUtils {


    public static String[] getFiles(String path) throws Exception {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        File[] tempList  = file.listFiles();
        if (tempList == null)
            throw new Exception("路径错误");
        for (File value : tempList) {
            if (value.toString().endsWith(".zip"))
                continue;
            if (value.isFile()) {
                list.add(value.toString());
            }
            if (value.isDirectory()) {
                String[] files = getFiles(value.toString());
                list.addAll(Arrays.asList(files));
            }
        }
        return list.toArray(new String[list.size()]);
    }

    public static String[] getContent(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        List<String> list = new ArrayList<>();
        String str;
        while ((str = br.readLine()) != null) {
            list.add(str);
        }
        return list.toArray(new String[list.size()]);
    }
}
