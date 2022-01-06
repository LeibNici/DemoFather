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

        Method method = getMethod(joinPoint);
        RedisCacheAble annotation = method.getAnnotation(RedisCacheAble.class);

        Object redisResult = redisService.getCacheObject(annotation.key());

        if (redisResult != null) {
            result = redisResult;
        } else {
            result = joinPoint.proceed();
            if (annotation.transfer() != Object.class) {
                Object target = annotation.transfer().newInstance();
                BeanUtils.copyProperties(result, target);
                redisService.setCacheObject(annotation.key(), target, annotation.expireTime(), annotation.timeUnit());
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

}
