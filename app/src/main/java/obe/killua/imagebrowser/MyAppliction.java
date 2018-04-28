package obe.killua.imagebrowser;

import android.app.Application;

import obe.killua.imagebrowser.utils.DensityHelper;

/**
 * Created by Administrator on 2018/4/23.
 */

public class MyAppliction extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        new DensityHelper(this,1280).activate();
    }
}
