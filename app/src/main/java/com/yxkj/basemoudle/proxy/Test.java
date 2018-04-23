package com.yxkj.basemoudle.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by 曾强 on 2018/3/1.
 */

public class Test {
    public static void main(String args[]) {
        IUserLogin userLogin = (IUserLogin) Proxy.newProxyInstance(IUserLogin.class.getClassLoader(), new Class[]{IUserLogin.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("Method= " + method + " 参数= " + Arrays.toString(args));
                return null;
            }
        });
        userLogin.login("曾强", "123456");
    }
}
