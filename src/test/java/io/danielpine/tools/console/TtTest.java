package io.danielpine.tools.console;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

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

        long bf = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            TableBuilder.newBuilder().display(students);
        }
        long af = System.currentTimeMillis() - bf;

        long bf2 = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            TT.data(students).display();
        }
        long af2 = System.currentTimeMillis() - bf2;

        System.out.println("TableBuilder Times:" + times + " spend:" + af);
        System.out.println("TT           Times:" + times + " spend:" + af2);

        TT<Student> data = TT.data(students);

        // 1. tablefy() returns a tablefied string
        System.out.println(data.tablefy());

        // 2. directly display (default output stream: System.out)
        data.display();

        // 3. write to an output stream
        try {
            data.display(new FileOutputStream("table.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
