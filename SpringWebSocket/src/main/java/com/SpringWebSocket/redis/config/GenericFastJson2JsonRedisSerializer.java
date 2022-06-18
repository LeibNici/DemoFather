package com.SpringWebSocket.redis.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class GenericFastJson2JsonRedisSerializer implements RedisSerializer<Object> {

    static final byte[] EMPTY_ARRAY = new byte[0];

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    public byte[] serialize(@Nullable Object source) throws SerializationException {
        if (source == null) {
            return EMPTY_ARRAY;
        } else {
            try {
                return JSON.toJSONBytes(source, SerializerFeature.WriteClassName);
            } catch (Exception var3) {
                throw new SerializationException("Could not write JSON: " + var3.getMessage(), var3);
            }
        }
    }

    public Object deserialize(@Nullable byte[] source) throws SerializationException {
        if (isEmpty(source))
            return null;
        return JSON.parse(source);
    }

    static boolean isEmpty(@Nullable byte[] data) {
        return data == null || data.length == 0;
    }

}
