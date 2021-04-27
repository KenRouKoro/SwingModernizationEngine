package cn.korostudio.jsme.tools.classscanner;

import java.io.File;

/**
 * @FileName: StringUtil.java
 * @Author
 * @Description:
 * @Date 2014年11月16日 上午10:04:03
 * @CopyRight
 */

/*
 * 字符串工具类
 */
public final class StringUtil {

    /**
     * @param propertyName 属性名
     * @return String "get"开头且参数(propertyName)值首字母大写的字符串
     * @Title: getMethodName
     * @Description: 获取对象类型属性的get方法名
     */
    public static String convertToReflectGetMethod(String propertyName) {
        return "get" + toFirstUpChar(propertyName);
    }

    /**
     * @param propertyName 属性名
     * @return String "set"开头且参数(propertyName)值首字母大写的字符串
     * @Title: convertToReflectSetMethod
     * @Description: 获取对象类型属性的set方法名
     */
    public static String convertToReflectSetMethod(String propertyName) {
        return "set" + toFirstUpChar(propertyName);
    }

    /**
     * @param target 目标字符串
     * @return String 首字母大写的字符串
     * @Title: toFirstUpChar
     * @Description: 将字符串的首字母大写
     */
    public static String toFirstUpChar(String target) {
        StringBuilder sb = new StringBuilder(target);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    /**
     * @param fileName 文件名
     * @return String 文件名后缀。如：jpg
     * @Title: getFileSuffixName
     * @Description: 获取文件名后缀
     */
    public static String getFileSuffixName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * @param stringValue 传入要验证的字符串
     * @return boolean true：不为 空 或 不为null; false:值为 空 或 为null
     * @Title: checkStringIsNotEmpty
     * @Description:验证字符串是否不为空
     */
    public static boolean isNotEmpty(String stringValue) {
        return null != stringValue && !"".equals(stringValue.trim());
    }

    /**
     * @param classFile 类文件
     * @return String 类全名
     * @Title: getPackageByPath
     * @Description 通过指定文件获取类全名
     */
    public static String getPackageByPath(File classFile, String exclude) {
        if (classFile == null || classFile.isDirectory()) {
            return null;
        }

        String path = classFile.getAbsolutePath().replace('\\', '/');

        path = path.substring(path.indexOf(exclude) + exclude.length()).replace('/', '.');
        if (path.startsWith(".")) {
            path = path.substring(1);
        }
        if (path.endsWith(".")) {
            path = path.substring(0, path.length() - 1);
        }

        return path.substring(0, path.lastIndexOf('.'));
    }
}