package com.nfg.devlot.dehariprovider.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nfg.devlot.dehariprovider.Activity.WorkerProfileActivity;
import com.nfg.devlot.dehariprovider.Models.WorkersModel;
import com.nfg.devlot.dehariprovider.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by hassan on 4/3/18.
 */

public class WorkersAdapter extends RecyclerView.Adapter<WorkersAdapter.ViewHolderWorkersAdapter> {

    Context context;
    ArrayList<WorkersModel> menuData;
    LayoutInflater inflater;

    public WorkersAdapter(Context context, ArrayList<WorkersModel> data) {
        this.context = context;
        this.menuData = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolderWorkersAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.top_rated_workers_list_row_layout, parent, false);
        return new ViewHolderWorkersAdapter(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderWorkersAdapter holder, int position) {

        if (holder != null) {
            holder.profileName.setText(menuData.get(position).getName());
            holder.ratingBar.setRating(Float.parseFloat(menuData.get(position).getAverage()));

            downloadImage(holder.profileView, menuData.get(position).getImagePath());
        }
    }


    private void downloadImage(ImageView profileView, String imagePath) {
        Picasso.get()
                .load(imagePath)
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(profileView);
    }

    public void UpdateRecord(ArrayList<WorkersModel> data)
    {
        this.menuData = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return menuData.size();
    }

    class ViewHolderWorkersAdapter extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        RelativeLayout  parentLayout;
        ImageView       profileView;
        TextView        profileName;
        RatingBar       ratingBar;

        protected ViewHolderWorkersAdapter(View view)
        {
            super(view);

            parentLayout    = (RelativeLayout)view.findViewById(R.id.parent_relativeLayout_topRatedWorkers);
            profileView     = (ImageView)     view.findViewById(R.id.workerProfile_imageView_topRatedWorkers);
            profileName     = (TextView)      view.findViewById(R.id.workerName_textView_topRatedWorker);
            ratingBar       = (RatingBar)     view.findViewById(R.id.ratingBar_topRatedWorker_xml);

            parentLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if(v.getId() == R.id.parent_relativeLayout_topRatedWorkers)
            {
                Intent intent = new Intent(context,WorkerProfileActivity.class);
                intent.putExtra("sid",menuData.get(getAdapterPosition()).getId());
                intent.putExtra("name",menuData.get(getAdapterPosition()).getName());
                intent.putExtra("phone",menuData.get(getAdapterPosition()).getPhoneNumber());
                intent.putExtra("location",menuData.get(getAdapterPosition()).getLocation());
                intent.putExtra("averagerating",menuData.get(getAdapterPosition()).getAverage());
                intent.putExtra("imagepath",menuData.get(getAdapterPosition()).getImagePath());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        }
    }

}
