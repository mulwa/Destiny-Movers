package com.example.gen.destinymovers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gen.destinymovers.Pojo.Request;

import java.util.List;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.MyViewHolder> {
    private List<Request> requestList;
    private Context context;

    public MyRequestAdapter(List<Request> requestList, Context context) {
        this.requestList = requestList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_request_item_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Request request = requestList.get(position);
        holder.mfrom.setText(request.getPickaddress());
        holder.mto.setText(request.getDropaddress());
        holder.mcost.setText(String.valueOf("Cost:" +request.getCost()));
        holder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Ready to delete",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mto,mfrom, mcost;
        public ImageButton mdelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            mto = itemView.findViewById(R.id.tv_to);
            mfrom = itemView.findViewById(R.id.tv_from);
            mcost  =  itemView.findViewById(R.id.tv_cost);
            mdelete  = itemView.findViewById(R.id.imb_delete);

        }
    }
}
