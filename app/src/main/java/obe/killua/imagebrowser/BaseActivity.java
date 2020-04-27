package obe.killua.imagebrowser;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/3/29.
 */

public class BaseActivity extends Activity{
    protected static String TAG = "===tag";

    private String imgPath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_MEDIA_EJECT);
        intentFilter.addDataScheme("file");
        registerReceiver(mReceiver,intentFilter);
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver(){
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Uri uri = intent.getData();
            assert uri != null;
            String path = uri.getPath();
            Log.i("===mReceiver", "mMountBroadReceiver get action = "+action+", path="+path + ", imgPath" + imgPath);
            if (Intent.ACTION_MEDIA_EJECT.equals(action)) {
                if(imgPath != null && imgPath.contains(path)){
                    finish();
                }
            }
        }
    };

    @Override
    public void finish() {
        super.finish();
        unregisterReceiver(mReceiver);
    }
}
