package com.cacheRedis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCacheAble {

    //缓存key
    public String key();

    //缓存转换对象
    public Class transfer() default Object.class;

    //缓存时间
    public long expireTime() default 5;

    //缓存时间单位
    public TimeUnit timeUnit() default TimeUnit.SECONDS;

}
