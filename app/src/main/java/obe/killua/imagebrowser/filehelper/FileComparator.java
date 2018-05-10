package obe.killua.imagebrowser.filehelper;

import java.util.Comparator;

import obe.killua.imagebrowser.bean.ImageBean;

/**
 * Created by Administrator on 2018/5/10.
 */

public class FileComparator implements Comparator<ImageBean>{

    @Override
    public int compare(ImageBean object1, ImageBean object2) {
        return object1.getName().compareToIgnoreCase(object2.getName());
    }
    
}
