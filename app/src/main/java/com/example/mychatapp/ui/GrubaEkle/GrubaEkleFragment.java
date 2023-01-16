package com.example.mychatapp.ui.GrubaEkle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mychatapp.GrupModel;
import com.example.mychatapp.R;
import com.example.mychatapp.ui.GrupOlustur.GroupAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class GrubaEkleFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    RecyclerView grubaekle_uye;
    RecyclerView grubaekle_rehber;
    EditText seciligrup;

    GrupModel grupModel;
    ArrayList<GrupModel> grupModelArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gruba_ekle, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        grubaekle_uye=view.findViewById(R.id.grubaekle_grup);
        grubaekle_rehber=view.findViewById(R.id.grubaekle_rehber);
        seciligrup=view.findViewById(R.id.grubaekle_seciligrup);

        grupModelArrayList=new ArrayList<>();


        //FetchGroups();


        return view;
    }

    private void FetchGroups() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("/userdata/" + userId +  "/groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            grupModelArrayList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                GrupModel grupModel = new GrupModel(documentSnapshot.getString("name"), documentSnapshot.getString("description"),
                        documentSnapshot.getString("image"), (List<String>) documentSnapshot.get("numbers"), documentSnapshot.getId());

                grupModelArrayList.add(grupModel);
            }
              grubaekle_uye.setAdapter(new GroupAddAdapter(grupModelArrayList));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            grubaekle_uye.setLayoutManager(linearLayoutManager);



        });

    }



}