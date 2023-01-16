package com.example.mychatapp.ui.MesajOlustur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatapp.MesajModel;
import com.example.mychatapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    List<MesajModel> mesajModelList;

    public MessageAdapter(List<MesajModel> mesajModelList){
        this.mesajModelList=mesajModelList;
    }


    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mesajolustur_message,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        MesajModel mesajModel=mesajModelList.get(position);
        holder.setData(mesajModel);
    }

    @Override
    public int getItemCount() {

        return mesajModelList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView messageText,messageDesp;
        public MessageViewHolder(View itemview){
            super(itemview);

            messageText=itemview.findViewById(R.id.mesajolustur_mesajadi);
            messageDesp=itemview.findViewById(R.id.mesajolustur_mesaj);
        }

        public void setData(MesajModel mesajModel) {
            messageText.setText(mesajModel.getMesajName());
            messageDesp.setText(mesajModel.getIcerik());
        }
    }
}
