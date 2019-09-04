package util;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * @author dragon.cl 2019/9/4 5:50 PM
 */
public class JsonUtils {

    private JsonUtils() {}

    private static String DATE_PATTERN_FOR_DAY = "yyyy-MM-dd";

    private static String DATE_PATTERN_FOR_TIME = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat dateFormatterDay = new SimpleDateFormat(
        DATE_PATTERN_FOR_DAY);

    public static SimpleDateFormat dateFormatterTime = new SimpleDateFormat(
        DATE_PATTERN_FOR_TIME);

    /**
     * fastJson 的序列化配置
     */
    private final static SerializeConfig SERIALIZE_MAPPING
        = new SerializeConfig();

    public static String toJsonString(Object object) {

        return toJsonString(object, DATE_PATTERN_FOR_TIME);
    }

    public static String toJsonString(Object object, String dateFormat) {

        if (null == object) {
            return "";
        }

        SERIALIZE_MAPPING.put(Date.class,
                              new SimpleDateFormatSerializer(dateFormat));
        // 默认打出所有属性(即使属性值为null)|属性排序输出
        SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.SortField
        };
        return JSON.toJSONString(object, SERIALIZE_MAPPING, features);
    }

    /**
     * 遇到值为null的属性时就不打印
     *
     * @param object
     *
     * @return
     */
    public static String toJsonStringExceptNullValue(Object object) {

        if (null == object) {
            return "";
        }

        SERIALIZE_MAPPING.put(Date.class, new SimpleDateFormatSerializer(
            DATE_PATTERN_FOR_TIME));
        SerializerFeature[] features = {
            SerializerFeature.WriteEnumUsingToString
        };
        return JSON.toJSONString(object, SERIALIZE_MAPPING, features);
    }

    public static Set<Long> convertString2LongSet(String string) {

        if (StringUtils.isEmpty(string)) {
            return Collections.EMPTY_SET;
        }

        JSONArray array = JSONArray.parseArray(string);
        return convertArray2LongSet(array);
    }

    public static Set<Long> convertArray2LongSet(JSONArray array) {

        if (array == null) {
            return Collections.EMPTY_SET;
        }

        Set<Long> result = new HashSet<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            result.add(array.getLong(i));
        }

        return result;
    }

    /**
     * 根据指定Key遍历json array，返回对应的对象
     *
     * @param jsonArray
     * @param keyName
     * @param keyValue
     *
     * @return
     */
    public static JSONObject retrieveObjectFromArray(JSONArray jsonArray,
                                                     String keyName,
                                                     String keyValue) {

        if (jsonArray != null && keyName != null && keyValue != null) {
            for (int j = 0, arrayLength = jsonArray.size(); j < arrayLength;
                 j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String theKeyValue = jsonObject.getString(keyName);
                if (keyValue.equals(theKeyValue)) {
                    return jsonObject;
                }
            }
        }
        return null;
    }

    /**
     * map转换
     */
    public static <K, V> Map<K, V> parseToMap(String json,
                                              Class<K> keyType,
                                              Class<V> valueType) {

        return JSON.parseObject(json,
                                new TypeReference<Map<K, V>>(keyType,
                                                             valueType) {
                                });
    }

    /**
     * 判断2个JSON是否一致
     * 对于JSONObject, 各个key:value可以是无序的, 比如 {"a":1,"b":2} 和 {"b":2, "a":1} 认为是一致的
     * 对于JSONArray, 各个element是有序的, 比如 ["a","b"] 和 ["b","a"] 认为是不一致的
     *
     * @param o1
     * @param o2
     *
     * @return
     */
    public static boolean isSame(Object o1, Object o2) {

        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null) {
            return true;
        }
        if (o1.getClass() != o2.getClass()) {
            return false;
        }
        if (o1 instanceof JSONObject) {
            JSONObject jo1 = (JSONObject)o1;
            JSONObject jo2 = (JSONObject)o2;
            if (jo1.size() != jo2.size()) {
                return false;
            }
            for (String key : jo1.keySet()) {
                Object f1 = jo1.get(key);
                Object f2 = jo2.get(key);
                if (!isSame(f1, f2)) {
                    return false;
                }
            }
        }
        if (o1 instanceof JSONArray) {
            JSONArray ja1 = (JSONArray)o1;
            JSONArray ja2 = (JSONArray)o2;
            if (ja1.size() != ja2.size()) {
                return false;
            }
            for (int i = 0, size = ja1.size(); i < size; i++) {
                Object f1 = ja1.get(i);
                Object f2 = ja2.get(i);
                if (!isSame(f1, f2)) {
                    return false;
                }
            }
        }
        return o1.equals(o2);
    }
}
