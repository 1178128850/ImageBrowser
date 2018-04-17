package obe.killua.imagebrowser.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.File;
import java.util.List;

import obe.killua.imagebrowser.R;
import obe.killua.imagebrowser.adapter.MyRecyclerAdapter;
import obe.killua.imagebrowser.anim.Anim;
import obe.killua.imagebrowser.anim.AnimBaiYeChuang;
import obe.killua.imagebrowser.anim.AnimShanXingZhanKai;
import obe.killua.imagebrowser.anim.AnimXiangNeiRongJie;
import obe.killua.imagebrowser.anim.EnterAnimLayout;
import obe.killua.imagebrowser.bean.BaseRecyclerBean;


/**
 * Created by wangtao on 2017/12/11.
 */

public class BindingUtils {

    @BindingAdapter({"showImg","rotateRotationAngle"})
    public static void showImg(ImageView iv, File file,float rotateRotationAngle){
        Glide.with(iv.getContext()).load(file).diskCacheStrategy(DiskCacheStrategy.SOURCE).bitmapTransform(new RotateTransformation(iv.getContext(),rotateRotationAngle)).into(iv);
    }

    @BindingAdapter({"showImg_Slide"})
    public static void showImg_Slide(EnterAnimLayout v, File file){
        ImageView iv = (ImageView) v.getChildAt(0);
        Glide.with(iv.getContext()).load(file).diskCacheStrategy(DiskCacheStrategy.SOURCE)/*.animate(R.anim.scal_showimage)*/.into(new GlideDrawableImageViewTarget(iv){
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                int i = PreferencesService.GetInt(v.getContext(), PreferencesService.SCROLL_ANIM, PreferencesService.ANIME_ONE);
                switch (i){
                    case PreferencesService.ANIME_ONE:
                        new AnimBaiYeChuang(v).startAnimation();
                        break;
                    case PreferencesService.ANIME_TWO:
                        new AnimShanXingZhanKai(v).startAnimation();
                        break;
                    case PreferencesService.ANIME_THREE:
                        new AnimXiangNeiRongJie(v).startAnimation();
                        break;

                }
                /*Animation animation1 = AnimationUtils.loadAnimation(iv.getContext(),R.anim.scal_showimage);
                iv.startAnimation(animation1);*/
            }
        });
    }
}
