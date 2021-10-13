package com.learn.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.learn.Models.Users;
import com.learn.booklistapp.MainActivity;
import com.learn.booklistapp.R;
import com.learn.booklistapp.databinding.FragmentSignUpBinding;


public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }


    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
    FragmentSignUpBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignUpBinding.inflate(getLayoutInflater());

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are Creating Account");


        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(binding.emailIdSignUp.getText().toString())||TextUtils.isEmpty(binding.passwordSignUp.getText().toString())
                        ||TextUtils.isEmpty(binding.userName.getText().toString())){
                    Toast.makeText(getContext(),"Please fill all requirements",Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();

                    auth.createUserWithEmailAndPassword(binding.emailIdSignUp.getText().toString(), binding.passwordSignUp.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Users newUser = new Users(binding.userName.getText().toString(), binding.emailIdSignUp.getText().toString(), binding.passwordSignUp.getText().toString());
                                        newUser.setInterests("Empty");
                                        String id = task.getResult().getUser().getUid();

                                        //setting the value in Users node --> to this id(specific node in users) and its value is this
                                        firebaseDatabase.getReference().child("Users").child(id).setValue(newUser);
                                        Toast.makeText(getContext(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }

            }
        });

        return binding.getRoot();

    }



}
