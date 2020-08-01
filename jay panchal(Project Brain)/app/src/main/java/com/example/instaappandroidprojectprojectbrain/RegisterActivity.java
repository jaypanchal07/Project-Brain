package com.example.instaappandroidprojectprojectbrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instaappandroidprojectprojectbrain.Forms.RegisterForm1;
import com.example.instaappandroidprojectprojectbrain.Model.Profile1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerEmailET, registerUsernameET, registerPwdET, registerFirstNameET;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    String userID;
    private static final String TAG = RegisterActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmailET = findViewById(R.id.registerEmailET);
        registerUsernameET = findViewById(R.id.registerUsernameET);
        registerPwdET = findViewById(R.id.registerPwdET);
        registerFirstNameET = findViewById(R.id.registerFirstNameET);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();




        setupButton();
    }
    public void setupButton() {
        Button signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerEmailET.getText().toString().equals("")    ||
                        registerUsernameET.getText().toString().equals("") ||
                        registerPwdET.getText().toString().equals("")
                )
                {
                    Toast.makeText(v.getContext(), "Somewhere is empty, please enter it.", Toast.LENGTH_SHORT).show();
                }
                else {
                    final Profile1 registerForm = new Profile1();
                    String password = registerPwdET.getText().toString();
                    registerForm.setEmail(registerEmailET.getText().toString());
                    registerForm.setUsername(registerUsernameET.getText().toString());
                    registerForm.setName(registerFirstNameET.getText().toString());



                    mAuth.createUserWithEmailAndPassword(registerForm.getEmail(), password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    userID = mAuth.getCurrentUser().getUid();
                                    Toast.makeText(RegisterActivity.this, "Successfully added, press Sign In button to sign in!" + userID,Toast.LENGTH_SHORT).show();

                                    DocumentReference documentReference = fStore.collection("users").document(userID);

                                    //final Map<String,Object> profile = new HashMap<>();

                                    final Map<String,Object> user = new HashMap<>();
                                    user.put("username", registerForm.getUsername());
                                    user.put("Name", registerForm.getName());
                                    user.put("email", registerForm.getEmail());
                                    user.put("Following", registerForm.getFollowing());

                                    //profile.put("profile", user);


                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "OnSuccess: Profile i created!" + userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });;


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Could not sign up failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                        }
                    });

                }
            }
        });

        Button signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}