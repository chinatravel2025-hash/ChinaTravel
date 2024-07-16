package com.example.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {



    /**
     * 直接获取对象属性值
     * @param obj			对象实例
     * @param fieldName		属性名称
     * @return
     */
    public static Object getFieldValue (Object obj, String fieldName) {
        if (obj == null) return null;

        Object value = null;
        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = field.get(obj);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return value;
    }


    /**
     * 直接获取对象属性值
     * @param obj			对象实例
     * @param fieldName		属性名称
     * @return
     */
    public static void setFiledValue (Object obj, String fieldName,Object newV) {
        if (obj == null) return ;

        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj,newV);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }



    public static Method getMethod(Class cls, String methodName,Class<?> ...parameterTypes){

        try{
            Method method = cls.getDeclaredMethod(methodName,parameterTypes);
            method.setAccessible(true);
            return method;
        }catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 反射调用某个方法.
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param parameters
     */
    public static Object methodInvoke(Object obj,String methodName,Class<?> [] parameterTypes,Object [] parameters){

        try {
           return getMethod(obj.getClass(),methodName,parameterTypes)
                    .invoke(obj,parameters);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }


}
