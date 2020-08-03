package com.example.instaappandroidprojectprojectbrain.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.instaappandroidprojectprojectbrain.Adapter.HomeIdeaAdapter;
import com.example.instaappandroidprojectprojectbrain.HomeActivity;
import com.example.instaappandroidprojectprojectbrain.MainActivity;
import com.example.instaappandroidprojectprojectbrain.Model.Idea;
import com.example.instaappandroidprojectprojectbrain.Model.Profile1;
import com.example.instaappandroidprojectprojectbrain.R;
import com.example.instaappandroidprojectprojectbrain.SearchIdeaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private TextView txtHomeUsername;
    private EditText edtHomeTitle, edtHomeContext, edtHomeContent;
    private Button btnHomePost, btnSignOut, btnSearch;
    private RecyclerView recyclerHome;
    private HomeIdeaAdapter adapter;
    private Idea newIdea;
    String userID;
    Profile1 profile;
    private ArrayList<Idea> searchedIdeaArrayList;
    private ArrayList<String> followedUsernames;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        newIdea = new Idea();
        followedUsernames = new ArrayList<>();
        db.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){

                    profile = documentSnapshot.toObject(Profile1.class);
                    Log.d("Home Frag", "DocumentSnapshot data: " + profile.getUsername());
                    txtHomeUsername.setText(profile.getUsername());
                    newIdea.setAuthor(profile);
                    Log.d("Home Frag", "DocumentSnapshot data: " + newIdea.getAuthor());

                }else{

                    Log.d("Home Frag", "No Data found! ");

                }
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





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtHomeUsername = view.findViewById(R.id.txtHomeUsername);
        edtHomeTitle = view.findViewById(R.id.edtHomeTitle);
        edtHomeContent = view.findViewById(R.id.edtHomeContent);
        edtHomeContext = view.findViewById(R.id.edtHomeContext);
        searchedIdeaArrayList = new ArrayList<>();
        adapter = new HomeIdeaAdapter(getActivity(), searchedIdeaArrayList, followedUsernames, this);

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchIdeaActivity.class);
                startActivity(i);
            }
        });


        btnHomePost = view.findViewById(R.id.btnHomePost);
        btnHomePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIdea.setTitle(edtHomeTitle.getText().toString());
                newIdea.setContent(edtHomeContent.getText().toString());
                newIdea.setContext(edtHomeContext.getText().toString());


                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("users").document(userID);
                Date currentTime = Calendar.getInstance().getTime();
                Log.d("Home Frag", "Checkkkkkkkkkkkkkkkkkk:  " + newIdea.getTitle());

                db.collection("Ideas").document(currentTime + "").set(newIdea);
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
            }
        });

        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        recyclerHome = view.findViewById(R.id.recyclerHome);
        recyclerHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerHome.setAdapter(adapter);
    }
}