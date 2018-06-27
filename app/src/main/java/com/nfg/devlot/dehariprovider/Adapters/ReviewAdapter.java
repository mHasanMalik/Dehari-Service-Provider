package com.nfg.devlot.dehariprovider.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.nfg.devlot.dehariprovider.Models.ReviewModel;
import com.nfg.devlot.dehariprovider.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by hassan on 4/6/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolderReviewAdapter> {

    private ArrayList<ReviewModel>  menuData;
    private LayoutInflater          inflater;
    private Context                 context;


    public ReviewAdapter(Context context, ArrayList<ReviewModel> data)
    {
        this.context    = context;
        this.menuData   = data;
        inflater        = LayoutInflater.from(context);
    }

    @Override
    public ViewHolderReviewAdapter onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.profile_review_list_row_layout,parent,false);
        return new ViewHolderReviewAdapter(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderReviewAdapter holder, int position)
    {
        if(holder!=null)
        {
            holder.reviewerName.setText(menuData.get(position).getReviewerName());
            holder.comment.setText(menuData.get(position).getComment());
            holder.rating.setRating(Float.parseFloat(menuData.get(position).getRating()));
        }

    }

    @Override
    public int getItemCount() {
        return menuData.size();
    }


    public void UpdateRecord(ArrayList<ReviewModel> menuData)
    {
        this.menuData = menuData;
        notifyDataSetChanged();
    }

    protected class ViewHolderReviewAdapter extends RecyclerView.ViewHolder
    {
        TextView    reviewerName;
        TextView    comment;
        TextView    dateOfReview;
        RatingBar   rating;

        public ViewHolderReviewAdapter(View view)
        {
            super(view);

            reviewerName    = (TextView) view.findViewById(R.id.reviewCommenterName_textView_workerProfile_xml);
            comment         = (TextView) view.findViewById(R.id.reviewCommenter_textView_workerProfile_xml);
            dateOfReview    = (TextView) view.findViewById(R.id.reviewDate_textView_reviewProfile_xml);
            rating          = (RatingBar)view.findViewById(R.id.userRating_ratingBar_workerProfile_xml);

        }
    }
}
