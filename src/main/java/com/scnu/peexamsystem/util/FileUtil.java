package com.scnu.peexamsystem.util;

import java.io.*;
import java.util.Objects;

public class FileUtil {
    /**
     * 文件上传工具类服务方法
     * @param file
     * @param filePath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static boolean uploadFile(byte[] file, String filePath, String fileName) throws Exception {

        File targetFile = new File(filePath);
        deleteFile(targetFile);
        if (targetFile.mkdirs()) {
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            out.write(file);
            out.flush();
            out.close();
            return true;
        }
        return false;
    }

    /**
     * 先根遍历序递归删除文件夹
     * @param dirFile 要被删除的文件或者目录
     * @return 删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : Objects.requireNonNull(dirFile.listFiles())) {
                deleteFile(file);
            }
        }
        return dirFile.delete();
    }
}
