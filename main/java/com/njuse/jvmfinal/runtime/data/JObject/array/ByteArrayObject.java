package com.njuse.jvmfinal.runtime.data.JObject.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ByteArrayObject extends ArrayObject {
    private byte[] array;

    public ByteArrayObject(int len, String type, JClass clazz) {
        super(len, type ,clazz);
        array = new byte[len];
    }
}
