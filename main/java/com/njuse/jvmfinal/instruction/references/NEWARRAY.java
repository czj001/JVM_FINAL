package com.njuse.jvmfinal.instruction.references;

import com.njuse.jvmfinal.classloader.ClassLoader;
import com.njuse.jvmfinal.classloader.classfilereader.EntryType;
import com.njuse.jvmfinal.instruction.base.Instruction;
import com.njuse.jvmfinal.memory.JHeap;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.data.JObject.ArrayObject;
import com.njuse.jvmfinal.runtime.data.JObject.array.ArrayType;

import java.nio.ByteBuffer;

public class NEWARRAY extends Instruction {
    private int atype;

    @Override
    public void fetchOperands(ByteBuffer reader) {
        this.atype = (int)reader.get() & 0xff;
    }


    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        int len = stack.popInt();
        if (len < 0) {
            throw new NegativeArraySizeException();
        } else{
            JClass arrayClass = getPrimitiveArrayClass(atype, frame.getMethod().getClazz().getLoadEntryType());
            ArrayObject ref = arrayClass.newArrayObject(len);
            JHeap.getInstance().addObject(ref);
            stack.pushObjectRef(ref);
        }
    }
    private JClass getPrimitiveArrayClass(int atype, EntryType initialingEntry){
        ClassLoader loader = ClassLoader.getInstance();
        try{
            switch (atype){
                case ArrayType.AT_BOOLEAN:
                    return loader.loadClass("[Z", initialingEntry);
                case ArrayType.AT_BYTE:
                    return loader.loadClass("[B", initialingEntry);
                case ArrayType.AT_CHAR:
                    return loader.loadClass("[C",initialingEntry);
                case ArrayType.AT_FLOAT:
                    return loader.loadClass("[F", initialingEntry);
                case ArrayType.AT_DOUBLE:
                    return loader.loadClass("[D",initialingEntry);
                case ArrayType.AT_SHORT:
                    return loader.loadClass("[S", initialingEntry);
                    case ArrayType.AT_INT:
                     return loader.loadClass("[I", initialingEntry);
                case ArrayType.AT_LONG:
                     return loader.loadClass("[J", initialingEntry);
                default:
                    throw new RuntimeException("Invalid atype");
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}
