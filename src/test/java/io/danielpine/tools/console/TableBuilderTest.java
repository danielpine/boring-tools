package io.danielpine.tools.console;

import org.fusesource.jansi.Ansi;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableBuilderTest {

    private static final Logger logger = LoggerFactory.getLogger(TableBuilderTest.class);


    List<User> users = Arrays.asList(
            new User("123", "Rain", "China"),
            new User("Daniel Pine", "Pine", "22")
    );

    List<Map<String, Object>> maps = Arrays.asList(
            new HashMap<String, Object>() {
                {
                    put("a", "1");
                    put("b", "2");
                    put("c", "3");
                    put("d", "4");
                }
            });


    @Test
    public void getContent() {
        String content = TableBuilder.newBuilder()
                                     .enableAutoIndex()
                                     .data(users)
                                     .getContent();
        logger.info(content);
    }


    @Test
    public void display() {
        TableBuilder.newBuilder()
                    .enableAnsi()
                    .enableAutoIndex()
                    .setHeadBackground(Ansi.Color.CYAN)
                    .setBodyBackgroundOdd(Ansi.Color.YELLOW)
                    .setBodyBackgroundEven(Ansi.Color.WHITE)
                    .display(users);
    }

    @Test
    public void displayMaps() {
        String content = TableBuilder.newBuilder()
                                     .enableAnsi()
                                     .enableAutoIndex()
                                     .setHeadBackground(Ansi.Color.CYAN)
                                     .setBodyBackgroundOdd(Ansi.Color.YELLOW)
                                     .setBodyBackgroundEven(Ansi.Color.WHITE)
                                     .data(maps)
                                     .getContent();
        System.out.println(content);
    }

    @Test
    public void tt() {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start("0");
        stopwatch.stop();
        stopwatch.start("tb");
        TableBuilder.newBuilder().display(users);
        stopwatch.stop();
        stopwatch.start("tt");
        TT<User> data = TT.data(users);
        data.display();
        stopwatch.stop();
        System.out.println(stopwatch.prettyPrint());
    }
}