package obe.killua.imagebrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import obe.killua.imagebrowser.R;
import obe.killua.imagebrowser.bean.BaseRecyclerBean;
import obe.killua.imagebrowser.utils.ViewTypeUtiis;

/**
 * Created by wangtao on 2017/12/11.
 * //通用recyclerview.adater
 *
 *   databinding list 数组写法
 <data>
 <import type="java.util.List" />
 <import type="com.jyzd.hytrading.adapter."/>

 <!--<variable
 name="datas"
 type="List&lt;BaseRecyclerBean&gt;" />-->

 </data>
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{


    public interface OnitemListener{
        void onitemClick(int postion);
        void onitemFocusListener(View view, boolean hasfocus, int position);
    }

    public interface OnLongClickListener{
        public boolean onLongClick(View v, int position);
    }

    private List<BaseRecyclerBean> mDatas = new ArrayList<>();

    private Context context;


    private OnitemListener onitemListener;

    private OnLongClickListener onLongClickListener;

    public OnLongClickListener getOnLongClickListener() {
        return onLongClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void setOnitemListener(OnitemListener onitemListener) {
        this.onitemListener = onitemListener;
    }

    public OnitemListener getOnitemListener() {
        return onitemListener;
    }

    public MyRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setmDatas(List<BaseRecyclerBean> mDatas) {
        this.mDatas = mDatas;
    }

    public List<BaseRecyclerBean> getmDatas() {
        return mDatas;
    }

    public MyRecyclerAdapter(List<BaseRecyclerBean> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }



    public void removedItem(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(context);
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(from, ViewTypeUtiis.getLayoutId(viewType), parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(viewDataBinding.getRoot());
        myViewHolder.setBinding(viewDataBinding);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.setIsRecyclable(false);//由于复用导致滑动删除滑动初始状态不准确
        holder.getBinding().setVariable(mDatas.get(position).getItem_bindingkey(),mDatas.get(position));
        holder.getBinding().executePendingBindings();
        /**
         * 自定义配置item事件监听
         * */
        initListener(holder,position);
    }

    private void initListener(MyViewHolder holder, int position) {
        if(onitemListener!=null){
            switch (getItemViewType(position)){
                case ViewTypeUtiis.VIEWTYPE_IMG:
                    holder.getBinding().getRoot().findViewById(R.id.iv_path).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            onitemListener.onitemFocusListener(v,hasFocus,position);
                        }
                    });
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mDatas == null){
            return 0;
        }else{
            return mDatas.get(position).getViewType();
        }
    }
}
