package com.yxkj.basemoudle.util;

import android.content.Context;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.yxkj.basemoudle.R;

/**
 *
 */

public class DividerUtil {
    public static DividerDecoration getDividerDecoration(Context context) {
        return new DividerDecoration.Builder(context)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.colorAccent)
                .build();
    }
}
