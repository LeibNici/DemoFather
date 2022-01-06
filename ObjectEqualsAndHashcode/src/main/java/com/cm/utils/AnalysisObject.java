package com.cm.utils;

import java.util.*;

public class AnalysisObject {

    private static Class current;

    public static Map<String, Class> map = new LinkedHashMap<>();

    public static Class analysis(Object o) {

        if (o.getClass() == HashMap.class || o.getClass() == Map.class || o.getClass() == LinkedHashMap.class) {
            Map<Object, Object> d = (Map<Object, Object>) o;
            for (Object key : d.keySet()) {
                current = d.getClass();
                analysis(d.get(key));
            }
        } else if (o.getClass() == ArrayList.class || o.getClass() == LinkedList.class) {
            List list = (List) o;
            current = list.get(0).getClass();
            analysis(list.get(0));
        }
        map.put(current.getSimpleName(),current);
        return current;
    }
}
