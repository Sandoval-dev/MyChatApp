package com.example.mychatapp.ui.GrubaEkle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapp.GrupModel;
import com.example.mychatapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GroupAddAdapter extends RecyclerView.Adapter<GroupAddAdapter.GrupViewHolder> {

    List<GrupModel> grupModelList;

    public GroupAddAdapter(List<GrupModel> grupModelList){
        this.grupModelList=grupModelList;
    }

    @NonNull
    @Override
    public GroupAddAdapter.GrupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GrupViewHolder grupViewHolder=new GrupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grubaekle_grup,parent,false));
        return grupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAddAdapter.GrupViewHolder holder, int position) {
        GrupModel grupModel=grupModelList.get(position);
        holder.setData(grupModel);
    }

    @Override
    public int getItemCount() {
        return grupModelList.size();
    }

    public class GrupViewHolder extends RecyclerView.ViewHolder{
        ImageView grupImageView;
        TextView grupnameText, grupaciklamaText;
        public GrupViewHolder(View itemView){
            super(itemView);

            grupImageView=itemView.findViewById(R.id.grubaekle_grupresim);
            grupnameText=itemView.findViewById(R.id.grubaekle_grupisim);
            grupaciklamaText=itemView.findViewById(R.id.grubaekle_grupaciklama);
        }
        public void setData(GrupModel grupModel) {
            grupnameText.setText(grupModel.getName());
            grupaciklamaText.setText(grupModel.getAciklama());

            if (grupModel.getImage()!=null){
                Picasso.get().load(grupModel.getImage()).into(grupImageView);
            }
        }
    }
}
