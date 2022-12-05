import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyTest {
    static final File file = new File("C://test_java", "out.txt");

    @Test
    public void check() throws IOException, InterruptedException {
        String str = "";
        int num = 10;
        MyThread.startThread(num);
        Thread.sleep(1000);
        Assert.assertEquals(String.valueOf(num), MyThread.readFile());
    }
}
