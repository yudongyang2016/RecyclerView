package com.wd.recycleview.flexbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.wd.recycleview.R;
import com.wd.recycleview.widget.SuperTextView;

import java.util.List;

/**
 * @author ydy
 */

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private List<String> labelList;
    private List<String> selectList;
    private OnItemClickListener onItemClickListener;

    public LabelAdapter(Context context, List<String> labelList, List<String> selectList) {
        this.context = context;
        this.labelList = labelList;
        this.selectList = selectList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return labelList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_favor_tag, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTag.setText(labelList.get(position));
        ViewGroup.LayoutParams params = holder.mTag.getLayoutParams();
        if (params instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams layoutParams = (FlexboxLayoutManager.LayoutParams) holder.mTag.getLayoutParams();
            layoutParams.setFlexGrow(1.0f);
        }
        if (selectList.contains(holder.mTag.getText().toString())) {
            holder.mTag.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.mTag.setStrokeColor(context.getResources().getColor(R.color.colorAccent));
        }
        holder.mTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, holder.getLayoutPosition());
            }
        });
        holder.mTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = holder.mTag.getCurrentTextColor();
                if (color == context.getResources().getColor(R.color.colorAccent)) {
                    holder.mTag.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    holder.mTag.setStrokeColor(context.getResources().getColor(R.color.colorPrimary));
                    String text = holder.mTag.getText().toString();
                    selectList.remove(text);
                } else {
                    holder.mTag.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    holder.mTag.setStrokeColor(context.getResources().getColor(R.color.colorAccent));
                    String text = holder.mTag.getText().toString();
                    selectList.add(text);
                }
            }
        });
    }

    public List<String> getSelectList() {
        return selectList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SuperTextView mTag;

        public ViewHolder(View itemView) {
            super(itemView);
            //必要的!!!
            itemView.setTag(this);
            mTag = (SuperTextView) itemView.findViewById(R.id.stv_tag);
        }
    }

}
