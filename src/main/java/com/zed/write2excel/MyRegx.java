package com.zed.write2excel;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegx {

    HashSet<String> set = new HashSet<>();

    HashMap<String, String> map_check = new HashMap<>();

    private static final String reg_name = "showName\":\".*?\"";

    private static final String reg_value = "0\\.[0-9]{8}";

    private static final String reg_total_name = "name\\\":\\\".*?\\\"";

    public HashMap<String,HashMap<String, String>> analyzeString(String str) throws Exception {
        HashMap<String, String> map = new HashMap<>();

        List<String> name = new ArrayList<>();
        List<String> value = new ArrayList<>();
        Pattern pattern_name = Pattern.compile(reg_name);
        Pattern pattern_value = Pattern.compile(reg_value);
        Pattern pattern_total_name = Pattern.compile(reg_total_name);

        Matcher matcher_name = pattern_name.matcher(str);
        Matcher matcher_value = pattern_value.matcher(str);
        Matcher matcher_total_name = pattern_total_name.matcher(str);

        String tagName = null;
        while (matcher_total_name.find()) {
            String total_name = matcher_total_name.group();
            if ("name\":\"rate\"".equals(total_name))
                continue;
            if ("name\":\"tgi\"".equals(total_name))
                continue;
            tagName = total_name.substring(7, total_name.length() - 1);
            switch (tagName) {
                case "pref_cosmetics":
                    tagName = "彩妆功效需求";
                    break;
                case "skin_care_category_prefer":
                    tagName = "美容护肤/美体/精油类型偏好";
                    break;
                case "pref_skincare":
                    tagName = "肤质";
                    break;
                case "pred_education_degree":
                    tagName = "预测学历";
                    break;
                case "interest_prefer":
                    tagName = "兴趣爱好";
                    break;
                case  "common_receive_province_180d":
                    tagName = "所在省份";
                    break;
                case "pred_age_level":
                    tagName = "预测年龄";
                    break;
                case "pred_life_stage":
                    tagName = "预测人生阶段";
                    break;
                case "pred_career_type":
                    tagName = "预测职业";
                    break;
                case "common_receive_city_level_180d":
                    tagName = "城市等级";
                    break;
                case "pred_gender":
                    tagName = "预测性别";
                    break;
                case "makeup_category_prefer":
                    tagName = "彩妆/香水/美妆工具类型偏好";
                    break;
                case "skin_price_region":
                    tagName = "护肤品价格偏好";
                    break;
                case "cosmetics_price_region":
                    tagName = "彩妆价格偏好";
                    break;
                case "derive_pay_ord_amt_6m_015_range":
                    tagName = "月均消费金额";
                    break;
                case "skin_year_cnt_region":
                    tagName = "护肤品年消费频次";
                    break;
                case "cosmetics_year_cnt_region":
                    tagName = "彩妆年消费频次";
                    break;
                case "skin_year_amt_region":
                    tagName = "护肤品年消";
                    break;
                case "cosmetics_year_amt_region":
                    tagName = "彩妆年消费金额";
                    break;
                case "dkx_strategy_crowd":
                    tagName = "大快消行业";
                    break;
            }
        }

        //add to list to check total count
        while (matcher_value.find()) {
//            String substring = matcher_value.group().substring(12);
            value.add(matcher_value.group());
        }
        while (matcher_name.find()) {
            String substring = matcher_name.group().substring(11);
            String sub = substring.substring(0, substring.length() - 1);
            name.add(sub);
        }
//        System.out.println("name"+name.size());
//        System.out.println("value"+value.size());
        if (name.size() != value.size())
            System.out.println("does not match");
        else for (int i = 0; i < name.size(); i++) {
            String second_tag_name = name.get(i);
//            if (!set.add(second_tag_name))
//                second_tag_name = tagName + "-" + second_tag_name;
            if (second_tag_name.contains("未知")||
            tagName.contains("彩妆")||
            tagName.contains("护肤"))
                second_tag_name = tagName + "-" + second_tag_name;

            map.put(second_tag_name,value.get(i));
        }
        HashMap<String, HashMap<String, String>> map1 = new HashMap<>();
        map1.put(tagName, map);
        return map1;
    }
}
