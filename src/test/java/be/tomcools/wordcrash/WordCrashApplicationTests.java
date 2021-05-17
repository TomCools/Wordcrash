package be.tomcools.wordcrash;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.awt.event.KeyEvent;

@SpringBootTest
class WordCrashApplicationTests {
    static {
        System.setProperty("java.awt.headless", "false");
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testKeyPress() throws Exception {
        final Robot robot = new Robot();

        for (int i = 0; i < 1000; i++) {
            robot.keyPress(KeyEvent.VK_M);
            Thread.sleep(100);
            System.out.println("typing!");
        }

    }


}
