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

import com.example.instaappandroidprojectprojectbrain.Forms.LoginForm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText loginEmailET, loginPwdET;
    private FirebaseAuth mAuth;
    private static final String TAG = RegisterActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmailET = findViewById(R.id.loginEmailET);
        loginPwdET = findViewById(R.id.loginPwdET);

        mAuth = FirebaseAuth.getInstance();
        setupButton();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() != null){
            Log.d(TAG, "username: " + mAuth.getCurrentUser().getUid());
            finish();
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
    }
    public void setupButton() {
        final Button loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginEmailET.getText().toString().equals("") ||
                        loginPwdET.getText().toString().equals("")
                )
                {
                    Toast.makeText(v.getContext(), "Your email or password is empty, please enter it.", Toast.LENGTH_SHORT).show();
                }
                else {
                    LoginForm loginForm = new LoginForm();
                    loginForm.setEmail(loginEmailET.getText().toString());
                    loginForm.setPassword(loginPwdET.getText().toString());
                    mAuth.signInWithEmailAndPassword(loginForm.getEmail(),loginForm.getPassword()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(MainActivity.this, "Signed In Succefully!",Toast.LENGTH_SHORT).show();

                                finish();

                                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                i.putExtra("username", mAuth.getCurrentUser().getUid());
                                startActivity(i);


                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}