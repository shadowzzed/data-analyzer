package com.zed.write2excel;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegx {

    HashSet<String> set = new HashSet<>();

    HashMap<String, String> map_check = new HashMap<>();

    private static final String reg_json_string = "\\[.*?\\]";

    private static final String reg_name = "tagValueName\":\".*?\"";

    private static final String reg_value = "rate\":.*?,";

    private static final String reg_total_name = "tagName\":\".*?\"";

    public HashMap<String,HashMap<String, Float>> analyzeString(String json) throws Exception {

        HashMap<String, HashMap<String, Float>> map1 = new HashMap<>();
        List<String> tag_name_total = new ArrayList<>();
        Pattern pattern_name = Pattern.compile(reg_name);
        Pattern pattern_value = Pattern.compile(reg_value);
        Pattern pattern_total_name = Pattern.compile(reg_total_name);
        Pattern pattern_json_string = Pattern.compile(reg_json_string);


        Matcher matcher_json_string = pattern_json_string.matcher(json);

        Matcher matcher_total_name = pattern_total_name.matcher(json);
        while (matcher_total_name.find()) {
            String total_name = matcher_total_name.group();
//            if ("name\":\"rate\"".equals(total_name))
//                continue;
//            if ("name\":\"tgi\"".equals(total_name))
//                continue;
            String tagName = null;
            tagName = total_name.substring(10, total_name.length() - 1);
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
                case "common_receive_province_180d":
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
            tag_name_total.add(tagName);
        }

        int count = 0;
        while (matcher_json_string.find()) {
            String tagName_temp = tag_name_total.get(count++);
            HashMap<String, Float> map = new HashMap<>();
            List<String> name = new ArrayList<>();
            List<Float> value = new ArrayList<>();
            String tagName = null;

            String str = matcher_json_string.group();
            Matcher matcher_name = pattern_name.matcher(str);
            Matcher matcher_value = pattern_value.matcher(str);
            //add to list to check total count
            while (matcher_value.find()) {
//            String substring = matcher_value.group().substring(12);
                String rate = matcher_value.group().substring(6, matcher_value.group().length() - 1);
                float f = Float.parseFloat(rate) / 100;
                value.add(f);
            }
            while (matcher_name.find()) {
                String sub = matcher_name.group().substring(15, matcher_name.group().length() - 1);
                name.add(sub);
            }
            if (name.size() != value.size())
                System.out.println("does not match");
            else {
                for (int i = 0; i < name.size(); i++) {
                    String second_tag_name = name.get(i);
//            if (!set.add(second_tag_name))
//                second_tag_name = tagName + "-" + second_tag_name;
                    if (second_tag_name.contains("未知") ||
                            tagName_temp.contains("彩妆") ||
                            tagName_temp.contains("护肤"))
                        second_tag_name = tagName_temp + "-" + second_tag_name;

                    map.put(second_tag_name, value.get(i));
                }
            }
            map1.put(tagName_temp, map);
        }




//        System.out.println("name"+name.size());
//        System.out.println("value"+value.size());

        return map1;
    }
}
