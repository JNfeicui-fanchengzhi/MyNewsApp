package com.fanfan.mynewsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fanfan.mynewsapp.R;
import com.fanfan.mynewsapp.bean.NewBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/15.
 */
public class NewAdapter extends BaseAdapter {
    private List<NewBean.DataBean> mData=new ArrayList<>();
    private LayoutInflater mInflater;
    private ImageLoader mLoader;

    public NewAdapter(Context context, ImageLoader imageLoader){
        mLoader=imageLoader;
        mInflater=LayoutInflater.from(context);
    }
    public void addData(List<NewBean.DataBean> been){
        mData.addAll(been);
    }
    @Override
    public int getCount() {
        if (mData!=null){
            return mData.size();
        }
        return 0;
    }

    @Override
    public NewBean.DataBean getItem(int position) {
        if (mData!=null){
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_list_new,null);
            viewHolder.icon= (NetworkImageView) convertView.findViewById(R.id.iv_pic);
            viewHolder.title= (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.text= (TextView) convertView.findViewById(R.id.tv_text);
            viewHolder.data= (TextView) convertView.findViewById(R.id.tv_data);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        NewBean.DataBean bean=mData.get(position);
        viewHolder.icon.setDefaultImageResId(R.mipmap.ic_launcher);
        viewHolder.icon.setErrorImageResId(R.drawable.failed_image);
        viewHolder.icon.setImageUrl(bean.getIcon(),mLoader);
        viewHolder.title.setText(bean.getTitle());
        viewHolder.text.setText(bean.getSummary());
        viewHolder.data.setText(bean.getStamp());
        return convertView;
    }
    private class ViewHolder{
        NetworkImageView icon;
        TextView title;
        TextView text;
        TextView data;

    }
}
