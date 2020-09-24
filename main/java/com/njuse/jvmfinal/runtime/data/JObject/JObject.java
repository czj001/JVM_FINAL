package com.njuse.jvmfinal.runtime.data.JObject;

import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class JObject {
    //一是从根据引用直接或间接地查找到对象在Java堆中的数据存放的起始地址或索引
    //二是根据引用直接或间接地查找到对象所属数据类型在方法区中的存储的类型信息

    protected static int sumInHeap; // representing the sum of objects in heap
    protected int indexInHeap; //对应第一点
                      // the index of the current JObject in heap
    protected JClass clazz; // 对应第二点


    //构建NULL
    protected boolean isNull = false;

    static {
        sumInHeap = 0;
    }

    public JObject() {
        indexInHeap = sumInHeap;
    }

    public boolean isInstanceOf(JClass clazz){
        return this.clazz.determineInstance(clazz);
    }
}
