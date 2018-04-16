package obe.killua.imagebrowser.utils;

import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Administrator on 2018/3/29.
 */

public class ImageUtils {
    public static final int FILE_DELETE_TRUE = 1;
    public static final int FILE_DELETE_FALSE = 2;
    public static final int FILE_DELETE_NOFILE = 3;
    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static int deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return FILE_DELETE_TRUE;
            } else {
                return FILE_DELETE_FALSE;
            }
        } else {
            return FILE_DELETE_NOFILE;
        }
    }

    /**
     * 获取指定文件大小(单位：字节)
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        if (file == null) {
            return 0;
        }
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        return size;
    }
}
