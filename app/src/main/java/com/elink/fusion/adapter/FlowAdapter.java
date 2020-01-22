package com.elink.fusion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elink.fusion.R;
import com.elink.fusion.bean.FlowBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Evloution
 * @date 2020-01-21
 * @email 15227318030@163.com
 * @description
 */
public class FlowAdapter extends BaseAdapter {

    private Context context;
    private List<FlowBean> flowBeanList;
    private LayoutInflater mInflater;

    public FlowAdapter(Context context, List<FlowBean> flowBeanList) {
        this.context = context;
        this.flowBeanList = flowBeanList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return flowBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return flowBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_flowlist, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemFlowName.setText(flowBeanList.get(position).NAME);
        viewHolder.itemFlowPromiseDays.setText(flowBeanList.get(position).PROMISE_DAYS + "个工作日");
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.item_flow_name)
        TextView itemFlowName;
        @BindView(R.id.item_flow_promise_days)
        TextView itemFlowPromiseDays;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
