package com.exampdm.moneybook.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exampdm.moneybook.R;
import com.exampdm.moneybook.db.entity.TagEntity;

import java.util.Collections;
import java.util.List;

public class TagFragmentAdapter extends RecyclerView.Adapter<TagFragmentAdapter.TagViewHolder> {

    public interface onTagClickListener{
        void onTagClick(TagEntity tag);
    }
    class TagViewHolder extends RecyclerView.ViewHolder{
        private TextView tagView;
        private TagViewHolder(View itemView){
            super(itemView);

            tagView= itemView.findViewById(R.id.tag_element_textview);
        }

        private void bind(final TagEntity tag, final onTagClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTagClick(tag);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<TagEntity> mTags = Collections.emptyList();
    private final onTagClickListener listener;

    public TagFragmentAdapter(Context context, onTagClickListener tagListener){
        mInflater=LayoutInflater.from(context);
        listener= tagListener;
    }
    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tag_element, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        TagEntity current = mTags.get(position);
        holder.bind(current,listener);
        holder.tagView.setText(current.getTag());

    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public void setmTags(List<TagEntity> tags){
        mTags=tags;
    }



}
