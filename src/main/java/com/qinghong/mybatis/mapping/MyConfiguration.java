package com.qinghong.mybatis.mapping;

import lombok.Data;

import java.util.Map;

@Data
public class MyConfiguration {

    private MyEnvironment myEnvironment;

    private Map<String, MyMapperStatement> mapperStatement;
}
