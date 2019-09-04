package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dragon.cl 2019/9/4 5:48 PM
 */
public class CollectionUtils {

    private CollectionUtils() {}

    public static boolean isEmpty(Map map) {

        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Collection list) {

        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {

        return map != null && !map.isEmpty();
    }

    public static boolean isNotEmpty(Collection list) {

        return list != null && !list.isEmpty();
    }

    public static boolean isNotEmpty(Object[] array) {

        return array != null
            && array.length > 0;
    }

    public static void removeDuplicate(List list) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }

    public static boolean hasDiffInSet(Set set1, Set set2) {

        if (set1 == null || set2 == null) {
            return true;
        }

        Set tmp1 = new HashSet(set1.size());
        tmp1.addAll(set1);
        tmp1.removeAll(set2);
        if (tmp1.size() > 0) {
            return true;
        }

        Set tmp2 = new HashSet(set2.size());
        tmp2.addAll(set2);
        tmp2.removeAll(set1);
        if (tmp2.size() > 0) {
            return true;
        }

        return false;
    }

    public static boolean hasCommonInSet(Set<String> set1, Set<String> set2) {

        if (set1 == null || set2 == null) {
            return false;
        }
        Set<String> tmp = new HashSet<>(set1.size());
        tmp.addAll(set1);
        tmp.retainAll(set2);
        return !tmp.isEmpty();
    }

    public static List<String> addAll(List<String> set1, List<String> set2) {

        List<String> tmp = new ArrayList<>();
        if (set1 != null) {
            tmp.addAll(set1);
        }
        if (set2 != null) {
            tmp.addAll(set2);
        }
        return tmp;
    }

    public static Set<String> removeEmpty(Set<String> from) {

        if (from == null) {
            return null;
        }
        Set<String> to = new HashSet<>();
        for (String ele : from) {
            if (StringUtils.isNotEmpty(ele)) {
                to.add(ele);
            }
        }
        return to;
    }

    public static Set<String> removeAll(Set<String> set1, Set<String> set2) {

        Set<String> tmp = new HashSet<>(set1.size());
        tmp.addAll(set1);
        tmp.removeAll(set2);
        return tmp;
    }

    public static int countValueSize(Map<String, List<String>> map) {

        int count = 0;
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> value = entry.getValue();
            if (value != null) {
                count += value.size();
            }
        }
        return count;
    }

}
