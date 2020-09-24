package com.njuse.jvmfinal.runtime.data.JObject.array;

import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import com.njuse.jvmfinal.runtime.data.JObject.JObject;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefArrayObject extends ArrayObject {
    private JObject[] array;
    public RefArrayObject(int len, String type, JClass clazz) {
        super(len, type, clazz);
        array = new JObject[len];

    }

}
