package io.danielpine.tools.console;

import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.Ansi;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.fusesource.jansi.Ansi;

public class TableBuilder {
    List<String> headers = new ArrayList<>();
    List<String> skipFields = new ArrayList<>();
    boolean autoIndex = false;
    boolean ansi = false;
    Ansi.Color headBackground = Ansi.Color.BLUE;
    Ansi.Color bodyBackgroundOdd = Ansi.Color.BLACK;
    Ansi.Color bodyBackgroundEven = Ansi.Color.WHITE;

    public static TableBuilder newBuilder() {
        return new TableBuilder();
    }

    public TableBuilder setHeadBackground(Ansi.Color headBackground) {
        this.headBackground = headBackground;
        return this;
    }

    public TableBuilder setBodyBackgroundOdd(Ansi.Color bodyBackgroundOdd) {
        this.bodyBackgroundOdd = bodyBackgroundOdd;
        return this;
    }

    public TableBuilder setBodyBackgroundEven(Ansi.Color bodyBackgroundEven) {
        this.bodyBackgroundEven = bodyBackgroundEven;
        return this;
    }

    public TableBuilder enableAutoIndex() {
        autoIndex = true;
        return this;
    }

    public TableBuilder enableAnsi() {
        ansi = true;
        return this;
    }

    public <T> void display(List<T> dous) {
        if (Objects.isNull(dous) || dous.isEmpty()) {
            System.out.println("===No Data to Display===");
            return;
        }
        List<Field> fields = Stream.of(dous.get(0).getClass().getDeclaredFields())
                .filter(f -> !skipFields.contains(f.getName()))
                .collect(Collectors.toList());
        List<String> headers = fields.stream().map(Field::getName).map(StringUtils::capitalize).collect(Collectors.toList());
        Table table = this.addHeaders(headers).enableAutoIndex().build();
        dous.stream().map(dou -> fields
                .stream()
                .map(f -> {
                    try {
                        f.setAccessible(true);
                        return String.valueOf(f.get(dou));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return "-";
                    }
                })
                .collect(Collectors.toList())
        ).forEach(table::put);
        table.display();
    }

    public TableBuilder addHeaders(List<String> headers) {
        this.headers.addAll(headers);
        return this;
    }

    public TableBuilder addHeaders(String... headers) {
        return addHeaders(Arrays.asList(headers));
    }

    public Table build() {
        Table table = new Table();
        table.setHeaders(headers);
        table.setAnsi(ansi);
        table.setAutoIndex(autoIndex);
        table.setHeadBackground(headBackground);
        table.setBodyBackgroundOdd(bodyBackgroundOdd);
        table.setBodyBackgroundEven(bodyBackgroundEven);
        return table;
    }

    private enum LineType {
        Head,
        Body
    }

    private class Table {
        List<String> headers;
        List<List<String>> rows = new ArrayList<>();
        boolean autoIndex = false;
        boolean ansi = false;
        int lines = 1;
        Ansi.Color headBackground = Ansi.Color.BLUE;
        Ansi.Color bodyBackgroundOdd = Ansi.Color.BLACK;
        Ansi.Color bodyBackgroundEven = Ansi.Color.WHITE;

        public void setLines(int lines) {
            this.lines = lines;
        }

        public void setHeadBackground(Ansi.Color headBackground) {
            this.headBackground = headBackground;
        }

        public void setBodyBackgroundOdd(Ansi.Color bodyBackgroundOdd) {
            this.bodyBackgroundOdd = bodyBackgroundOdd;
        }

        public void setBodyBackgroundEven(Ansi.Color bodyBackgroundEven) {
            this.bodyBackgroundEven = bodyBackgroundEven;
        }

        public boolean isAutoIndex() {
            return autoIndex;
        }

        public void setAutoIndex(boolean autoIndex) {
            this.autoIndex = autoIndex;
        }

        public boolean isAnsi() {
            return ansi;
        }

        public void setAnsi(boolean ansi) {
            this.ansi = ansi;
        }

        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(List<String> headers) {
            this.headers = headers;
        }

        public List<List<String>> getRows() {
            return rows;
        }

        public void setRows(List<List<String>> rows) {
            this.rows = rows;
        }

        public Table put(List<String> cols) {
            this.rows.add(cols);
            lines++;
            return this;
        }

        public Table put(String... cols) {
            this.rows.add(Arrays.asList(cols));
            return this;
        }

        private boolean isChinese(int ch) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
            return Stream.of(
                    Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
                    Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS,
                    Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A,
                    Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B,
                    Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION,//中文句号
                    Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS,//中文逗号
                    Character.UnicodeBlock.GENERAL_PUNCTUATION//中文引号
            ).anyMatch(ub::equals);
        }

        private int absoluteWidth(String text) {
            return text.length() + countChinese(text);
        }

        private int countChinese(String text) {
            return (int) text.chars().filter(this::isChinese).count();
        }

        public List<Integer> computeMaxColumnLength() {
            ArrayList<Integer> record = new ArrayList<>();
            for (int i = 0; i < headers.size(); i++) {
                record.add(absoluteWidth(headers.get(i)));
            }
            for (List<String> cols : rows) {
                for (int i = 0; i < cols.size(); i++) {
                    int len = absoluteWidth(cols.get(i));
                    if (len > record.get(i)) {
                        record.set(i, len);
                    }
                }
            }
            return record;
        }

        StringBuilder context = new StringBuilder();

        private void appendLine(String text) {
            context.append(text).append("\n");
        }

        int bodyCount = 0;

        private void appendBody(String text) {
            if (autoIndex) {
                text = String.format("|%-" + String.valueOf(lines).length() + "d", bodyCount) + text;
            }
            appendLine(text, bodyCount % 2 == 0 ? bodyBackgroundEven : bodyBackgroundOdd);
            bodyCount++;
        }

        private void appendHead(String text) {
            if (autoIndex) {
                text = String.format("|%-" + String.valueOf(lines).length() + "s", "#") + text;
            }
            appendLine(text, headBackground);
        }

        private void appendLine(String text, Ansi.Color color) {
            if (ansi) {
                text = Ansi.ansi().eraseScreen().bg(color).a(text).reset().toString();
            }
            context.append(text).append("\n");
        }

        public void display() {
            List<Integer> record = computeMaxColumnLength();
            StringBuilder borderBuilder = new StringBuilder();
            for (int i = 0; i < headers.size(); i++) {
                borderBuilder.append("+-" + StringUtils.repeat("-", record.get(i) + 2));
            }
            String border = borderBuilder.append("+").toString();
            StringBuilder tplBuilder = new StringBuilder();
            for (int i = 0; i < headers.size(); i++) {
                tplBuilder.append("| %-" + (record.get(i) + 2) + "s");
            }
            String tpl = tplBuilder.append("|").toString();
            if (!ansi) appendLine(border);
            appendHead(String.format(tpl, headers.toArray()));
            if (!ansi) appendLine(border);
            for (List<String> cols : rows) {
                StringBuilder tplColBuilder = new StringBuilder();
                for (int i = 0; i < headers.size(); i++) {
                    String col = cols.get(i);
                    int countChinese = countChinese(col);
                    tplColBuilder.append("| %-").append(record.get(i) + 2 - countChinese).append("s");
                }
                String tplCol = tplColBuilder.append("|").toString();
                appendBody(String.format(tplCol, cols.toArray()));
            }
            if (!ansi) appendLine(border);
            print();
        }

        private void print() {
            System.out.println(context.toString());
        }
    }

    public TableBuilder skipFields(String... fields) {
        skipFields.addAll(Arrays.asList(fields));
        return this;
    }
}
