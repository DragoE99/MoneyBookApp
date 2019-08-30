package com.exampdm.moneybook.UI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.exampdm.moneybook.R;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private MoneyItemAdapter mAdapter;

    private Drawable icon;
    private  ColorDrawable background;

    private Drawable iconEdit;
    private  ColorDrawable backgroundEdit;

    public SwipeToDeleteCallback( MoneyItemAdapter adapter){
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter=adapter;

        icon= ContextCompat.getDrawable(mAdapter.getContext(),
                R.drawable.ic_delete_white);
        background=new ColorDrawable(Color.RED);

        iconEdit= ContextCompat.getDrawable(mAdapter.getContext(),
                R.drawable.ic_edit_white);
        backgroundEdit=new ColorDrawable(Color.parseColor("#ff6d00"));

    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction){
        int position=viewHolder.getAdapterPosition();
        if(direction==ItemTouchHelper.LEFT){
            mAdapter.updtItem(position);
        }else {
            mAdapter.deleteItem(position);
        }

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY, int actionState, boolean isCurrentlyActive){
        super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 40;

        int iconMargin= (itemView.getHeight()-icon.getIntrinsicHeight())/2;
        int iconTop= itemView.getTop()+(itemView.getHeight()-icon.getIntrinsicHeight())/2;
        int iconBottom = iconTop+icon.getIntrinsicHeight();

        if(dX>0){
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = iconLeft+ icon.getIntrinsicWidth();

            icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft()+((int)dX)+ backgroundCornerOffset, itemView.getBottom());
            backAndIconDraw(background, icon, c);
        }else if(dX<0){
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;

            iconEdit.setBounds(iconLeft, iconTop, iconRight, iconBottom);


            backgroundEdit.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
            backAndIconDraw(backgroundEdit, iconEdit, c);
        }else {
            background.setBounds(0,0,0,0);
            backAndIconDraw(background, icon, c);
        }



    }

    private void backAndIconDraw(ColorDrawable back, Drawable icon, Canvas c){
        back.draw(c);
        icon.draw(c);
    }
}
