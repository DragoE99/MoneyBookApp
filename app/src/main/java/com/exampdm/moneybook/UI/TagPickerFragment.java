package com.exampdm.moneybook.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exampdm.moneybook.R;
import com.exampdm.moneybook.db.entity.TagEntity;
import com.exampdm.moneybook.viewmodel.MoneyViewModel;

import java.util.List;

public class TagPickerFragment extends DialogFragment{
    private MoneyViewModel moneyViewModel;
    private TagEntity clickedTag;

    public interface tagSelectedListener{
        void getTagSelected(TagEntity tag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.tag_picker_fragment, container, false);
         moneyViewModel = ViewModelProviders.of(getActivity()).get(MoneyViewModel.class);

        RecyclerView recyclerView= view.findViewById(R.id.tag_recycler_view);
        final TagFragmentAdapter adapter = new TagFragmentAdapter(getContext(),new TagFragmentAdapter.onTagClickListener(){
            @Override
            public void onTagClick(TagEntity tag){
                clickedTag=tag;
                setSelectedTag(clickedTag);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        moneyViewModel.getAllTags().observe(this, new Observer<List<TagEntity>>() {
            @Override
            public void onChanged(List<TagEntity> tags) {
                adapter.setmTags(tags);
                moneyViewModel.updtTagList();
            }
        });
        return view;
    }

    private void setSelectedTag(TagEntity tagEntity){
        tagSelectedListener listener = (tagSelectedListener) getTargetFragment();
        assert listener != null;
        listener.getTagSelected(tagEntity);
        dismiss();
    }


}
