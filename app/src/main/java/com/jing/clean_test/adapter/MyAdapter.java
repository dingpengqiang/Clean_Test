package com.jing.clean_test.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jing.clean.R;
import com.jing.clean_test.model.RowsBean;

import java.util.List;

/**
 * Created by Ding.pengqiang
 * on 2017/3/16.
 */

public class MyAdapter extends BaseQuickAdapter<RowsBean,BaseViewHolder> {

    public MyAdapter(int layoutResId, List<RowsBean> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, RowsBean item) {
        helper.setText(R.id.title,item.getTitle())
        .setText(R.id.description,item.getDescription());

        //加载图片
        Glide.with(mContext).load(item.getImageHref())
                .into((ImageView) helper.getView(R.id.imageHref));

    }
}
