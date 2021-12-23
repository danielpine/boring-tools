package io.danielpine.tools.console;

import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void tt() {
        List<User> users = Arrays.asList(
                new User("小雨点", "Rain", "China"),
                new User("Daniel Pine", "Pine", "中国")
        );
        StopWatch stopwatch = new StopWatch();
        stopwatch.start("0");
        stopwatch.stop();
        stopwatch.start("tt");
        TT<User> data = TT.data(users);
        data.display();
        stopwatch.stop();
        stopwatch.start("tb");
        TableBuilder.newBuilder().display(users);
        stopwatch.stop();

        System.out.println(stopwatch.prettyPrint());
    }
}