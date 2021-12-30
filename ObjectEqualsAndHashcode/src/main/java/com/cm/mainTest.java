package com.cm;

import com.cm.domian.User;
import com.cm.domian.UserLombok;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class mainTest {

    public static void main(String[] args) {

        User user1 = new User();
        user1.setUsername("chenmign");
        user1.setDate(new Date());
        user1.setAge(123);

        User user2 = new User();
        user2.setUsername("chenmign");
        user2.setAge(123);

        log.info("手写equals和hashcode  " + user1.equals(user2));

        UserLombok user3 = new UserLombok();
        user3.setUsername("chenmign");
        user3.setDate(new Date());
        user3.setAge(123);

        UserLombok user4 = new UserLombok();
        user4.setUsername("chenmign");
        user4.setAge(123);

        log.info("使用lombok + exclude排除比较属性  " + user3.equals(user4));

    }

}
