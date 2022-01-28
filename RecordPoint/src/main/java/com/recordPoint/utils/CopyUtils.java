package com.recordPoint.utils;

import java.io.*;
import java.util.List;

/**
 * @author chenming
 * @description List 列表深度复制
 * @create: 2022-01-20
 */
public class CopyUtils {

    /**
     * 对指定List集合进行深度复制
     *
     * @param srcList 原始List集合
     * @param <T>     返回相应的List结合
     * @return 返回与原始集合相同值的新集合
     */
    public static <T> List<T> deepCopy(List<T> srcList) {

        // 字符数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // java.io 序列化对象输出流 使用writeObject对指定的obj对象进行序列化
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(srcList);
            // 字符数组输入流
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            // java.io 反序列化对象输入流 使用readObject对指定的字符数组对象进行序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            List<T> destList = (List<T>) objectInputStream.readObject();
            return destList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
