package com.njuse.jvmfinal.runtime.data.JObject.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharArrayObject extends ArrayObject {
    private char[] array;
    public CharArrayObject(int len, String type, JClass clazz) {
        super(len, type, clazz);
        array = new char[len];
    }
}
