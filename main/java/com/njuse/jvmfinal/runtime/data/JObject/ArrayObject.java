package com.njuse.jvmfinal.runtime.data.JObject;


import com.njuse.jvmfinal.memory.jclass.JClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrayObject extends JObject{

    protected int len;
    protected String type;

    public ArrayObject(int len, String type, JClass clazz) {
        if (len < 0) throw new NegativeArraySizeException();
        this.len = len;
        this.type = type;
        this.clazz = clazz;
        sumInHeap++;
    }
}
