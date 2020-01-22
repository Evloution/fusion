package com.elink.fusion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elink.fusion.R;
import com.elink.fusion.bean.InstanceBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Evloution
 * @date 2020-01-21
 * @email 15227318030@163.com
 * @description
 */
public class InstanceAdapter extends BaseAdapter {

    private Context context;
    private List<InstanceBean> instanceBeanList;
    private LayoutInflater mInflater;
    private String typeFase = null; // 状态为1 时显示的字
    private int fontColor = 0; // 字的颜色
    private int returnStatus = 0; // 返回的状态

    public InstanceAdapter(Context context, List<InstanceBean> instanceBeanList) {
        this.context = context;
        this.instanceBeanList = instanceBeanList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return instanceBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return instanceBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_instance, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 项目标题
        viewHolder.itemInstanceTitle.setText(instanceBeanList.get(position).TITLE);
        // 项目当前的流程
        viewHolder.itemInstanceContent.setText(instanceBeanList.get(position).CONTENT);
        // 项目状态
        returnStatus = instanceBeanList.get(position).STATUS;
        /**
         * 0|初始状态
         * 1|办理中
         * 2|已结束
         */
        if (returnStatus == 0) {
            typeFase = "未受理";
            fontColor = R.color.gray_three;
        } else if (returnStatus == 1) {
            typeFase = "办理中";
            fontColor = R.color.red;
        } else if (returnStatus == 2) {
            typeFase = "已结束";
            fontColor = R.color.limegreen;
            viewHolder.itemInstanceEndtimeTxt.setVisibility(View.VISIBLE);
            viewHolder.itemInstanceEndtimeTxt1.setVisibility(View.VISIBLE);
            viewHolder.itemInstanceEndtimeTxt.setText(instanceBeanList.get(position).END_TIME);
        }
        viewHolder.itemInstanceStatus.setText(typeFase);
        viewHolder.itemInstanceStatus.setTextColor(context.getResources().getColor(fontColor));
        // 项目创建时间
        viewHolder.itemInstanceCreatedtimeTxt.setText(instanceBeanList.get(position).CREATED_TIME);
        // 项目开始时间
        String startTime = instanceBeanList.get(position).START_TIME;
        if (startTime == null || "".equals(startTime)) {
            viewHolder.itemInstanceStarttimeTxt.setText("项目未开始受理");
        } else {
            viewHolder.itemInstanceStarttimeTxt.setText(startTime);
        }
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.item_instance_title)
        TextView itemInstanceTitle;
        @BindView(R.id.item_instance_status)
        TextView itemInstanceStatus;
        @BindView(R.id.item_instance_createdtime_txt1)
        TextView itemInstanceCreatedtimeTxt1;
        @BindView(R.id.item_instance_createdtime_txt)
        TextView itemInstanceCreatedtimeTxt;
        @BindView(R.id.item_instance_starttime_txt1)
        TextView itemInstanceStarttimeTxt1;
        @BindView(R.id.item_instance_starttime_txt)
        TextView itemInstanceStarttimeTxt;
        @BindView(R.id.item_instance_endtime_txt1)
        TextView itemInstanceEndtimeTxt1;
        @BindView(R.id.item_instance_endtime_txt)
        TextView itemInstanceEndtimeTxt;
        @BindView(R.id.item_instance_content1)
        TextView itemInstanceContent1;
        @BindView(R.id.item_instance_content)
        TextView itemInstanceContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
