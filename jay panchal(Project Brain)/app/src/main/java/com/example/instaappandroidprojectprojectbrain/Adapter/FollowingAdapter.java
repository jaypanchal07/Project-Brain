package com.example.instaappandroidprojectprojectbrain.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instaappandroidprojectprojectbrain.Fragment.FollowingFragment;
import com.example.instaappandroidprojectprojectbrain.Fragment.HomeFragment;
import com.example.instaappandroidprojectprojectbrain.Model.Idea;
import com.example.instaappandroidprojectprojectbrain.Model.Profile1;
import com.example.instaappandroidprojectprojectbrain.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.firestore.SetOptions.merge;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Profile1> listOfUsers;
    Profile1 profile;
    static ArrayList<String> usernames;
    ArrayList<String> checkUsers;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public FollowingAdapter(Context context, ArrayList<Profile1> listOfUsers, FollowingFragment followingFragment) {
        this.context = context;
        this.listOfUsers = listOfUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View oneItem = inflater.inflate(R.layout.view_following_row, parent, false);

        return new ViewHolder(oneItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Profile1 user = listOfUsers.get(position);
        Log.d("Following Adapter: ", "onBindViewHolder: " + listOfUsers);
        holder.ideaAuthorTV.setText("Author: " + user.getUsername());
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usernames = new ArrayList<>();

        checkUsers = new ArrayList<>();

        DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Profile1 temp = documentSnapshot.toObject(Profile1.class);
                if(temp.getFollowing() != null){
                    usernames = temp.getFollowing();
                }else{

                }

            }
        });

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    checkUsers = (ArrayList<String>) documentSnapshot.get("Following") ;
                    Log.d("Folloowing Adapter: ", "onSuccess: Check Users: " + checkUsers);
                    if(checkUsers != null){
                        for(String u : checkUsers){
                            if (u.equals(user.getUsername())){
                                holder.followUserBtn.setText("Following");
                            }
                        }
                    }else{

                    }

                }else{
                    Log.d("Following Frag ", "No Data found! ");
                }
            }
        });

        holder.followUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getUid());
                usernames.add(listOfUsers.get(holder.getAdapterPosition()).getUsername());
                Log.d("check user", "onClick: " + usernames);
                if (holder.followUserBtn.getText().toString().equals("Follow")){

                    holder.followUserBtn.setText("Following");

                    final Map<String,Object> user = new HashMap<>();
                    user.put("Following", usernames);

                    documentReference.set(user, merge());

                }else if (holder.followUserBtn.getText().toString().equals("Following")){

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ideaAuthorTV;
        Button followUserBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ideaAuthorTV = itemView.findViewById(R.id.ideaAuthorTV1);
            this.followUserBtn = itemView.findViewById(R.id.followUserBtn1);
        }
    }
}
