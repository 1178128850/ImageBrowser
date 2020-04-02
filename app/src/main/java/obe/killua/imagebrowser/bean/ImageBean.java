package obe.killua.imagebrowser.bean;



import androidx.databinding.Bindable;

import java.io.File;

import obe.killua.imagebrowser.BR;
import obe.killua.imagebrowser.utils.ViewTypeUtiis;

/**
 * Created by Administrator on 2018/3/28.
 */

public class ImageBean extends BaseRecyclerBean{

    String name;
    File file;
    @Bindable
    float rotateRotationAngle = 0f;
    long size;

    public ImageBean(){
        setItem_bindingkey(BR.imagebean);
        setViewType(ViewTypeUtiis.VIEWTYPE_IMG);
    }


    public float getRotateRotationAngle() {
        return rotateRotationAngle;
    }

    public void setRotateRotationAngle(float rotateRotationAngle) {
        this.rotateRotationAngle =this.rotateRotationAngle + rotateRotationAngle;
        notifyPropertyChanged(BR.rotateRotationAngle);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}

