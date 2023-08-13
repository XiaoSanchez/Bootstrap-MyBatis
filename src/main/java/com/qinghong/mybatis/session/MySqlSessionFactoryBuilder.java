package com.qinghong.mybatis.session;

import com.qinghong.mybatis.mapping.MyConfiguration;
import com.qinghong.mybatis.parsing.XMLConfigBuilder;

import java.io.InputStream;

public class MySqlSessionFactoryBuilder {

    public MySqlSessionFactory build(InputStream inputStream) {
        MyConfiguration myConfiguration = new XMLConfigBuilder(inputStream).parse();
        return new MySqlSessionFactory(myConfiguration);
    }
}
