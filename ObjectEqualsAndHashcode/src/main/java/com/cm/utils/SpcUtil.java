package com.cm.utils;

/**
 * @author chenming
 * @description
 * @create: 2022-03-18
 */
public class SpcUtil {

    public SpcUtil() {
        System.out.println("SpcUtil init");
    }

    static {
        SpcUtil spcUtil = new SpcUtil();
        System.out.println("Spc static");
    }

    public static void sout(){
        System.out.println("Spc sout method");
    }

}
