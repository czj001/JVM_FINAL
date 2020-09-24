package com.njuse.jvmfinal.instruction.references;

import com.njuse.jvmfinal.instruction.base.Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.ClassRef;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import com.njuse.jvmfinal.runtime.data.JObject.array.RefArrayObject;

import java.nio.ByteBuffer;

public class MULTIANEWARRAY extends Instruction {
    private int index;
    private int dimensions;

    @Override
    public void fetchOperands(ByteBuffer reader) {
        this.index = (int)reader.getShort() & 0xffff;
        this.dimensions = (int)reader.get() & 0xff;
    }

    @Override
    public void execute(StackFrame frame) {
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        ClassRef classRef = (ClassRef)runtimeConstantPool.getConstant(this.index);
        try{
            JClass arrayClass = classRef.resolveClassRef();
            OperandStack stack = frame.getOperandStack();
            int[] lenArray = getAndCheckCounts(stack, dimensions);
            ArrayObject arrayRef = createMultiDimensionArray(0,lenArray, arrayClass);
            JHeap.getInstance().addObject(arrayRef);
            stack.pushObjectRef(arrayRef);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    private int[] getAndCheckCounts(OperandStack stack, int dimensions){
        int[] lenArray = new int[dimensions];
        for(int i = dimensions - 1; i >= 0; --i) {
            lenArray[i] = stack.popInt();
            if (lenArray[i] < 0) {
                throw new NegativeArraySizeException();
            }
        }
        return lenArray;
    }
    private ArrayObject createMultiDimensionArray(int index, int[] lenArray, JClass arrClass) {
        int len = lenArray[index];
        index++;
        ArrayObject arrRef = arrClass.newArrayObject(len);
        if(index < lenArray.length){
            assert arrRef instanceof RefArrayObject;
            for(int i = 0; i < arrRef.getLen(); ++i) {
                ((RefArrayObject)arrRef).getArray()[i] = this.createMultiDimensionArray(index, lenArray, arrClass.getComponentRefClass());
            }

        }
        return arrRef;
    }
}
