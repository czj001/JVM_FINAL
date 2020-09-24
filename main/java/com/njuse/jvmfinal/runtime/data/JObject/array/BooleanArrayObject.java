package com.njuse.jvmfinal.runtime.data.JObject.array;


import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooleanArrayObject extends ArrayObject {
    private boolean[] array;

    public BooleanArrayObject(int len, String type, JClass clazz){
        super(len,type,clazz);
        array = new boolean[len];
    }
}
