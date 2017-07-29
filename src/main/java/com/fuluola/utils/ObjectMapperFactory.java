package com.fuluola.utils;

import java.text.SimpleDateFormat;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/** 
 * @description 
 * @author  fuzhuan fu.luola@qq.com
 * @date 2017年6月26日 
 */
public class ObjectMapperFactory {

    public static final ObjectMapper JSON = new ObjectMapper();

    static {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        SimpleModule module = new SimpleModule();
        builder.modules(module);

        // json
        JSON.registerModule(module);
        JSON.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); // 1.8 and above
        JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSON.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        builder.configure(JSON);
    }
}
