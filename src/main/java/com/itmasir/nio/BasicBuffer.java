package com.itmasir.nio;

import java.nio.IntBuffer;

/**
 * 举例说明buffer 在nio中的使用 
 */
public class BasicBuffer {

    public static void main(String[] args) {

        //创建一个大小为5的buffer
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        //如何从buffer读取数据
        //将buffer进行读写切换
        intBuffer.flip();
        //如果还有值
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
