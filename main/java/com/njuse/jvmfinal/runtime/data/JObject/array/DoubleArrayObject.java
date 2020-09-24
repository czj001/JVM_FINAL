package com.njuse.jvmfinal.runtime.data.JObject.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoubleArrayObject extends ArrayObject {
    private double[] array;

    public DoubleArrayObject(int len, String type, JClass clazz) {
        super(len, type, clazz);
        array = new double[len];
    }
}
