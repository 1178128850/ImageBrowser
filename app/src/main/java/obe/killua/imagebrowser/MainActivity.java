package obe.killua.imagebrowser;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import obe.killua.imagebrowser.activity.SlideActivity;
import obe.killua.imagebrowser.adapter.MyRecyclerAdapter;
import obe.killua.imagebrowser.bean.BaseRecyclerBean;
import obe.killua.imagebrowser.bean.ImageBean;
import obe.killua.imagebrowser.databinding.ActivityMainBinding;
import obe.killua.imagebrowser.dialog.Dialog_delete;
import obe.killua.imagebrowser.dialog.Dialog_menu;
import obe.killua.imagebrowser.filehelper.FileComparator;
import obe.killua.imagebrowser.filehelper.FilenameExtFilter;
import obe.killua.imagebrowser.utils.ImageUtils;

public class MainActivity extends BaseActivity{

    private String[] types = {"png","gif", "jpg" ,"jpeg" ,"bmp" ,"wbmp" ,"webp"};
    private ActivityMainBinding viewDataBinding;
    private MyRecyclerAdapter myRecyclerAdapter;
    private List<BaseRecyclerBean> baseRecyclerBeans = new ArrayList<>();;
    private Dialog_menu dialog_menu;
    private Dialog_delete dialog_delete;
    private int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            selectedPosition = savedInstanceState.getInt("selectedPosition");
            Log.i(TAG, "onCreate: "+selectedPosition);
        }
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dialog_menu = new Dialog_menu(this, R.style.Dialog);
        dialog_menu.initView(this);
        dialog_delete = new Dialog_delete(this, R.style.Dialog);
        dialog_delete.initView(this);
        initView();
        initImages();
        myRecyclerAdapter.setmDatas(baseRecyclerBeans);
        notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectedPosition",selectedPosition);
        super.onSaveInstanceState(outState);
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecyclerAdapter = new MyRecyclerAdapter(this);
        /*myRecyclerAdapter.setOnitemListener(this);*/
        viewDataBinding.rvImages.setLayoutManager(linearLayoutManager);
        viewDataBinding.rvImages.setItemAnimator(new DefaultItemAnimator());
        myRecyclerAdapter.setmDatas(baseRecyclerBeans);
        viewDataBinding.rvImages.setAdapter(myRecyclerAdapter);

    }

    private void initImages() {
        try{
            Uri uri = getIntent().getData();
            setImgPath(uri.getPath());
            File file = new File(uri.getPath());
            Log.i(TAG, "initImages: "+ file.getPath());
           /* ImageBean imageBean = new ImageBean();
            imageBean.setFile(file);
            imageBean.setName(file.getName());
            imageBean.setSize(ImageUtils.getFileSize(file));
            baseRecyclerBeans.add(imageBean);*/
            search(file);
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检索当前文件夹下图片
     * */
    private void search(File fileold) throws Exception {
        File[] files;
        if(!fileold.isDirectory()){
            files = new File(fileold.getParent()).listFiles(new FilenameExtFilter(types));
        }else{
            files=fileold.listFiles(new FilenameExtFilter(types));
        }
        if(files.length>0)
        {
            for(int j=0;j<files.length;j++)
            {
                if(!files[j].isDirectory())
                {
                    /*String end = files[j].getName().substring(files[j].getName().lastIndexOf(".") + 1, files[j].getName().length()).toLowerCase();
                    end = end.toUpperCase();
                    if(Arrays.asList(types).contains(end) && !files[j].equals(fileold))
                    {*/
                        ImageBean imageBean = new ImageBean();
                        imageBean.setFile(files[j]);
                        Log.i(TAG, "search: "+files[j]);
                        imageBean.setName(files[j].getName());
                        imageBean.setSize(ImageUtils.getFileSize(files[j]));
                        baseRecyclerBeans.add(imageBean);
                    /*}*/
                }
            }
        }
        Comparator fileComparator = new FileComparator();
        Collections.sort(baseRecyclerBeans, fileComparator);
        for (int i = 0;i<baseRecyclerBeans.size();i++){
            ImageBean imageBean = (ImageBean) baseRecyclerBeans.get(i);
            if(imageBean.getFile().equals(fileold)){
                selectedPosition = i;
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_MENU:
//                    menu_popupWindow.showPopupWindow(viewDataBinding.getRoot());
                    dialog_menu.show();
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if(selectedPosition-1 >= 0){
                        selectedPosition -=1;
                        scrollToPosition(selectedPosition);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if(selectedPosition+1 < baseRecyclerBeans.size()){
                        selectedPosition +=1;
                        scrollToPosition(selectedPosition);
                    }
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void doClick(View view) {
        ImageBean imageBean = (ImageBean) myRecyclerAdapter.getmDatas().get(selectedPosition);
        switch (view.getId()){
            case R.id.bt_rotate_left:
                imageBean.setRotateRotationAngle(-90);
                break;
            case R.id.bt_rotate_right:
                imageBean.setRotateRotationAngle(90);
                break;
            case R.id.bt_startHDP:
                Bundle bundle = new Bundle();
                bundle.putInt("selectedPosition",selectedPosition);
                onSaveInstanceState(bundle);
                Intent intent = new Intent(this, SlideActivity.class);
                intent.putExtra("datas", (Serializable) myRecyclerAdapter.getmDatas());
                intent.putExtra("selectedPosition",selectedPosition);
                intent.putExtra("imgPath",getImgPath());
                startActivity(intent);
                dialog_menu.dismiss();
                break;
            /*case R.id.bt_details:
                Toast.makeText(this,""+imageBean.toString(),Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.bt_delete:
                dialog_delete.show();
                break;
            case R.id.tv_delete:
                int i = ImageUtils.deleteFile(imageBean.getFile().getPath());
                switch (i){
                    case ImageUtils.FILE_DELETE_TRUE:
                        if(selectedPosition == myRecyclerAdapter.getmDatas().size()-1){
                            myRecyclerAdapter.getmDatas().remove(selectedPosition);
                            selectedPosition--;
                        }else{
                            myRecyclerAdapter.getmDatas().remove(selectedPosition);
                        }
                        notifyDataSetChanged();
                        Toast.makeText(this,"成功删除" + imageBean.getName(),Toast.LENGTH_SHORT).show();
                        break;
                    case ImageUtils.FILE_DELETE_FALSE:
                        Toast.makeText(this, "删除失败",Toast.LENGTH_SHORT).show();
                        break;
                    case ImageUtils.FILE_DELETE_NOFILE:
                        Toast.makeText(this, imageBean.getName() + "不存在！",Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog_delete.dismiss();
                break;
            case R.id.tv_cancel:
                dialog_delete.dismiss();
                break;
        }
    }

    public void notifyDataSetChanged(){
        viewDataBinding.setSize(myRecyclerAdapter.getmDatas().size());
        viewDataBinding.setPostion(selectedPosition+1);
        myRecyclerAdapter.notifyDataSetChanged();
        viewDataBinding.rvImages.scrollToPosition(selectedPosition);
    }

    /*@Override
    public void onitemClick(int postion) {

    }

    @Override
    public void onitemFocusListener(View view, boolean hasfocus, int position) {
        if(hasfocus){
            selectedPosition = position;
            viewDataBinding.setPostion(selectedPosition+1);
        }
    }*/

    void scrollToPosition(int selectedPosition){
        viewDataBinding.setPostion(selectedPosition+1);
        viewDataBinding.rvImages.smoothScrollToPosition(selectedPosition);
    }

}