package com.njuse.jvmfinal.classloader.classfilereader;
import java.io.IOException;
public class CompositeEntry extends Entry{
    public CompositeEntry(String classpath) {
        super(classpath);
    }
    public byte[] readClass(String className) throws IOException {
        String[] paths = classpath.split(PATH_SEPARATOR);
        Entry entry ;
        byte[] b;
        for(String i : paths){
            entry = ClassFileReader.chooseEntryType(i);
            b = entry.readClass(className);
            if(b != null) return b;
        }
        return null;
    }
}
