package io.danielpine.tools.console;

import org.junit.Test;

import java.util.Arrays;

public class TableBuilderTest {

    @Test
    public void display() {
        TableBuilder.newBuilder().display(
                Arrays.asList(
                        new User("小雨点", "Rain", "China"),
                        new User("Daniel Pine", "Pine", "中国")
                )
        );
    }
}