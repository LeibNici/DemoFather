package com.Test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.metal.MetalIconFactory;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenming
 * @description
 * @create: 2022-03-28
 */
@SpringBootTest
@Slf4j
public class Test20220328 {
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
    @Test
    void test() throws ParseException {
        List<DateDemo> list = new ArrayList<>();
        Date date = DateUtils.parseDate("2022-03-26 00:00:00", parsePatterns);
        for (int i = 0; i < 30; i++) {
            DateDemo one = new DateDemo();
            one.setId(Long.valueOf(i));
            one.setRecordDate(DateUtils.addMinutes(date,i));
            list.add(one);
        }
        Date start = DateUtils.parseDate("2022-03-26 00:09:00", parsePatterns);
        Date end = DateUtils.parseDate("2022-03-26 00:19:00", parsePatterns);
        List<DateDemo> collect = list.stream().filter(dateDemo -> dateDemo.getRecordDate().after(start) && dateDemo.getRecordDate().before(end)).collect(Collectors.toList());

        log.info("123");
    }

    @Data
    class DateDemo{
        private Long id;
        private Date recordDate;
    }

}
