package com.example.mychatapp.ui.GrupOlustur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mychatapp.GrupModel;
import com.example.mychatapp.R;
import com.example.mychatapp.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class GrupOlusturFragment extends Fragment {
    EditText grupolustur_grupadi,grupolustur_aciklama;
    ImageView grupSimge;
    Button grupolustur;
    RecyclerView grupolustur_tumgruplar;

    Uri filePath;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    FirebaseStorage firebaseStorage;
    ArrayList<GrupModel> gruparray;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_grup_olustur, container, false);

        grupolustur_grupadi=view.findViewById(R.id.grupolustur_grupadi);
        grupolustur_aciklama=view.findViewById(R.id.grupolustur_aciklama);
        grupSimge=view.findViewById(R.id.grupolustur_simge);
        grupolustur=view.findViewById(R.id.btn_grupolustur);
        grupolustur_tumgruplar=view.findViewById(R.id.grupolustur_tumgrup);


        firebaseAuth=FirebaseAuth.getInstance();
        fireStore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

        gruparray=new ArrayList<>();



        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode()==getActivity().RESULT_OK){
                        Intent data=result.getData();
                        filePath=data.getData();
                        grupSimge.setImageURI(filePath);

                    }
        });


        grupSimge.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });

        grupolustur.setOnClickListener(v -> {
            String grupName=grupolustur_grupadi.getText().toString();
            String grupAciklama=grupolustur_aciklama.getText().toString();

            if (grupName.isEmpty()){
                Tools.showMessage("Grup ismi gerekli");
                return;
            }
            if (grupAciklama.isEmpty()){
                Tools.showMessage("Grup açıklaması gerekli");
                return;
            }

            if (filePath!=null){
                StorageReference storageReference=firebaseStorage.getReference().child("images/" + UUID.randomUUID().toString());

                storageReference.putFile(filePath).addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri ->{
                        String downloadUrl=uri.toString();

                        CreateGroup(grupName,grupAciklama,downloadUrl);
                    });
                });
                return;
            }
            else{
                CreateGroup(grupName,grupAciklama,null);
            }


        });


        FetchGroups();
        return view;
    }

    private void CreateGroup(String name,String aciklama,String imageUrl){
        String userId=firebaseAuth.getCurrentUser().getUid();

        fireStore.collection("/userdata/" + userId + "/" + "groups").add(new HashMap<String, Object>(){{
            put("name",name);
            put("aciklama",aciklama);
            put("image",imageUrl);
            put("numbers",new ArrayList<String>());

        }}).addOnSuccessListener(documentReference -> {
            Tools.showMessage("Grup başarıyla oluşturuldu.");

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                GrupModel groupModel = new GrupModel( name, aciklama, imageUrl, (List<String>)documentSnapshot.get("numbers"), documentSnapshot.getId());
                gruparray.add(groupModel);
                grupolustur_tumgruplar.getAdapter().notifyItemInserted(gruparray.size() - 1);
            });
        }).addOnFailureListener(e ->{
            Tools.showMessage("Grup oluşturulamadı.");
        });
    }

    private void FetchGroups(){
        String userId=firebaseAuth.getCurrentUser().getUid();
        fireStore.collection("/userdata/" + userId + "/" + "groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            gruparray.clear();
            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                GrupModel grupModel=new GrupModel(documentSnapshot.getString("name"),documentSnapshot.getString("aciklama"),
                        documentSnapshot.getString("image"),(List<String>)documentSnapshot.get("numbers"),documentSnapshot.getId());

            }

            grupolustur_tumgruplar.setAdapter(new GroupAdapter(gruparray));
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            grupolustur_tumgruplar.setLayoutManager(linearLayoutManager);


        });
    }


}