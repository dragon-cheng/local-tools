package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author dragon.cl 2019/9/4 5:23 PM
 */
public class StringUtils {

    private static final Pattern humpPattern = Pattern.compile("[A-Z]");

    private StringUtils() {

    }

    public static boolean isSame(String s1, String s2) {

        return (s1 == null) ? s2 == null : s1.equals(s2);
    }

    public static boolean isEmpty(String string) {

        return string == null
            || "".equals(string.trim());
    }

    public static boolean isNotEmpty(String string) {

        return string != null
            && !"".equals(string.trim());
    }

    public static boolean isBlank(String str) {

        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotBlank(String str) {

        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public static String toString(String[] array) {

        if (array == null) {
            return "";
        }
        if (array.length == 1) {
            return array[0];
        }
        StringBuilder buf = new StringBuilder(64);
        for (String item : array) {
            buf.append(item).append(",");
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    public static int string2Int(String string) {

        try {
            return Integer.parseInt(string);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * 驼峰命名转下划线
     *
     * @param str
     *
     * @return
     */
    public static String camelToUnderLine(String str) {

        if (str == null || "".equals(str)) {
            return "";
        }

        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);

        String result = sb.toString();
        // 如果首字母为大写，就会以_开头，截取掉这个_
        if (result.startsWith("_")) {
            result = result.substring(1);
        }

        return result;

    }

    public static List<String> splitIgnoreBlank(String s, String d) {

        List<String> list = new ArrayList<>();
        if (isEmpty(s)) {
            return list;
        }
        String[] array = s.split(d);
        for (String ele : array) {
            if (isNotBlank(ele)) {
                list.add(ele);
            }
        }
        return list;
    }

    /**
     * 将字符串数组转成特定格式的字符串：seperator分隔，并以seperator开始和结束
     */
    public static String convertArrayStringToSeperatedString(String arrayString,
                                                             String seperator) {

        if (arrayString == null || !arrayString.startsWith("[")) {
            return arrayString;
        }
        JSONArray array = JSON.parseArray(arrayString);
        if (array == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = array.size(); i < size; i++) {
            String str = array.getString(i);
            if (StringUtils.isEmpty(str)) {
                continue;
            }
            sb.append(seperator).append(str);
        }
        sb.append(seperator);
        return sb.toString();
    }

    public static String convertListToSeperatedString(List<String> array,
                                                      String seperator) {

        if (array == null || array.size() <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(seperator);
        for (String ele : array) {
            if (isEmpty(ele)) {
                continue;
            }
            sb.append(ele).append(seperator);
        }
        return sb.toString();
    }

    public static List<String> convertString2List(String source) {

        List<String> target = new ArrayList<>();
        String[] listString = source.split(",");
        for (int i = 0; i < listString.length; i++) {
            target.add(listString[i]);
        }
        return target;
    }

    public static List<String> splitByChar(String source, char c) {

        char[] array = source.toCharArray();
        List<String> list = new ArrayList<>();
        int begin = 0;
        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != c) {
                size++;
                continue;
            }
            char[] subArray = new char[size];
            System.arraycopy(array, begin, subArray, 0, size);
            list.add(new String(subArray));
            begin = i + 1;
            size = 0;
        }
        char[] newArray = new char[size];
        System.arraycopy(array, begin, newArray, 0, size);
        list.add(new String(newArray));
        return list;
    }

    public static void main(String[] args) {

        System.out.println(isSame(null, null));
        System.out.println(isSame(null, ""));
        System.out.println(isSame("", null));
        System.out.println(isSame("a", "b"));
        System.out.println(isSame("a", "a"));
    }
}
