package com.experience;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by fj on 2022/2/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j      //替代对象实例化的注解
public class LoggerTest {

    @Test
    public void test(){
        String name = "little dust";
        String password = "123456";
        //两种Log.info()效果相同
        log.info("name: " + name + ", " + "password: " + password);
        //输出时可以使用{}作为占位符，在后面按顺序给出占位符代表的对象
        log.info("name: {}, password: {}", name , password);
        log.debug("debug...");
        log.error("error...");
    }
}

