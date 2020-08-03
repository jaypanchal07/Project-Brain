package com.example.instaappandroidprojectprojectbrain.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.instaappandroidprojectprojectbrain.Fragment.HomeFragment;
import com.example.instaappandroidprojectprojectbrain.GotoActivity;
import com.example.instaappandroidprojectprojectbrain.HomeActivity;
import com.example.instaappandroidprojectprojectbrain.Model.Idea;
import com.example.instaappandroidprojectprojectbrain.Model.Profile1;
import com.example.instaappandroidprojectprojectbrain.Model.Todos;
import com.example.instaappandroidprojectprojectbrain.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeIdeaAdapter extends RecyclerView.Adapter<HomeIdeaAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Idea> listOfIdeas;
    static ArrayList<String> usernames;
    static ArrayList<Idea> toDolist;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public HomeIdeaAdapter(Context context, ArrayList<Idea> listOfIdeas, ArrayList<String> followedUsernames, HomeFragment homeFragment) {
        this.context = context;
        this.listOfIdeas = listOfIdeas;
        //this.usernames = followedUsernames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View oneItem = inflater.inflate(R.layout.view_home_row, parent, false);
        return new ViewHolder(oneItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Idea idea = listOfIdeas.get(position);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usernames = new ArrayList<>();
        toDolist = new ArrayList<>();
        Log.d("check IdeaUsername: ", "onBindViewHolder: " + idea.getAuthor().getUsername());

        DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getUid());

//        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Idea temp = documentSnapshot.toObject(Idea.class);
//                if(temp != null){
//                    toDolist.add(temp);
//                }else{
//
//                }
//
//            }
//        });

        holder.btnToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((Activity)context).finish();
                Todos newTodo = new Todos();
                newTodo.setTodoIdea(idea);
                toDolist.add(idea);

                final Map<String,Object> userToDo = new HashMap<>();
                userToDo.put("ToDo", toDolist);

                db.collection("users").document(mAuth.getCurrentUser().getUid()).set(userToDo, SetOptions.merge());

                Intent i = new Intent(context, GotoActivity.class);
                context.startActivity(i);
            }
        });

        //DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Profile1 temp = documentSnapshot.toObject(Profile1.class);
                if(temp.getFollowing() != null){
                    usernames = temp.getFollowing();
                    Log.d("Homw Frgbwejf", "onSuccess: " + usernames);
                    Log.d("Homw Frgbwejf", "onSuccess: "+ idea.getAuthor().getUsername());

                    for(String username : usernames){
                        Log.d("check username: ", "onBindViewHolder: " + username);
                        if(idea.getAuthor().getUsername().equals(username)){
                            holder.ideaTitleTV.setText(idea.getTitle());
                            holder.ideaContextTV.setText(idea.getContext());
                            holder.ideaContentTV.setText(idea.getContent());
                            holder.ideaAuthorTV.setText("Author: " + idea.getAuthor().getUsername());
                        }
                        //notifyDataSetChanged();
                    }

                }else{

                }

            }
        });



//        holder.followUserBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listOfIdeas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ideaTitleTV, ideaContextTV, ideaContentTV, ideaAuthorTV;
        private Button btnToDo;
        private Button btnCiting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ideaTitleTV = itemView.findViewById(R.id.ideaTitleTV);
            this.ideaContentTV = itemView.findViewById(R.id.ideaContentTV);
            this.ideaAuthorTV = itemView.findViewById(R.id.ideaAuthorTV);
            this.ideaContextTV = itemView.findViewById(R.id.ideaContextTV);
            this.btnToDo = itemView.findViewById(R.id.btnToDo);
            this.btnCiting = itemView.findViewById(R.id.btnCiting);
        }
    }
}
