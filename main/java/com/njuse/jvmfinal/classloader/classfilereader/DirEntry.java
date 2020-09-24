package com.njuse.jvmfinal.classloader.classfilereader;
import java.io.*;
import com.njuse.jvmfinal.util.IOUtil;
public class DirEntry extends Entry{
    public DirEntry(String classpath) {
        super(classpath);
    }

    public byte[] readClass(String className) throws IOException {
        String Path = classpath+FILE_SEPARATOR+className;
        File f = new File(Path);
        if(!f.exists()){
            return null;
        }
        InputStream is = new FileInputStream(f);
        return IOUtil.readFileByBytes(is);
    }
}
