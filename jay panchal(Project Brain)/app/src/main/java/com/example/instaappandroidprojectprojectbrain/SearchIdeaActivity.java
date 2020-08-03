package com.example.instaappandroidprojectprojectbrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.instaappandroidprojectprojectbrain.Adapter.SearchIdeaAdapter;
import com.example.instaappandroidprojectprojectbrain.Adapter.ToDoAdapter;
import com.example.instaappandroidprojectprojectbrain.Model.Idea;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchIdeaActivity extends AppCompatActivity {

    private SearchIdeaAdapter adapter;
    private RecyclerView recyclerSearchIdea;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ArrayList<Idea> searchedIdeaArrayList;
    private EditText edtSearchIdea;
    private Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_idea);
        setup();

    }
    public void setup(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        searchedIdeaArrayList = new ArrayList<>();
        edtSearchIdea = findViewById(R.id.edtSearchIdea);
        btnSearch = findViewById(R.id.btnSearch);
        adapter = new SearchIdeaAdapter(this, searchedIdeaArrayList);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Ideas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Idea documentIdea = documentSnapshot.toObject(Idea.class);
                            if(documentIdea.getTitle().equals(edtSearchIdea.getText().toString())){
                                searchedIdeaArrayList.clear();
                                searchedIdeaArrayList.add(documentIdea);
                                Log.d("Home Frag : ", "onSuccess1: " + searchedIdeaArrayList.get(0).getTitle());
                            }else{
                                edtSearchIdea.setText("Not Found");
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        db.collection("Ideas").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Idea documentIdea = documentSnapshot.toObject(Idea.class);

                        searchedIdeaArrayList.add(documentIdea);
                        Log.d("Home Frag : ", "onSuccess1: " + searchedIdeaArrayList.get(0).getTitle());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        recyclerSearchIdea = findViewById(R.id.recyclerSearchIdea);
        recyclerSearchIdea.setLayoutManager(new LinearLayoutManager(SearchIdeaActivity.this));
        recyclerSearchIdea.setAdapter(adapter);
    }

}