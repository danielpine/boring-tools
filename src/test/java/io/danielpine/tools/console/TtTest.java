package io.danielpine.tools.console;


import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.util.StopWatch;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Hyleon
 */
public class TtTest {

    @Test
    public void testTT() {
        List<Student> students = Arrays.asList(
                new Student("ZhangSan", 11, 3, false, true),
                new Student("Lisi", 11, 555556666, false, false),
                new Student("王五", 999, 123, true, false)
        );
        int times = 10000;
        StopWatch stopwatch = new StopWatch();
        stopwatch.start("TT");
        for (int i = 0; i < times; i++) {
            TT.data(students).display();
        }
        stopwatch.stop();
        stopwatch.start("TB");
        for (int i = 0; i < times; i++) {
            TableBuilder.newBuilder().display(students);
        }
        stopwatch.stop();
        System.out.println(stopwatch.prettyPrint());
    }
}
