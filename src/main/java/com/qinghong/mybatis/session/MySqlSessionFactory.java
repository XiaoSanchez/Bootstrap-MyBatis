package com.qinghong.mybatis.session;

import com.qinghong.mybatis.executor.MyExecutor;
import com.qinghong.mybatis.mapping.MyConfiguration;

public class MySqlSessionFactory {

    private MyConfiguration myConfiguration;

    public MySqlSessionFactory() {
    }

    public MySqlSessionFactory(MyConfiguration myConfiguration) {
        this.myConfiguration = myConfiguration;
    }

    public MySqlSession openSession() {
        MyExecutor executor = new MyExecutor(myConfiguration);
        return new MySqlSession(myConfiguration,executor);
    }
}
