package com.cm.domian;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Objects;

@Data
public class UserLombok {

    private String name;

    private Integer age;

    @EqualsAndHashCode.Exclude
    private Date date;
}
