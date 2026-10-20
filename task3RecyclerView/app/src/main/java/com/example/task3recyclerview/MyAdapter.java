package com.example.task3recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> mDataList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MyAdapter(List<String> dataList, OnItemClickListener listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = mDataList.get(position);
        holder.tvName.setText(name);

        // 动态判断名字设置图片（如果你图片名字不同，这里要同步改）
        if (name.contains("Lion")) holder.ivImg.setImageResource(R.drawable.lion);
        else if (name.contains("Tiger")) holder.ivImg.setImageResource(R.drawable.tiger);
        else if (name.contains("Monkey")) holder.ivImg.setImageResource(R.drawable.monkey);
        else if (name.contains("Dog")) holder.ivImg.setImageResource(R.drawable.dog);
        else if (name.contains("Cat")) holder.ivImg.setImageResource(R.drawable.cat);
        else if (name.contains("Elephant")) holder.ivImg.setImageResource(R.drawable.elephant);
        else holder.ivImg.setImageResource(R.mipmap.ic_launcher); // 新增的项默认用机器人图标

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
