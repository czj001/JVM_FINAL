package com.njuse.jvmfinal.classloader.classfilereader;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;
import com.njuse.jvmfinal.util.IOUtil;


public class ClassFileReader {
    private static ClassFileReader reader = new ClassFileReader();
    private static final String FILE_SEPARATOR = File.separator;
    private static final String PATH_SEPARATOR = File.pathSeparator;

    private static Entry bootClasspath = null;//bootstrap class entry
    private static Entry extClasspath = null;//extension class entry
    private static Entry userClasspath = null;//user class entry

    private ClassFileReader(){
        //单例设计模式，控制对象的生成
    }


    public static void initBootAndExt(){
        bootClasspath = chooseEntryType(String.join(File.separator,System.getenv("JAVA_HOME"), "jre", "lib","*"));
        extClasspath = chooseEntryType(String.join(File.separator,System.getenv("JAVA_HOME"), "jre", "lib","ext","*"));
        //两个的路径是确定的
    }

    public static void setUserClasspath(String classpath) {
        userClasspath = chooseEntryType(classpath);
    }

    public static Entry chooseEntryType(String classpath){
        if(classpath.contains(";")|| classpath.contains(":")){
            return new CompositeEntry(classpath.replace(";",PATH_SEPARATOR).replace(":",PATH_SEPARATOR));
        }else if(classpath.endsWith(FILE_SEPARATOR+"*")){
            return new WildEntry(classpath);
        }else if(classpath.contains(".jar")||classpath.contains(".JAR")||
                classpath.contains(".zip")||classpath.contains(".ZIP")){
            return new ArchivedEntry(classpath);
        }else {
            return new DirEntry(classpath);
        }
    }


    public  Pair<byte[], Integer> findClass(String className, EntryType privilege) throws IOException,ClassNotFoundException{
        String realClassName = className + ".class";
        realClassName = IOUtil.transform(realClassName);
        if(privilege == null) privilege = new EntryType(EntryType.USER_ENTRY);
        byte[] c = null;
        if(privilege.getValue() >= EntryType.BOOT_ENTRY){
            c = bootClasspath.readClass(realClassName);
        }
        if(c != null) return Pair.of(c,EntryType.BOOT_ENTRY);


        if(privilege.getValue() >= EntryType.EXT_ENTRY){
            c = extClasspath.readClass(realClassName);
        }
        if(c != null) return Pair.of(c,EntryType.EXT_ENTRY);


        c =userClasspath.readClass(realClassName);
        if(c != null) return Pair.of(c,EntryType.USER_ENTRY);
        else throw new ClassNotFoundException();

    }

    public static ClassFileReader getInstance(){
        //返回ClassFileReader实例
        return reader;
    }
}
