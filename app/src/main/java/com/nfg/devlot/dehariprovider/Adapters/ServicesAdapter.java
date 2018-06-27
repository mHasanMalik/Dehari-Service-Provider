package com.nfg.devlot.dehariprovider.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nfg.devlot.dehariprovider.Models.ServicesModel;
import com.nfg.devlot.dehariprovider.R;
import java.util.ArrayList;

/**
 * Created by hassan on 4/2/18.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolderServicesAdapter> {

    private Context                     context;
    private LayoutInflater              inflater;
    private ArrayList<ServicesModel>    menuData;

    public ServicesAdapter(Context context, ArrayList<ServicesModel> data)
    {
        this.context    = context;
        this.menuData   = data;
        inflater        = LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolderServicesAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.services_list_row_layout,parent,false);
        return new ViewHolderServicesAdapter(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderServicesAdapter holder, int position) {

        if(holder!=null)
        {
            holder.serviceName.setText(menuData.get(position).getServiceName());
        }
    }

    @Override
    public int getItemCount() {
        return menuData.size();
    }


    public void UpdateRecord(ArrayList<ServicesModel> menuData)
    {
        this.menuData = menuData;
        notifyDataSetChanged();
    }

    protected class ViewHolderServicesAdapter extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView        serviceName;
        RelativeLayout  parentLayout;

        protected ViewHolderServicesAdapter(View view)
        {
            super(view);
            serviceName     = (TextView) view.findViewById(R.id.serviceName_textView_service_xml);
            parentLayout    = (RelativeLayout) view.findViewById(R.id.relativeLayout_services_xml);


            /**
             *
             * SETTING RECYCLER VIEW ON ITEM CLICK LISTENER HERE
             * @code parentLayout.setOnClickListener(OnClickListener);
             * @func onClick(View v);
             *
             * */

            parentLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if(v.getId() == R.id.relativeLayout_services_xml)
            {

            }
        }
    }
}
