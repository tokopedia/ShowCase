package com.tokopedia.showcase.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hendrysetiadi on 13/11/2016.
 */

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder>{

    private List<SampleItem> sampleItemList;
    public SampleAdapter (List<SampleItem> sampleItemList){
        this.sampleItemList = sampleItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.sample_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SampleItem sampleItem = sampleItemList.get(position);
        holder.imageView.setImageResource(sampleItem.getIconRes());
        holder.textViewTitle.setText (sampleItem.getTitle());
        holder.textViewDesc.setText(sampleItem.getDescription());

    }

    @Override
    public int getItemCount() {
        if (null!= sampleItemList) {
            return sampleItemList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDesc;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            textViewTitle = (TextView) itemView.findViewById(R.id.tv_title);
            textViewDesc = (TextView) itemView.findViewById(R.id.tv_desc);
        }
    }

}
