package io.danielpine.tools.console;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Hyleon
 * @Component Terminal Table
 */
public class TT<T> {
    private TT(Collection<T> data) {
        this.data = data;
    }

    private final Collection<T> data;

    public static <T> TT<T> data(Collection<T> data) {
        return new TT<>(data);
    }

    public void display() {
        display(System.out);
    }

    public void display(OutputStream outputStream) {
        try {
            outputStream.write(tablefy().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String tablefy() {
        if (data == null || data.size() == 0) {
            return "============== NO DATA ==============";
        }
        // fields
        Field[] declaredFields = data.stream().findFirst().get().getClass().getDeclaredFields();

        Map<Field, Integer> lenMap = data.stream()
                .map(d -> Arrays.stream(declaredFields)
                        .collect(Collectors.toMap(Function.identity(), field -> {
                            field.setAccessible(true);
                            try {
                                Object o = field.get(d);
                                return o == null ? 0 : o.toString().length();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            return 0;
                        })))
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, en -> Objects.requireNonNull(en.getValue().stream()
                        .max(Map.Entry.comparingByValue())
                        .orElse(null)).getValue()));
        StringBuilder result = new StringBuilder();

        // center
        String center = genCenter(lenMap);

        // top 3
        result.append(genFirst(lenMap)).append("\n");
        result.append(genTopic(lenMap)).append("\n");
        result.append(center).append("\n");

        Iterator<T> iterator = data.iterator();
        while (iterator.hasNext()) {
            result.append("│").append(toData(iterator.next(), lenMap.entrySet())).append("\n");
            if (iterator.hasNext()) {
                result.append(center).append("\n");
            }
        }
        result.append(genLast(lenMap)).append("\n");
        return result.toString();
    }

    private String genFirst(Map<Field, Integer> lenMap) {
        StringBuilder sbFirst = new StringBuilder("┌");
        lenMap.entrySet().forEach(s -> sbFirst.append(toCnt(s)).append("┬"));
        return sbFirst.substring(0, sbFirst.length() - 1) + "┐";
    }

    private String genCenter(Map<Field, Integer> lenMap) {
        StringBuilder sbCenter = new StringBuilder("├");
        lenMap.entrySet().forEach(s -> sbCenter.append(toCnt(s)).append("┼"));
        return sbCenter.substring(0, sbCenter.length() - 1) + "┤";
    }

    private String genLast(Map<Field, Integer> lenMap) {
        StringBuilder sbLast = new StringBuilder("└");
        lenMap.entrySet().forEach(s -> sbLast.append(toCnt(s)).append("┴"));
        return sbLast.substring(0, sbLast.length() - 1) + "┘";
    }

    private String genTopic(Map<Field, Integer> lenMap) {
        StringBuilder sbTopic = new StringBuilder("│");
        lenMap.entrySet().forEach(s -> sbTopic.append(toTopic(s)).append("│"));
        return sbTopic.toString();
    }

    private String toCnt(Map.Entry<Field, Integer> s) {
        return StringUtils.repeat("─", Math.max(s.getValue(), s.getKey().getName().length()));
    }

    private String toTopic(Map.Entry<Field, Integer> s) {
        StringBuilder key = new StringBuilder(s.getKey().getName().toUpperCase(Locale.ROOT));
        key.append(StringUtils.repeat(" ", Math.max(Math.max(s.getValue(), key.length()) - key.length(), 0)));
        return key.toString();
    }

    private String toData(T d, Set<Map.Entry<Field, Integer>> entrySet) {
        StringBuilder sb = new StringBuilder();
        entrySet.forEach(k -> {
            try {
                String v = k.getKey().get(d) == null ? "" : k.getKey().get(d).toString();
                sb.append(v).append(StringUtils.repeat(" ", Math.max(getMax(k.getValue(), v.length(), k.getKey().getName().length()) - v.length(), 0))).append("│");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return sb.toString();
    }

    private int getMax(int... arr) {
        int max = 0;
        for (int j : arr) {
            if (j > max) {
                max = j;
            }
        }
        return max;
    }
}