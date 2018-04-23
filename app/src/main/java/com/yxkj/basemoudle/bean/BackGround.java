package com.yxkj.basemoudle.bean;

/**
 * Created by 曾强 on 2017/11/29.
 */

public class BackGround {
    public int res_id;
    public boolean isSelect;

    public BackGround(int res_id, boolean isSelect) {
        this.res_id = res_id;
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "BackGround{" +
                "res_id=" + res_id +
                ", isSelect=" + isSelect +
                '}';
    }
}
