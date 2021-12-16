package model;

import lombok.extern.slf4j.Slf4j;

/**
 * @author deer
 * @date 2021-07-17
 */
@Slf4j
public class Simple {
    public void test() {
        System.out.println("hello world");
    }

    public String test(String msg) {
        return msg;
    }
}
