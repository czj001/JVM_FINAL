package com.njuse.jvmfinal.runtime.data;

import com.njuse.jvmfinal.runtime.data.JObject.JObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slot {
    private JObject object;
    private Integer value;

    public Slot clone(){
        Slot newSlot = new Slot();
        newSlot.setValue( this.value);
        newSlot.setObject(this.object);
        return newSlot;
    }
}
