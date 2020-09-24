package com.njuse.jvmfinal.runtime.data.JObject.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LongArrayObject extends ArrayObject {
    private long[] array;

    public LongArrayObject(int len, String type, JClass clazz) {
        super(len, type, clazz);
        array = new long[len];
    }

}
