package com.njuse.jvmfinal.runtime.data.JObject;

import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.Vars;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NonArrayObject extends JObject {

    private Vars instanceFields;

    public NonArrayObject(JClass clazz){
        assert clazz != null;
        this.clazz = clazz;
        sumInHeap++;
        instanceFields = new Vars(clazz.getInstanceSlotCount());
        initDefaultValue(clazz); //
    }
    private void initDefaultValue(JClass clazz) {
        do {
            for(Field f : clazz.getFields()){
                if(!f.isStatic()){
                    int slotID = f.getSlotID();
                    String descriptor = f.getDescriptor();
                    switch (descriptor.charAt(0)) {
                        case 'Z':
                        case 'B':
                        case 'C':
                        case 'S':
                        case 'I':
                            this.instanceFields.setInt(slotID, 0);
                            break;
                        case 'F':
                            this.instanceFields.setFloat(slotID, 0);
                            break;
                        case 'J':
                            this.instanceFields.setLong(slotID, 0);
                            break;
                        case 'D':
                            this.instanceFields.setDouble(slotID, 0);
                            break;
                        default:
                            this.instanceFields.setObjectRef(slotID, new NullObject());
                            break;
                    }
                }
            }
            clazz = clazz.getSuperClass();
        } while (clazz != null);
    }


}
