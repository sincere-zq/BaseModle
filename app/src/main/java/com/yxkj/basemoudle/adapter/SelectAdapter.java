package com.yxkj.basemoudle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yxkj.basemoudle.R;
import com.yxkj.basemoudle.bean.BackGround;
import com.yxkj.basemoudle.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 曾强 on 2017/11/29.
 */

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {
    private Context context;
    private List<BackGround> backGrounds;
    private List<BackGround> backGroundSelect = new ArrayList<>();

    public void setBackGrounds(List<BackGround> backGrounds) {
        this.backGrounds = backGrounds;
        notifyDataSetChanged();
    }

    public SelectAdapter(Context context) {

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_one, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //只看这里
        final BackGround backGround = backGrounds.get(position);
        if (backGround.isSelect) {
            holder.itemView.setBackgroundColor(Color.RED);
        } else {
            holder.itemView.setBackgroundColor(Color.GREEN);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backGround.isSelect) {
                    backGroundSelect.remove(backGround);
                    backGround.isSelect = false;
                } else {
                    backGround.isSelect = true;
                    backGroundSelect.add(backGround);
                }
                LogUtil.e(backGroundSelect.toString());
                notifyDataSetChanged();
                notifyItemChanged(position, backGround);
            }
        });
    }

    @Override
    public int getItemCount() {
        return backGrounds == null ? 0 : backGrounds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
