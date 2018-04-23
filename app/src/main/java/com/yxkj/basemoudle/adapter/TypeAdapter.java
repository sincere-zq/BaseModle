package com.yxkj.basemoudle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yxkj.basemoudle.R;
import com.yxkj.basemoudle.bean.BackGround;

import java.util.List;

/**
 * Created by 曾强 on 2017/11/28.
 */

public class TypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<BackGround> stringList;

    public TypeAdapter(Context context) {
        this.context = context;
    }

    public void setStringList(List<BackGround> stringList) {
        this.stringList = stringList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new OneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_one, parent, false));
            case 2:
                return new TwoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_two, parent, false));
            case 3:
                return new ThreeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_three, parent, false));
            case 4:
                return new FourViewHolder(LayoutInflater.from(context).inflate(R.layout.item_four, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneViewHolder) {
            OneViewHolder oneViewHolder = (OneViewHolder) holder;
            oneViewHolder.img1.setBackgroundColor(Color.RED);
        } else if (holder instanceof TwoViewHolder) {
            TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
            twoViewHolder.img1.setBackgroundColor(Color.RED);
            twoViewHolder.img2.setBackgroundColor(Color.RED);
        } else if (holder instanceof ThreeViewHolder) {
            ThreeViewHolder threeViewHolder = (ThreeViewHolder) holder;
            threeViewHolder.img1.setBackgroundColor(Color.RED);
            threeViewHolder.img2.setBackgroundColor(Color.RED);
            threeViewHolder.img3.setBackgroundColor(Color.RED);
        } else if (holder instanceof FourViewHolder) {
            FourViewHolder fourViewHolder = (FourViewHolder) holder;
            fourViewHolder.img1.setBackgroundColor(Color.RED);
            fourViewHolder.img2.setBackgroundColor(Color.RED);
            fourViewHolder.img3.setBackgroundColor(Color.RED);
            fourViewHolder.img4.setBackgroundColor(Color.RED);
        }
        //只看这里
        final BackGround backGround = stringList.get(position);
        if (backGround.isSelect) {
            holder.itemView.setBackgroundColor(Color.RED);
        } else {
            holder.itemView.setBackgroundColor(Color.GREEN);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backGround.isSelect) {
                    backGround.isSelect = false;
                } else {
                    backGround.isSelect = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        int size = stringList.size();
        switch (size) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return stringList == null ? 0 : 1;
    }

    class OneViewHolder extends RecyclerView.ViewHolder {
        ImageView img1;

        public OneViewHolder(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder {
        ImageView img1, img2;

        public TwoViewHolder(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            img2 = (ImageView) itemView.findViewById(R.id.img2);
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder {
        ImageView img1, img2, img3;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            img2 = (ImageView) itemView.findViewById(R.id.img2);
            img3 = (ImageView) itemView.findViewById(R.id.img3);
        }
    }

    class FourViewHolder extends RecyclerView.ViewHolder {
        ImageView img1, img2, img3, img4;

        public FourViewHolder(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            img2 = (ImageView) itemView.findViewById(R.id.img2);
            img3 = (ImageView) itemView.findViewById(R.id.img3);
            img4 = (ImageView) itemView.findViewById(R.id.img4);
        }
    }


}
