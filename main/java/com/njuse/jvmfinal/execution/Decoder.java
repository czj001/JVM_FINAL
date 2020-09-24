package com.njuse.jvmfinal.execution;

import com.njuse.jvmfinal.instruction.base.Instruction;
import com.njuse.jvmfinal.instruction.base.OpCode;
import com.njuse.jvmfinal.instruction.comparison.*;
import com.njuse.jvmfinal.instruction.constant.*;
import com.njuse.jvmfinal.instruction.control.*;
import com.njuse.jvmfinal.instruction.conversion.*;
import com.njuse.jvmfinal.instruction.extra.INEG;
import com.njuse.jvmfinal.instruction.invoke.INVOKE_INTERFACE;
import com.njuse.jvmfinal.instruction.invoke.INVOKE_SPECIAL;
import com.njuse.jvmfinal.instruction.invoke.INVOKE_STATIC;
import com.njuse.jvmfinal.instruction.invoke.INVOKE_VIRTUAL;
import com.njuse.jvmfinal.instruction.load.*;
import com.njuse.jvmfinal.instruction.math.bool.IXOR;
import com.njuse.jvmfinal.instruction.math.operation.*;
import com.njuse.jvmfinal.instruction.math.shift.ISHL;
import com.njuse.jvmfinal.instruction.references.*;
import com.njuse.jvmfinal.instruction.stack.DUP;
import com.njuse.jvmfinal.instruction.stack.POP;
import com.njuse.jvmfinal.instruction.store.*;


import java.util.HashMap;

public class Decoder {
    private static HashMap<Integer, Instruction> opcodeMap = new HashMap<>();

    static {

        opcodeMap.put(OpCode.ACONST_NULL, new ACONST_NULL());//1

        opcodeMap.put(OpCode.ICONST_M1, new ICONST_N(-1));//2
        opcodeMap.put(OpCode.ICONST_0, new ICONST_N(0));//3
        opcodeMap.put(OpCode.ICONST_1, new ICONST_N(1));//4
        opcodeMap.put(OpCode.ICONST_2, new ICONST_N(2));//5
        opcodeMap.put(OpCode.ICONST_3, new ICONST_N(3));//6
        opcodeMap.put(OpCode.ICONST_4, new ICONST_N(4));//7
        opcodeMap.put(OpCode.ICONST_5, new ICONST_N(5));//8

        opcodeMap.put(OpCode.LCONST_0, new LCONST_L(0));//9
        opcodeMap.put(OpCode.LCONST_1, new LCONST_L(1));//10

        opcodeMap.put(OpCode.FCONST_0, new FCONST_F(0));//11
        opcodeMap.put(OpCode.FCONST_1, new FCONST_F(1));//12
        opcodeMap.put(OpCode.FCONST_2, new FCONST_F(2));//13

        opcodeMap.put(OpCode.DCONST_0, new DCONST_D(0));//14
        opcodeMap.put(OpCode.DCONST_1, new DCONST_D(1));//15

        opcodeMap.put(OpCode.BIPUSH, new BIPUSH());//16
        opcodeMap.put(OpCode.SIPUSH, new SIPUSH());//17
        opcodeMap.put(OpCode.LDC, new LDC());//18
        opcodeMap.put(OpCode.LDC2_W,new LDC2_W());//20
        opcodeMap.put(OpCode.ILOAD, new ILOAD());//21
        opcodeMap.put(OpCode.LLOAD, new LLOAD());//22
        opcodeMap.put(OpCode.FLOAD, new FLOAD());//23
        opcodeMap.put(OpCode.DLOAD, new DLOAD());//24
        opcodeMap.put(OpCode.ALOAD, new ALOAD());//25

        opcodeMap.put(OpCode.ILOAD_0, new ILOAD_N(0));//26
        opcodeMap.put(OpCode.ILOAD_1, new ILOAD_N(1));//27
        opcodeMap.put(OpCode.ILOAD_2,new ILOAD_N(2));//28
        opcodeMap.put(OpCode.ILOAD_3,new ILOAD_N(3));//29

        opcodeMap.put(OpCode.LLOAD_0,new LLOAD_N(0));//30
        opcodeMap.put(OpCode.LLOAD_1,new LLOAD_N(1));//31
        opcodeMap.put(OpCode.LLOAD_2,new LLOAD_N(2));//32
        opcodeMap.put(OpCode.LLOAD_3,new LLOAD_N(3));//33

        opcodeMap.put(OpCode.FLOAD_0, new FLOAD_N(0));//34
        opcodeMap.put(OpCode.FLOAD_1, new FLOAD_N(1));//35
        opcodeMap.put(OpCode.FLOAD_2, new FLOAD_N(2));//36
        opcodeMap.put(OpCode.FLOAD_3, new FLOAD_N(3));//37

        opcodeMap.put(OpCode.DLOAD_0,new DLOAD_N(0)) ;//38
        opcodeMap.put(OpCode.DLOAD_1,new DLOAD_N(1)); //39
        opcodeMap.put(OpCode.DLOAD_2,new DLOAD_N(2));//40
        opcodeMap.put(OpCode.DLOAD_3,new DLOAD_N(3));//41

        opcodeMap.put(OpCode.ALOAD_0, new ALOAD_N(0));//42
        opcodeMap.put(OpCode.ALOAD_1, new ALOAD_N(1));//43
        opcodeMap.put(OpCode.ALOAD_2, new ALOAD_N(2));//44
        opcodeMap.put(OpCode.ALOAD_3, new ALOAD_N(3));//45

        opcodeMap.put(OpCode.IALOAD, new IALOAD());//46

        opcodeMap.put(OpCode.AALOAD, new AALOAD());//50
        opcodeMap.put(OpCode.CALOAD, new CALOAD());//52
        opcodeMap.put(OpCode.ISTORE, new ISTORE());//54
        opcodeMap.put(OpCode.LSTORE, new LSTORE());//55
        opcodeMap.put(OpCode.FSTORE, new FSTORE());//56
        opcodeMap.put(OpCode.DSTORE, new DSTORE());//57
        opcodeMap.put(OpCode.ASTORE, new ASTORE());//58

        opcodeMap.put(OpCode.ISTORE_0, new ISTORE_N(0));//59
        opcodeMap.put(OpCode.ISTORE_1, new ISTORE_N(1));//60
        opcodeMap.put(OpCode.ISTORE_2, new ISTORE_N(2));//61
        opcodeMap.put(OpCode.ISTORE_3, new ISTORE_N(3));//62

        opcodeMap.put(OpCode.LSTORE_0, new LSTORE_N(0));//63
        opcodeMap.put(OpCode.LSTORE_1, new LSTORE_N(1));//64
        opcodeMap.put(OpCode.LSTORE_2, new LSTORE_N(2));//65
        opcodeMap.put(OpCode.LSTORE_3, new LSTORE_N(3));//66

        opcodeMap.put(OpCode.FSTORE_0, new FSTORE_N(0));//67
        opcodeMap.put(OpCode.FSTORE_1, new FSTORE_N(1));//68
        opcodeMap.put(OpCode.FSTORE_2, new FSTORE_N(2));//69
        opcodeMap.put(OpCode.FSTORE_3, new FSTORE_N(3));//70

        opcodeMap.put(OpCode.DSTORE_0, new DSTORE_N(0));//71
        opcodeMap.put(OpCode.DSTORE_1, new DSTORE_N(1));//72
        opcodeMap.put(OpCode.DSTORE_2, new DSTORE_N(2));//73
        opcodeMap.put(OpCode.DSTORE_3, new DSTORE_N(3));//74

        opcodeMap.put(OpCode.ASTORE_0, new ASTORE_N(0));//75
        opcodeMap.put(OpCode.ASTORE_1, new ASTORE_N(1));//76
        opcodeMap.put(OpCode.ASTORE_2, new ASTORE_N(2));//77
        opcodeMap.put(OpCode.ASTORE_3, new ASTORE_N(3));//78

        opcodeMap.put(OpCode.IASTORE, new IASTORE());//79
        opcodeMap.put(OpCode.AASTORE, new AASTORE());//83
        opcodeMap.put(OpCode.CASTORE, new CASTORE());//85

        opcodeMap.put(OpCode.POP,new POP());//87
        opcodeMap.put(OpCode.DUP, new DUP());//89

        opcodeMap.put(OpCode.IADD, new IADD());//96
        opcodeMap.put(OpCode.LADD, new LADD());//97
        opcodeMap.put(OpCode.FADD, new FADD());//98
        opcodeMap.put(OpCode.DADD, new DADD());//99
        opcodeMap.put(OpCode.ISUB, new ISUB());//100
        opcodeMap.put(OpCode.IMUL, new IMUL());//104
        opcodeMap.put(OpCode.IDIV, new IDIV());//108
        opcodeMap.put(OpCode.INEG, new INEG());//116
        opcodeMap.put(OpCode.ISHL, new ISHL());//120
        opcodeMap.put(OpCode.IXOR, new IXOR());//130
        opcodeMap.put(OpCode.IINC, new IINC());//132

        opcodeMap.put(OpCode.I2L,new I2L());//133
        opcodeMap.put(OpCode.I2F,new I2F());//134
        opcodeMap.put(OpCode.I2D, new I2D());//135

        opcodeMap.put(OpCode.L2I, new L2I());//136
        opcodeMap.put(OpCode.L2F, new L2F());//137
        opcodeMap.put(OpCode.L2D, new L2D());//138

        opcodeMap.put(OpCode.F2I, new F2I());//139
        opcodeMap.put(OpCode.F2L, new F2L());//140
        opcodeMap.put(OpCode.F2D, new F2D());//141

        opcodeMap.put(OpCode.D2I, new D2I());//142
        opcodeMap.put(OpCode.D2L, new D2L());//143
        opcodeMap.put(OpCode.D2F, new D2F());//144

        opcodeMap.put(OpCode.I2B, new I2B());//145
        opcodeMap.put(OpCode.I2C,new I2C());//146
        opcodeMap.put(OpCode.I2S, new I2S());//147

        opcodeMap.put(OpCode.LCMP, new LCMP());//148

        opcodeMap.put(OpCode.FCMPL,new FCMPL());//149
        opcodeMap.put(OpCode.FCMPG,new FCMPG());//150

        opcodeMap.put(OpCode.DCMPL, new DCMPL());//151
        opcodeMap.put(OpCode.DCMPG, new DCMPG());//152

        opcodeMap.put(OpCode.IFEQ, new IFEQ());//153
        opcodeMap.put(OpCode.IFNE, new IFNE());//154
        opcodeMap.put(OpCode.IFLT, new IFLT());//155
        opcodeMap.put(OpCode.IFGE,new IFGE());//156
        opcodeMap.put(OpCode.IFGT, new IFGT());//157
        opcodeMap.put(OpCode.IFLE, new IFLE());// 158

        opcodeMap.put(OpCode.IF_ICMPEQ, new IF_ICMPEQ());//159
        opcodeMap.put(OpCode.IF_ICMPNE, new IF_ICMPNE());//160
        opcodeMap.put(OpCode.IF_ICMPLT, new IF_ICMPLT());//161
        opcodeMap.put(OpCode.IF_ICMPGE, new IF_ICMPGE());//162
        opcodeMap.put(OpCode.IF_ICMPGT, new IF_ICMPGT());//163
        opcodeMap.put(OpCode.IF_ICMPLE, new IF_ICMPLE());//164

        opcodeMap.put(OpCode.IF_ACMPEQ, new IF_ACMPEQ());//165
        opcodeMap.put(OpCode.IF_ACMPNE, new IF_ACMPNE());//166

        opcodeMap.put(OpCode.GOTO_,new GOTO());//167

        opcodeMap.put(OpCode.IRETURN,new IRETURN());//172
        opcodeMap.put(OpCode.LRETURN, new LRETURN());//173
        opcodeMap.put(OpCode.FRETURN, new FRETURN());//174
        opcodeMap.put(OpCode.DRETURN, new DRETURN());//175
        opcodeMap.put(OpCode.ARETURN, new ARETURN());//176
        opcodeMap.put(OpCode.RETURN_, new RETURN());//177

        opcodeMap.put(OpCode.GETSTATIC,new GETSTATIC());//178
        opcodeMap.put(OpCode.PUTSTATIC,new PUTSTATIC());//179
        opcodeMap.put(OpCode.GETFIELD, new GETFIELD());//180
        opcodeMap.put(OpCode.PUTFIELD, new PUTFIELD());//181
        opcodeMap.put(OpCode.INVOKEVIRTUAL, new INVOKE_VIRTUAL());//182
        opcodeMap.put(OpCode.INVOKESPECIAL, new INVOKE_SPECIAL());//183
        opcodeMap.put(OpCode.INVOKESTATIC, new INVOKE_STATIC());//184
        opcodeMap.put(OpCode.INVOKEINTERFACE, new INVOKE_INTERFACE());//185
        opcodeMap.put(OpCode.NEW_, new NEW());//187

        opcodeMap.put(OpCode.NEWARRAY, new NEWARRAY());//188
        opcodeMap.put(OpCode.ARRAYLENGTH, new ARRAYLENGTH());//190
        opcodeMap.put(OpCode.INSTANCEOF_, new INSTANCEOF());//193

        opcodeMap.put(OpCode.MULTIANEWARRAY, new MULTIANEWARRAY());//197
        opcodeMap.put(OpCode.IFNULL, new IFNULL());//198
        opcodeMap.put(OpCode.IFNONNULL, new IFNONNULL());//199

    }

    public static Instruction decode(int opcode){
        Instruction instruction = opcodeMap.get(opcode);
        if (instruction == null) {
            throw new UnsupportedOperationException("Unsupported instruction " + String.format("0x%08X", opcode));
        }
        return instruction;
    }

}
