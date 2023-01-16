package com.example.mychatapp.ui.MesajGonder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mychatapp.GrupModel;
import com.example.mychatapp.MesajModel;
import com.example.mychatapp.R;
import com.example.mychatapp.ui.GrupOlustur.GroupAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class MesajGonderFragment extends Fragment {
    RecyclerView grupRecyle,mesajRecyle;
    TextView selectedgrup,selectedmesaj;
    Button sendbutton;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    ArrayList<GrupModel> grupModelArrayList;
    ArrayList<MesajModel> mesajModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_mesaj_gonder, container, false);

        grupRecyle=view.findViewById(R.id.mesajgonder_tumgrup);
        mesajRecyle=view.findViewById(R.id.mesajgonder_grup);

        selectedgrup=view.findViewById(R.id.mesajgonder_grubaekle);
        selectedmesaj=view.findViewById(R.id.mesajgonder_secilimesaj);

        sendbutton=view.findViewById(R.id.mesajgonder_button);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        //FetchGroups();



        return view;
    }

    private void FetchGroups() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("/userdata/" + userId +  "/groups").get().addOnCompleteListener(task -> {
           if(task.isSuccessful()) {
               grupModelArrayList.clear();
               for (DocumentSnapshot document : task.getResult()) {
                   GrupModel grupModel = new GrupModel(document.getString("name"), document.getString("description"),
                           document.getString("image"), (List<String>) document.get("numbers"), document.getId());

                   grupModelArrayList.add(grupModel);
               }
               grupRecyle.setAdapter(new GroupAdapter(grupModelArrayList));
               LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
               grupRecyle.setLayoutManager(linearLayoutManager);

           }
        });

    }


}