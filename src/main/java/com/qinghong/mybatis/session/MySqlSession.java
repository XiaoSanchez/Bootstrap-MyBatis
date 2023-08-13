package com.qinghong.mybatis.session;

import com.qinghong.mapper.UUserInfoMapper;
import com.qinghong.mybatis.executor.MyExecutor;
import com.qinghong.mybatis.mapping.MyConfiguration;
import com.qinghong.mybatis.mapping.MyMapperStatement;
import com.qinghong.mybatis.proxy.MapperProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

public class MySqlSession {

    private MyConfiguration myConfiguration;

    private MyExecutor myExecutor;

    public MySqlSession(MyConfiguration myConfiguration, MyExecutor myExecutor) {
        this.myConfiguration = myConfiguration;
        this.myExecutor = myExecutor;
    }

    public <T> T getMappwe(Class<T> clazz) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz}, mapperProxy);
    }

    public <T> T selectOne(String statementKey,Object[] args) {
        MyMapperStatement  mapperStatement = myConfiguration.getMapperStatement().get(statementKey);
        List<T> resultList = myExecutor.query(mapperStatement, args);
        if (resultList != null && resultList.size()>1){
            throw new RuntimeException("more than one");
        }else {
            return resultList.get(0);
        }
    }

    public void selectList(Object[] args) {
    }
}
