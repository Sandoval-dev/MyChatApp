package com.example.mychatapp.ui.MesajOlustur;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mychatapp.MesajModel;
import com.example.mychatapp.R;
import com.example.mychatapp.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class MesajOlusturFragment extends Fragment {

    EditText mesaj_isim,mesaj_aciklama;
    Button mesajolustur;
    RecyclerView tummesajlar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<MesajModel> mesajModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mesaj_olustur, container, false);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        mesajModelList=new ArrayList<>();

        mesaj_isim=view.findViewById(R.id.mesajolustur_mesajadi);
        mesaj_aciklama=view.findViewById(R.id.mesajolustur_mesaj);
        mesajolustur=view.findViewById(R.id.btn_mesajolustur);
        tummesajlar=view.findViewById(R.id.mesajolustur_tummesaj);




        mesajolustur.setOnClickListener(v -> {
            String messageName = mesaj_isim.getText().toString();
            String messageDescription = mesaj_aciklama.getText().toString();

            if (messageName.isEmpty() || messageDescription.isEmpty()) {
                Tools.showMessage("Lütfen tüm alanları doldurun.");
                return;

            }
            CreateMessage(messageName, messageDescription);


        });

        FetchMessage();
        return view;
    }

    private void CreateMessage(String messageName, String messageDescription) {
        String userId = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore.collection("/userdata/" + userId +  "/messages").add(new HashMap<String, String>(){{
                    put("name", messageName);
                    put("description", messageDescription);
                }})
                .addOnSuccessListener(documentReference -> {
                    Tools.showMessage("Mesaj başarıyla oluşturuldu");


                    documentReference.get().addOnSuccessListener(documentSnapshot -> {
                        MesajModel messageModel = new MesajModel(messageName, messageDescription, documentSnapshot.getId());
                        //mesajModelList.add(messageModel);
                        tummesajlar.getAdapter().notifyItemInserted(mesajModelList.size() - 1);
                    });

                })
                .addOnFailureListener(e -> {
                    Tools.showMessage("Mesaj oluşturulamadı");
                });
    }

    private void FetchMessage()
    {
        String userId=firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("/userdata/" + userId + "/messages").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    mesajModelList.clear();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                        MesajModel mesajModel=new MesajModel(documentSnapshot.getString("name"),documentSnapshot.getString("description"),documentSnapshot.getId());
                        //mesajModelList.add(mesajModel);
                    }

                    tummesajlar.setAdapter(new MessageAdapter(mesajModelList));
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    tummesajlar.setLayoutManager(linearLayoutManager);
                })
                .addOnFailureListener(e ->{
                    Tools.showMessage("Mesajlar alınırken bir hata oluştu.");
                });
    }

}