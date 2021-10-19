package com.learn.booklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.learn.booklistapp.databinding.ActivityUserPreferenceBookBinding;

public class UserPreferenceBook extends AppCompatActivity {


    ActivityUserPreferenceBookBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPreferenceBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String interestsText = binding.editText.getText().toString();

                firebaseDatabase.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("interests").setValue(interestsText);

                Intent i = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(i);

            }
        });


    }
}