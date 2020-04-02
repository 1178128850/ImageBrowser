package obe.killua.imagebrowser.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


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
