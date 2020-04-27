package obe.killua.imagebrowser.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import obe.killua.imagebrowser.BaseActivity;
import obe.killua.imagebrowser.R;
import obe.killua.imagebrowser.bean.BaseRecyclerBean;
import obe.killua.imagebrowser.bean.ImageBean;
import obe.killua.imagebrowser.databinding.ActivitySlideBinding;
import obe.killua.imagebrowser.dialog.Dialog_menu;
import obe.killua.imagebrowser.utils.PreferencesService;

/**
 * Created by Administrator on 2018/3/29.
 */

public class SlideActivity extends BaseActivity  implements Dialog_menu.MyDismissListener{

    private ActivitySlideBinding viewDataBinding;
    private Dialog_menu dialog_menu;
    private List<BaseRecyclerBean> baseRecyclerBeans = new ArrayList<>();
    private boolean isWhile = true;
    private Thread thread = null;
    private int selectedPosition = 0;

    private static final int DOESDISPATCH = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DOESDISPATCH:
                    DisMenu();
                    break;
            }
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_slide);
        initImages();
        notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isWhile = true;
        startHDP();
    }

    private void initImages() {
        //noinspection unchecked
        baseRecyclerBeans = (List<BaseRecyclerBean>) getIntent().getSerializableExtra("datas");
        selectedPosition = getIntent().getIntExtra("selectedPosition",0);
        String imgPath = getIntent().getStringExtra("imgPath");
        setImgPath(imgPath);
    }

    private void startHDP() {
        thread = new Thread(() -> {
            while (isWhile) {
                runOnUiThread(() -> scrollToPosition(selectedPosition));
                try {
                    Thread.sleep(PreferencesService.GetInt(getApplicationContext(),PreferencesService.SCROLL_TIME,3*1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                switch (PreferencesService.GetInt(getApplicationContext(),PreferencesService.SCROLL_TYPE,PreferencesService.CYCLEPLAY)){
                    case PreferencesService.LISTPLAY:
                        if (selectedPosition + 1 >= baseRecyclerBeans.size()) {//
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "播放完毕", Toast.LENGTH_SHORT).show());
//                            finish();
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
    protected void onStop() {
        super.onStop();
        isWhile = false;
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void doClick(View view) {
        switch (view.getId()){
            case R.id.bt_time:
                handler.removeMessages(DOESDISPATCH);
                dialog_menu = new Dialog_menu(this, R.style.Dialog);
                dialog_menu.setMyDismissListener(this);
                dialog_menu.initPP_time(this);
                dialog_menu.show((int)(viewDataBinding.layoutMenu.getX() + view.getX()+view.getWidth()/2),
                        (int)viewDataBinding.layoutMenu.getY());
                break;
            case R.id.bt_anim:
                handler.removeMessages(DOESDISPATCH);
                dialog_menu = new Dialog_menu(this, R.style.Dialog);
                dialog_menu.setMyDismissListener(this);
                dialog_menu.initPP_anim(this);
                dialog_menu.show((int)(viewDataBinding.layoutMenu.getX() + view.getX()+view.getWidth()/2),
                        (int)viewDataBinding.layoutMenu.getY());
                break;
            case R.id.bt_type:
                handler.removeMessages(DOESDISPATCH);
                dialog_menu = new Dialog_menu(this, R.style.Dialog);
                dialog_menu.setMyDismissListener(this);
                dialog_menu.initPP_type(this);
                dialog_menu.show((int)(viewDataBinding.layoutMenu.getX() + view.getX()+view.getWidth()/2),
                        (int)viewDataBinding.layoutMenu.getY());
                break;
            case R.id.tv_time1:
                PreferencesService.SaveInt(this,PreferencesService.SCROLL_TIME,3*1000);
                dialog_menu.dismiss();
                break;
            case R.id.tv_time2:
                PreferencesService.SaveInt(this,PreferencesService.SCROLL_TIME,5*1000);
                dialog_menu.dismiss();
                break;
            case R.id.tv_time3:
                PreferencesService.SaveInt(this,PreferencesService.SCROLL_TIME,10*1000);
                dialog_menu.dismiss();
                break;
            case R.id.tv_type1:
                PreferencesService.SaveInt(this,PreferencesService.SCROLL_TYPE,PreferencesService.LISTPLAY);
                dialog_menu.dismiss();
                break;
            case R.id.tv_type2:
                PreferencesService.SaveInt(this,PreferencesService.SCROLL_TYPE,PreferencesService.CYCLEPLAY);
                dialog_menu.dismiss();
                break;
            case R.id.tv_type3:
                PreferencesService.SaveInt(this,PreferencesService.SCROLL_TYPE,PreferencesService.RANDOMPLAY);
                dialog_menu.dismiss();
            case R.id.tv_anim1:
                PreferencesService.SaveInt(this,PreferencesService.SCROLL_ANIM,PreferencesService.ANIME_ONE);
                dialog_menu.dismiss();
                break;
            case R.id.tv_anim2:
                PreferencesService.SaveInt(this,PreferencesService.SCROLL_ANIM,PreferencesService.ANIME_TWO);
                dialog_menu.dismiss();
                break;
            case R.id.tv_anim3:
                PreferencesService.SaveInt(this,PreferencesService.SCROLL_ANIM,PreferencesService.ANIME_THREE);
                dialog_menu.dismiss();
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(isShowMenu()){
                handler.removeMessages(DOESDISPATCH);
                handler.sendEmptyMessageDelayed(DOESDISPATCH,5*1000);
            }
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
            handler.removeMessages(DOESDISPATCH);
            handler.sendEmptyMessageDelayed(DOESDISPATCH,5*1000);
            viewDataBinding.layoutMenu.setVisibility(View.VISIBLE);
        }
    }

    public void DisMenu(){
        if(isShowMenu()){
            viewDataBinding.layoutMenu.setVisibility(View.INVISIBLE);
        }
    }

    public boolean isShowMenu(){
        if(viewDataBinding.layoutMenu.getVisibility() == View.VISIBLE){
            return true;
        }
        return false;
    }

    @Override
    public void dismiss() {
        DisMenu();
    }
}

