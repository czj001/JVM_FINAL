package com.njuse.jvmfinal.classloader.classfilereader;


import java.io.File;
import java.io.IOException;
public abstract class Entry {
    public final String FILE_SEPARATOR = File.separator;
    public final String PATH_SEPARATOR = File.pathSeparator;
    public String classpath;

    public Entry(String classpath){
        this.classpath = classpath;
    }
    public abstract byte[] readClass(String className) throws IOException;
}
