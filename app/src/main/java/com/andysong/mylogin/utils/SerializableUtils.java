package com.andysong.mylogin.utils;

import com.orhanobut.logger.Logger;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化工具类
 * Created by Administrator on 2017/7/5.
 */

public class SerializableUtils {

    public static <T extends Serializable> Object readObject(File file) {
        ObjectInputStream in = null;
        T t = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            t = (T) in.readObject();
        } catch (EOFException e) {
            // ... this is fine
        } catch (IOException e) {
            Logger.e("e " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    public static <T extends Serializable> boolean writeObject(T t, String fileName) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(t);
            Logger.d("序列化成功 " + t.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Logger.d("序列化失败 " + e.getMessage());
            return false;
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getSerializableFile(String rootPath, String fileName) throws IOException {
        File file = new File(rootPath);
        if (!file.exists()) file.mkdirs();
        File serializable = new File(file, fileName);
        if (!serializable.exists()) serializable.createNewFile();
        return serializable;
    }
}
