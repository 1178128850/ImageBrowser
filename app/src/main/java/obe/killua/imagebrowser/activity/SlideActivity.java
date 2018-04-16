package obe.killua.imagebrowser.activity;

import android.app.ActionBar;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import obe.killua.imagebrowser.BaseActivity;
import obe.killua.imagebrowser.R;
import obe.killua.imagebrowser.adapter.MyRecyclerAdapter;
import obe.killua.imagebrowser.bean.BaseRecyclerBean;
import obe.killua.imagebrowser.bean.ImageBean;
import obe.killua.imagebrowser.databinding.ActivitySlideBinding;
import obe.killua.imagebrowser.dialog.Dialog_menu;
import obe.killua.imagebrowser.utils.PreferencesService;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SlideActivity extends BaseActivity{

    private ActivitySlideBinding viewDataBinding;
    private Dialog_menu dialog_menu;
    private List<BaseRecyclerBean> baseRecyclerBeans = new ArrayList<>();
    private Thread thread = null;
    private int selectedPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_slide);
        initImages();
        notifyDataSetChanged();
        startHDP();
    }

    private void initImages() {
        //noinspection unchecked
        baseRecyclerBeans = (List<BaseRecyclerBean>) getIntent().getSerializableExtra("datas");
    }

    private void startHDP() {
        thread = new Thread(() -> {
            while (true) {
                runOnUiThread(() -> scrollToPosition(selectedPosition));
                try {
                    Thread.sleep(PreferencesService.GetInt(getApplicationContext(),"SCROLLTIME",3*1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                switch (PreferencesService.GetInt(getApplicationContext(),"SCROLLTYPE",PreferencesService.LISTPLAY)){
                    case PreferencesService.LISTPLAY:
                        if (selectedPosition + 1 >= baseRecyclerBeans.size()) {//
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "播放完毕", Toast.LENGTH_SHORT).show());
                            finish();
                            return;
                        } else {
                            selectedPosition += 1;
                        }
                        break;
                    case PreferencesService.CYCLEPLAY:
                        if (selectedPosition + 1 >= baseRecyclerBeans.size()) {//
                                selectedPosition = 0;
                        } else {
                            selectedPosition += 1;
                        }
                        break;
                    case PreferencesService.RANDOMPLAY:
                        selectedPosition = new Random().nextInt(baseRecyclerBeans.size());
                        break;
                }
            }
        });
        thread.start();
    }

    @Override
    public void finish() {
        if(thread != null){
            thread.stop();
            thread.destroy();
        }
        super.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void doClick(View view) {
        switch (view.getId()){
            case R.id.bt_time:
                dialog_menu = new Dialog_menu(this, R.style.Dialog);
                dialog_menu.initPP_time(this);
                dialog_menu.show((int)(viewDataBinding.layoutMenu.getX() + view.getX()+view.getWidth()/2),
                        (int)viewDataBinding.layoutMenu.getY());
                break;
            case R.id.bt_type:
                dialog_menu = new Dialog_menu(this, R.style.Dialog);
                dialog_menu.initPP_type(this);
                dialog_menu.show((int)(viewDataBinding.layoutMenu.getX() + view.getX()+view.getWidth()/2),
                        (int)viewDataBinding.layoutMenu.getY());
                break;
            case R.id.tv_time1:
                PreferencesService.SaveInt(this,"SCROLLTIME",3*1000);
                dialog_menu.dismiss();
                break;
            case R.id.tv_time2:
                PreferencesService.SaveInt(this,"SCROLLTIME",5*1000);
                dialog_menu.dismiss();
                break;
            case R.id.tv_time3:
                PreferencesService.SaveInt(this,"SCROLLTIME",10*1000);
                dialog_menu.dismiss();
                break;
            case R.id.tv_type1:
                PreferencesService.SaveInt(this,"SCROLLTYPE",PreferencesService.LISTPLAY);
                dialog_menu.dismiss();
                break;
            case R.id.tv_type2:
                PreferencesService.SaveInt(this,"SCROLLTYPE",PreferencesService.CYCLEPLAY);
                dialog_menu.dismiss();
                break;
            case R.id.tv_type3:
                PreferencesService.SaveInt(this,"SCROLLTYPE",PreferencesService.RANDOMPLAY);
                dialog_menu.dismiss();
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_MENU:
                    showOrDisMenu();
                    break;
                case KeyEvent.KEYCODE_BACK:
                    if(isShowMenu()){
                        showOrDisMenu();
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if(selectedPosition-1 >= 0 && !isShowMenu()){
                        selectedPosition -=1;
                        scrollToPosition(selectedPosition);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if(selectedPosition+1 < baseRecyclerBeans.size() && !isShowMenu()){
                        selectedPosition +=1;
                        scrollToPosition(selectedPosition);
                    }
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void notifyDataSetChanged(){
        viewDataBinding.setSize(baseRecyclerBeans.size());
        viewDataBinding.setPostion(selectedPosition+1);
        viewDataBinding.setImageBean((ImageBean) baseRecyclerBeans.get(selectedPosition));
    }

    void scrollToPosition(int selectedPosition){
        viewDataBinding.setPostion(selectedPosition+1);
        viewDataBinding.setImageBean((ImageBean) baseRecyclerBeans.get(selectedPosition));
    }

    public void showOrDisMenu(){
        if(isShowMenu()){
            viewDataBinding.layoutMenu.setVisibility(View.INVISIBLE);
        }else{
            viewDataBinding.layoutMenu.setVisibility(View.VISIBLE);
        }
    }

    public boolean isShowMenu(){
        if(viewDataBinding.layoutMenu.getVisibility() == View.VISIBLE){
            return true;
        }
        return false;
    }

}

