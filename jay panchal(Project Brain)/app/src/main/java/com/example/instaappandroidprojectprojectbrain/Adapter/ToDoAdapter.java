package com.example.instaappandroidprojectprojectbrain.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instaappandroidprojectprojectbrain.Fragment.HomeFragment;
import com.example.instaappandroidprojectprojectbrain.Model.Idea;
import com.example.instaappandroidprojectprojectbrain.Model.Todos;
import com.example.instaappandroidprojectprojectbrain.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private Context context;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ArrayList<Idea> todolist;

    public ToDoAdapter(Context context, ArrayList<Idea> todolist) {
        this.context = context;
        this.todolist = todolist;
        Log.d("o;fnoefnboaen;fb", "ToDoAdapter: " + todolist);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View oneItem = inflater.inflate(R.layout.view_todo_row, parent, false);
        return new ToDoAdapter.ViewHolder(oneItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Idea todo1 = todolist.get(position);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

//        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
                    //todolist = (ArrayList<Todos>) documentSnapshot.get("ToDo") ;

                    holder.title.setText(todo1.getTitle());
                    holder.context.setText(todo1.getContext());
                    holder.content.setText(todo1.getContent());
                    holder.author.setText(todo1.getAuthor().getUsername());
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
//        Log.d("Sizeeeeeeeeeee", "getItemCount: "+ todolist.size());

        return todolist.size();

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, context, content, author;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                title = itemView.findViewById(R.id.ideaTitleToDo);
                context = itemView.findViewById(R.id.ideaContextToDo);
                content = itemView.findViewById(R.id.ideaContentToDo);
                author = itemView.findViewById(R.id.ideaAuthorToDo);
        }
    }
}
