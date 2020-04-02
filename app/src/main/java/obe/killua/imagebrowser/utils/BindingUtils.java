package obe.killua.imagebrowser.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.File;
import obe.killua.imagebrowser.anim.AnimBaiYeChuang;
import obe.killua.imagebrowser.anim.AnimShanXingZhanKai;
import obe.killua.imagebrowser.anim.AnimXiangNeiRongJie;
import obe.killua.imagebrowser.anim.EnterAnimLayout;


/**
 * Created by wangtao on 2017/12/11.
 */

public class BindingUtils {

    @BindingAdapter({"drawableLeft","w","h","p"})
    public static void drawableLeft(TextView tv, Drawable rs, int w, int h, int padding){
        rs.setBounds(0,0,w,h);
        tv.setCompoundDrawablePadding(padding);
        tv.setCompoundDrawables(rs,null,null,null);
    }

    @BindingAdapter({"showImg","rotateRotationAngle"})
    public static void showImg(ImageView iv, File file,float rotateRotationAngle){
        Glide.with(iv.getContext()).load(file).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new RotateTransformation(iv.getContext(),rotateRotationAngle)).into(iv);
    }

    @BindingAdapter({"showImg_Slide"})
    public static void showImg_Slide(EnterAnimLayout v, File file){
        ImageView iv = (ImageView) v.getChildAt(0);
        Glide.with(iv.getContext()).load(file).diskCacheStrategy(DiskCacheStrategy.NONE)/*.animate(R.anim.scal_showimage)*/.into(new GlideDrawableImageViewTarget(iv){
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
