package com.example.instaappandroidprojectprojectbrain.Adapter;

import android.annotation.SuppressLint;
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

public class SearchIdeaAdapter extends RecyclerView.Adapter<SearchIdeaAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Idea> listOfIdeas;
    static ArrayList<String> usernames;
    static ArrayList<Idea> toDolist;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public SearchIdeaAdapter(Context context, ArrayList<Idea> listOfIdeas) {
        this.context = context;
        this.listOfIdeas = listOfIdeas;
        //this.usernames = followedUsernames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View oneItem = inflater.inflate(R.layout.view_search_idea, parent, false);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ideaTitleTV = itemView.findViewById(R.id.ideaTitleTV);
            this.ideaContentTV = itemView.findViewById(R.id.ideaContentTV);
            this.ideaAuthorTV = itemView.findViewById(R.id.ideaAuthorTV);
            this.ideaContextTV = itemView.findViewById(R.id.ideaContextTV);
        }
    }
}
