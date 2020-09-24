package com.njuse.jvmfinal.memory;

import com.njuse.jvmfinal.runtime.data.JObject.JObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class JHeap {
    private static int maxSize = 500;
    private static int currentSize = 0;
    private static JHeap jHeap = new JHeap();
    private static Set<JObject> objects;


    private static Map<Integer, JObject> refToObject = new HashMap<>();


    private JHeap(){
        objects = new LinkedHashSet<>();
    }
    public static JHeap getInstance(){return jHeap;}

    public void addObject (JObject object){
        if(currentSize >= maxSize) throw new OutOfMemoryError();
        objects.add(object);
        refToObject.put(object.getIndexInHeap(), object);
        currentSize++;
    }
}
