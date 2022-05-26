package com.firebase.ontapckandoid;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        TextView email  = findViewById(R.id.editTextTextPersonName2);
        TextView pass = findViewById(R.id.editTextTextPassword2);
        TextView pass2 = findViewById(R.id.editTextTextPassword3);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pass.getText().toString().equalsIgnoreCase(pass2.getText().toString())){
                    createAccount(email.getText().toString(),pass.getText().toString());
                }else{
                    Toast.makeText(Register.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.textView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void createAccount(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i  = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(i);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        if(user != null){
            Toast.makeText(Register.this, "createUserWithEmail:success",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(Register.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

}