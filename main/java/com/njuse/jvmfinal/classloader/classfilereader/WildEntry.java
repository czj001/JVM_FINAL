package com.njuse.jvmfinal.classloader.classfilereader;

import java.io.*;

public class WildEntry extends Entry{
    public WildEntry(String classpath) {
        super(classpath);
    }

    public byte[] readClass(String className) throws IOException {
        char[] c = classpath.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < c.length-2; i++){
            sb.append(c[i]);
        }
        byte[]b;
        String fp = sb.toString();
        File f = new File(fp);
        if(!f.exists()) return null;
        String[] files = f.list();
        for(String i :files){
            Entry entry = null;
            if(i.toLowerCase().endsWith(".jar")||i.toLowerCase().endsWith(".zip")){
                String cp = fp +  FILE_SEPARATOR+ i;
                entry = new ArchivedEntry(cp);
                if((b = entry.readClass(className))!=null){
                    return b;
                }
            }
        }
        return null;
    }
}
