package cn.nh.kevin.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Test
    public void testStream() {
        List<String> list = Arrays.asList("one","two","three", "four","five");
        list.stream().filter(str -> str.length() > 3).forEach(System.out::println);
    }
}
