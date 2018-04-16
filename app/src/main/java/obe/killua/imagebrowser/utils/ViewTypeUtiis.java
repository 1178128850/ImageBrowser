package obe.killua.imagebrowser.utils;

import obe.killua.imagebrowser.R;

/**
 * Created by wangtao on 2017/12/11.
 */

public class ViewTypeUtiis {


    public static final int VIEWTYPE_IMG = 100;


    public static int getLayoutId(int ViewType){
        switch (ViewType){
            case VIEWTYPE_IMG:
                return  R.layout.item_rcl_img;
        }
        return -1;
    }
}
