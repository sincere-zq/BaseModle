package com.yxkj.basemoudle.adapter;

import android.content.Context;
import android.widget.TextView;

import com.yxkj.basemoudle.R;
import com.yxkj.basemoudle.base.ListBaseAdapter;
import com.yxkj.basemoudle.base.SuperViewHolder;

/**
 *
 */

public class NormalAdapter extends ListBaseAdapter<String> {

    public NormalAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_normal;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        String text = mDataList.get(position);
        TextView textView = holder.getView(R.id.tv_normal);
        textView.setText(text);
    }
}
