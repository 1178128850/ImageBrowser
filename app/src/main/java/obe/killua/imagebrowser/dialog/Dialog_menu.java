package obe.killua.imagebrowser.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import obe.killua.imagebrowser.R;
import obe.killua.imagebrowser.utils.PreferencesService;
import obe.killua.imagebrowser.utils.ViewUtils;

/**
 * Created by Administrator on 2018/3/30.
 */

public class Dialog_menu extends Dialog implements View.OnFocusChangeListener{

    private ViewDataBinding inflate;
    private boolean isExitAnimationing = false;

    public Dialog_menu(@NonNull Context context) {
        super(context);
    }

    public Dialog_menu(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().getDecorView().setPadding(0,0,0,15);
    }

    protected Dialog_menu(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void initView(Context context){
        inflate = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.menu_layout, null, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(inflate.getRoot(),layoutParams);
    }

    public void initPP_time(Context context){
        inflate = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pp_layout_time, null, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(inflate.getRoot(),layoutParams);
        inflate.getRoot().findViewById(R.id.tv_time1).setOnFocusChangeListener(this);
        inflate.getRoot().findViewById(R.id.tv_time2).setOnFocusChangeListener(this);
        inflate.getRoot().findViewById(R.id.tv_time3).setOnFocusChangeListener(this);
        switch (PreferencesService.GetInt(context,"SCROLLTIME",3*1000)){
            case 3*1000:
                inflate.getRoot().findViewById(R.id.tv_time1).requestFocus();
                break;
            case 5*1000:
                inflate.getRoot().findViewById(R.id.tv_time2).requestFocus();
                break;
            case 10*1000:
                inflate.getRoot().findViewById(R.id.tv_time3).requestFocus();
                break;
        }
    }

    public void initPP_type(Context context){
        inflate = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pp_layout_type, null, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(inflate.getRoot(),layoutParams);
        inflate.getRoot().findViewById(R.id.tv_type1).setOnFocusChangeListener(this);
        inflate.getRoot().findViewById(R.id.tv_type2).setOnFocusChangeListener(this);
        inflate.getRoot().findViewById(R.id.tv_type3).setOnFocusChangeListener(this);
        switch (PreferencesService.GetInt(context,"SCROLLTYPE",3*1000)){
            case PreferencesService.LISTPLAY:
                inflate.getRoot().findViewById(R.id.tv_type1).requestFocus();
                break;
            case PreferencesService.CYCLEPLAY:
                inflate.getRoot().findViewById(R.id.tv_type2).requestFocus();
                break;
            case PreferencesService.RANDOMPLAY:
                inflate.getRoot().findViewById(R.id.tv_type3).requestFocus();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            ((TextView)v).setTextColor(Color.WHITE);
        }else{
            ((TextView)v).setTextColor(0xFF999999);
        }
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_MENU:
//                    menu_popupWindow.showPopupWindow(viewDataBinding.getRoot());
                    dismiss();
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void show() {
        if(!isShowing()){
            super.show();
           /* Animation animation = AnimationUtils.loadAnimation(inflate.getRoot().getContext(), R.anim.translate_in);
            inflate.getRoot().startAnimation(animation);*/
        }
    }

    public void show(int x,int y){
        getWindow().setGravity(Gravity.NO_GRAVITY);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        int[] ints = ViewUtils.unDisplayViewSize(inflate.getRoot());
        inflate.getRoot().setX(x - inflate.getRoot().getMeasuredWidth()/2);
        inflate.getRoot().setY(y - inflate.getRoot().getMeasuredHeight());
        show();
    }

    @Override
    public void dismiss() {
        if(isShowing() && !isExitAnimationing){
           /* Animation animation = AnimationUtils.loadAnimation(inflate.getRoot().getContext(), R.anim.translate_out);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isExitAnimationing = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Mydismiss();
                    isExitAnimationing = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            inflate.getRoot().startAnimation(animation);*/
            Mydismiss();
        }
    }

    public void Mydismiss(){
        super.dismiss();
    }



}
