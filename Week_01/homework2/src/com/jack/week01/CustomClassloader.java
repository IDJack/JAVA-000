package com.jack.week01;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内
 * 容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。
 */
public class CustomClassloader extends ClassLoader {
    private static final String CLASS_FILE_PATH = "src/com/jack/week01/Hello.xlass";
	
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> clz = new CustomClassloader().findClass("Hello");
        Method helloMethod = clz.getMethod("hello");
        helloMethod.invoke(clz.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = new byte[0];
        try {
            classBytes = loadClassBytes(CLASS_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassBytes(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(255 - bytes[i]);
        }
        return bytes;
    }
}
