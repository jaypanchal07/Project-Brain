package com.example.instaappandroidprojectprojectbrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.instaappandroidprojectprojectbrain.Adapter.ToDoAdapter;
import com.example.instaappandroidprojectprojectbrain.Model.Idea;
import com.example.instaappandroidprojectprojectbrain.Model.Todos;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GotoActivity extends AppCompatActivity {

    private ToDoAdapter adapter;
    private RecyclerView recyclerToDo;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ArrayList<Idea> todolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goto);

        setup();

    }
    public void setup(){

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        todolist = new ArrayList<>();

        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    todolist = (ArrayList<Idea>) documentSnapshot.get("ToDo") ;
                    Log.d("Tin takle ", "onSuccess: " + todolist);

                    documentSnapshot.get("ToDo");
                    // adapter.notifyDataSetChanged();
                    adapter = new ToDoAdapter(GotoActivity.this, todolist);


                    recyclerToDo = findViewById(R.id.recyclerToDo);
                    recyclerToDo.setLayoutManager(new LinearLayoutManager(GotoActivity.this));
                    recyclerToDo.setAdapter(adapter);
                }
            }
        });

    }
}