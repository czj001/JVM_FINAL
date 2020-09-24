package com.njuse.jvmfinal.runtime;

import com.njuse.jvmfinal.runtime.data.JObject.JObject;
import com.njuse.jvmfinal.runtime.data.Slot;

public class Vars {
    //对于变量的封装类,避免直接操纵数组导致错误
    Slot[] slots;
    int slotCounts;


    public Vars(int counts){
        assert counts >= 0;
        this.slotCounts = counts;
        this.slots = new Slot[this.slotCounts];
        for(int i = 0; i < slotCounts; i++){
            slots[i] = new Slot();
        }
    }


    public void setInt(int index, int value) {
        if (index < 0 || index >= slotCounts) throw new IndexOutOfBoundsException();
        slots[index].setValue(value);
    }

    public int getInt(int index) {
        if (index < 0 || index >= slotCounts) throw new IndexOutOfBoundsException();
        return slots[index].getValue();
    }

    public void setFloat(int index, float value) {
        if (index < 0 || index >= slotCounts) throw new IndexOutOfBoundsException();
        slots[index].setValue(Float.floatToIntBits(value));
    }

    public float getFloat(int index) {
        if (index < 0 || index >= slotCounts) throw new IndexOutOfBoundsException();
        return Float.intBitsToFloat(slots[index].getValue());
    }

    public void setLong(int index, long value) {
        if (index < 0 || index + 1 >= slotCounts) throw new IndexOutOfBoundsException();
        slots[index].setValue((int) value);
        slots[index + 1].setValue((int) (value >> 32));
    }

    public long getLong(int index) {
        if (index < 0 || index + 1 >= slotCounts) throw new IndexOutOfBoundsException();
        int low = slots[index].getValue();
        int high = slots[index + 1].getValue();
        return (((long) high) << 32) | ((long) low & 0x0ffffffffL);
    }

    public void setDouble(int index, double value) {
        if (index < 0 || index + 1 >= slotCounts) throw new IndexOutOfBoundsException();
        setLong(index, Double.doubleToLongBits(value));
    }

    public double getDouble(int index) {
        if (index < 0 || index + 1 >= slotCounts) throw new IndexOutOfBoundsException();
        return Double.longBitsToDouble(getLong(index));
    }

    public void setObjectRef(int index, JObject ref) {
        if (index < 0 || index >= slotCounts) throw new IndexOutOfBoundsException();
        slots[index].setObject(ref);
    }

    public JObject getObjectRef(int index) {
        if (index < 0 || index >= slotCounts) throw new IndexOutOfBoundsException();
        return slots[index].getObject();
    }

    public void setSlot(int index, Slot slot) {
        if (index < 0 || index >= slotCounts) throw new IndexOutOfBoundsException();
        slots[index] = slot;
    }
}
