package com.qinghong.mybatis.mapping;

import lombok.Data;

@Data
public class MyMapperStatement {

    private String namespace;

    private String id;

    private String parameterType;

    private String resultType;

    private String sql;
}
