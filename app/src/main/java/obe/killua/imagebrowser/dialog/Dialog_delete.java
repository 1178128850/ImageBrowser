package obe.killua.imagebrowser.dialog;


import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import obe.killua.imagebrowser.R;
import obe.killua.imagebrowser.utils.PreferencesService;
import obe.killua.imagebrowser.utils.ViewUtils;

/**
 * Created by Administrator on 2018/3/30.
 */

public class Dialog_delete extends Dialog{

    private ViewDataBinding inflate;

    public Dialog_delete(@NonNull Context context) {
        super(context);
    }

    public Dialog_delete(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().getDecorView().setPadding(0,0,0,0);
    }

    protected Dialog_delete(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void initView(Context context){
        inflate = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.delete_layout, null, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(inflate.getRoot(),layoutParams);
    }

}
