package com.mllweb.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileUtils {
    /**
     * createFile
     *
     * @param fileName fileName
     */
    public static File createFile(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                if (!file.isFile()) {
                    file.createNewFile();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * createFolder
     *
     * @param path filePath
     */
    public static File createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            if (!file.isDirectory()) {
                file.mkdirs();
            }
        }
        return file;
    }

    /**
     * delete file or delete folder
     *
     */
    public static boolean delete(File file) {
        if (file != null) {
            if (file.isFile()) {
                file.delete();
                return true;
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null || files.length == 0) {
                    file.delete();
                } else {
                    for (File f : files) {
                        delete(f);
                    }
                    file.delete();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * delete file or delete folder
     *
     */
    public static boolean delete(String path) {
        File file = new File(path);
        if (file.exists()) {
            return delete(file);
        }
        return false;
    }

    /**
     * file write
     *
     * @param fileName fileName
     * @param bytes    bytes
     */
    public static void write(String fileName, byte[] bytes) {
        try {
            File file = createFile(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bytes, 0, bytes.length);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * file exists
     *
     * @param fileName
     * @return
     */
    public static boolean exists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * get file size or get folder size
     *
     * @param path
     * @return how much size
     */
    public static long size(String path) {
        long size = 0;
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                return file.length();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    size += size(f.getAbsolutePath());
                }
                return size;
            }
        }
        return size;
    }

    /**
     * file read
     *
     * @param fileName
     * @return byte array
     */
    public static byte[] read(String fileName) {
        try {
            if (exists(fileName)) {
                File file = new File(fileName);
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                int i = -1;
                while ((i = bufferedInputStream.read()) != -1) {
                    bytes.write(i);
                }
                byte[] byteArray = bytes.toByteArray();
                bytes.close();
                return byteArray;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
