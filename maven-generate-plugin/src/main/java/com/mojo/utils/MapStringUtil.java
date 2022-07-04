package com.mojo.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/6/6 18:37
 * @Description:
 */
public class MapStringUtil {
    public static String MapToString(Map<String, String> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(","));
        return mapAsString;
    }

    public static Map<String, String> stringToMap(String mapAsString) {
        Map<String, String> map = Arrays.stream(mapAsString.split(","))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        return map;
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("keya", "valuea");
        map.put("keyb", "valueb");
        map.put("keyc", "valuec");
        map.put("keyd", "valued");

        System.out.println(MapToString(map));
        Map<String, ?> map1 = stringToMap(MapToString(map));

        map1.forEach((key,value)->{
            System.out.println(key + "=" + value);
        });
    }
}
