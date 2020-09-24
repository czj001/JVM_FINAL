package com.njuse.jvmfinal.classloader.classfilereader;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.njuse.jvmfinal.util.IOUtil;
public class ArchivedEntry extends Entry {
    public ArchivedEntry(String classpath){
        super(classpath);
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        ZipFile zipFile = new ZipFile(classpath);
        ZipEntry zipEntry = zipFile.getEntry(className.replace(FILE_SEPARATOR,"/"));
        if(zipEntry == null) return null;
        InputStream is = zipFile.getInputStream(zipEntry);
        return IOUtil.readFileByBytes(is);
    }
}
