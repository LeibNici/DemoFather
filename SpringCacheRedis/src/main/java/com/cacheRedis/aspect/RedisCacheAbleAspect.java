package com.cacheRedis.aspect;

import com.cacheRedis.annotation.RedisCacheAble;
import com.cacheRedis.redis.service.RedisService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.tree.TreeNode;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedisCacheAbleAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.cacheRedis.annotation.RedisCacheAble)")
    public void redisCacheAble() {
    }

    @Pointcut("@annotation(com.cacheRedis.annotation.RedisCacheEvict)")
    public void redisCacheEvict() {
    }

    @Around("redisCacheAble()")
    public Object putRedis(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;

        //获取当前方法
        Method method = getMethod(joinPoint);
        //获取当前方法上的指定注解
        RedisCacheAble annotation = method.getAnnotation(RedisCacheAble.class);
        //查看redis中是否存在该返回值
        Object redisResult = redisService.getCacheObject(annotation.key());
        //存在则直接取缓存中的值
        if (redisResult != null) {
            result = redisResult;
        } else {
            //不存在先执行当前方法，并执行存入缓存操作
            result = joinPoint.proceed();

            /**
             * 由于BeanUtils.copyProperties无法处理list和arrylist类型，手动转换对象
             */
            if (result.getClass() == ArrayList.class) {
                List list = new ArrayList();
                for (Object o : (List) result) {
                    Object target = annotation.transfer().newInstance();
                    BeanUtils.copyProperties(o, target);
                    list.add(target);
                }
                redisService.setCacheObject(annotation.key(), list, annotation.expireTime(), annotation.timeUnit());
            } else if (result.getClass() == HashMap.class) {
                //手动转换Map嵌套的对象
                Map<Object, Object> sourseMap = (Map) result;
                Map<Object, Object> resultMap = new HashMap<>();
                for (Object key : sourseMap.keySet()) {
                    Object target = annotation.transfer().newInstance();
                    BeanUtils.copyProperties(sourseMap.get(key), target);
                    resultMap.put(key, target);
                }
                redisService.setCacheObject(annotation.key(), resultMap, annotation.expireTime(), annotation.timeUnit());
            } else {
                redisService.setCacheObject(annotation.key(), result, annotation.expireTime(), annotation.timeUnit());
            }
        }

        return result;
    }

    @Around("redisCacheEvict()")
    public Object removeRedis(ProceedingJoinPoint joinPoint) {
        return null;
    }

    public Method getMethod(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

    private class target {
    }
}
