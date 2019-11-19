package com.zed.write2excel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Zeluo
 * @date 2019/11/19 10:24
 */
@Component
public class ConstentConfig {
    public static String READPATH = "D:\\logs\\data";

    public static String WRITEPATH = "D:\\logs\\test1.xls";
}
