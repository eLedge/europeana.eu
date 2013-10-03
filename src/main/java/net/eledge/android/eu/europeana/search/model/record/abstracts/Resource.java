package net.eledge.android.eu.europeana.search.model.record.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.lang.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {

    public String about;

    public static Map<String, String[]> mergeMapArrays(Map<String, String[]> source1, Map<String, String[]> source2) {
        Map<String, String[]> merged = new HashMap<String, String[]>();
        if (source1 != null) {
            merged.putAll(source1);
        } else {
            return source2;
        }
        if (source2 != null) {
            for (String key: source2.keySet()) {
                if (merged.containsKey(key)) {
                    merged.put(key, mergeArray(merged.get(key), source2.get(key)));
                } else {
                    merged.put(key, source2.get(key));
                }
            }
        }
        return merged;
    }

    public static <T> T[] mergeArray(T[] array1, T[] array2) {
        return (T[]) ArrayUtils.addAll(array1, array2);
    }

    public static String[] getPreferred(Map<String, String[]> data, String locale) {
        String key = null;
        if ((locale != null) && data.containsKey(locale)) {
            key = locale;
        } else
        if (data.containsKey("def")) {
            key = "def";
        } else
        if (data.containsKey("en")) {
            key = "en";
        }
        if (key == null) {
            key = data.keySet().iterator().next();
        }
        return data.get(key);
    }

    public static <T> T defaultValue(T value1, T value2) {
        if (value1 != null) {
            return value1;
        }
        return value2;
    }

}