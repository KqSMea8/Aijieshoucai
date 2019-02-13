package com.example.rongjiaying.aijieshoucai.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 只是根据baseAdapter来弄的 适配器
 * Created by Ken on 2015/10/21.
 */
public abstract class AbsBaseAdapter<T> extends BaseAdapter {

    protected Context context;
    private List<T> datas;
    private int resid;

    public AbsBaseAdapter(Context context, int resid) {
        this.context = context;
        this.resid = resid;
        datas = new ArrayList<T>();
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return datas;
    }

    public void addDatas(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, resid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindDatas(viewHolder, datas.get(position), position);
        return convertView;
    }

    public abstract void bindDatas(ViewHolder viewHolder, T data, int position);

    /**
     */
    public class ViewHolder {
        private View layoutView;
        private Map<Integer, View> mapCache = new HashMap<Integer, View>();

        public ViewHolder(View layoutView) {
            this.layoutView = layoutView;
        }

        public View getView(int id) {
            if (mapCache.containsKey(id)) {
                return mapCache.get(id);
            } else {
                View v = layoutView.findViewById(id);
                mapCache.put(id, v);
                return v;
            }
        }

        public ImageView getImageView(int id) {
            if (mapCache.containsKey(id)) {
                return (ImageView) mapCache.get(id);
            } else {
                ImageView v = (ImageView) layoutView.findViewById(id);
                mapCache.put(id, v);
                return v;
            }
        }

        public TextView getTextView(int id) {
            if (mapCache.containsKey(id)) {
                return (TextView) mapCache.get(id);
            } else {
                TextView v = (TextView) layoutView.findViewById(id);
                mapCache.put(id, v);
                return v;
            }
        }
    }
    public void clear() {
//        int itemCount = datas.size();
//        datas.clear();
//        this.notifyItemRangeRemoved(0,itemCount);

        if (datas == null || datas.size() <= 0)
            return;

        for (Iterator it = datas.iterator(); it.hasNext(); ) {

            T t = (T) it.next();
            int position = datas.indexOf(t);
            it.remove();
            notifyDataSetChanged();
        }
    }
}
