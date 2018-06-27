package com.nfg.devlot.dehariprovider.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nfg.devlot.dehariprovider.Activity.ServicesActivity;
import com.nfg.devlot.dehariprovider.Models.CategoryModel;
import com.nfg.devlot.dehariprovider.R;
import java.util.ArrayList;

/**
 * Created by hassan on 3/31/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolderCategoryAdapter> {

    Context                     context;
    ArrayList<CategoryModel>    menuData;
    LayoutInflater              inflater;


    public CategoryAdapter(Context context, ArrayList<CategoryModel> data)
    {
        this.context    = context;
        this.menuData   = data;
        inflater        = LayoutInflater.from(context);
    }

    @Override
    public ViewHolderCategoryAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view  = inflater.inflate(R.layout.category_list_row_layout,parent,false);
        return new ViewHolderCategoryAdapter(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderCategoryAdapter holder, int position) {

        if(holder !=null)
        {
            holder.categoryName.setText(menuData.get(position).getCname());
        }
    }

    public void updateRecord(ArrayList<CategoryModel> newData)
    {
        this.menuData = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return menuData.size();
    }

    public class ViewHolderCategoryAdapter extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView        categoryName;
        ImageView       categoryImage;
        RelativeLayout  parentLayout;

        public ViewHolderCategoryAdapter(View itemView) {
            super(itemView);

            categoryName    = (TextView)  itemView.findViewById(R.id.category_textView_list_row_layout);
            categoryImage   = (ImageView) itemView.findViewById(R.id.category_imageView_list_row_layout);
            parentLayout    = (RelativeLayout) itemView.findViewById(R.id.parent_relativeLayout_list_row_layout);


            /**
             *
             * HANDLING RECYCLER VIEW ON ITEM CLICK LISTENER HERE
             *
             * @code parent.setOnClickListener(OnClickListener)
             * @func onClick(View v)
             *
             * */


            parentLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {

            if(v.getId() == R.id.parent_relativeLayout_list_row_layout)
            {
                Intent intent = new Intent(context,ServicesActivity.class);
                intent.putExtra("categoryId", menuData.get(getAdapterPosition()).getCid());

                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
        }
    }
}
