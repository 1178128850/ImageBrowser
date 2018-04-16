package obe.killua.imagebrowser.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by wangtao on 2017/12/11.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;



    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }

    public MyViewHolder(View itemView) {
        super(itemView);
    }
}
