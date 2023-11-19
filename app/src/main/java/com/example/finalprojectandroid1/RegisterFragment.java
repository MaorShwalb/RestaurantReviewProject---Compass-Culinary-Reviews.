package com.example.finalprojectandroid1;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.finalprojectandroid1.databinding.OpeningScreenFragmentBinding;
import com.example.finalprojectandroid1.databinding.RegisterFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;

    private RegisterFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        binding = RegisterFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        progressBar = binding.progressBar;

        binding.btnCreateRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(view);
            }
        });

        return binding.getRoot();
    }

    private void registerUser(View view) {
        String username = binding.username.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        String repassword = binding.repassword.getText().toString();
        String age = binding.age.getText().toString();
        String location = binding.location.getText().toString();

        if (username.isEmpty()){
            binding.username.setError("Full name is required!");
            binding.username.requestFocus();
            return;
        }

        if (username.isEmpty()){
            binding.email.setError("Email is required!");
            binding.email.requestFocus();
            return;
        }

        if (username.isEmpty()){
            binding.password.setError("Password is required!");
            binding.password.requestFocus();
            return;
        }

        if (username.isEmpty()){
            binding.repassword.setError("Re-password is required!");
            binding.repassword.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.setError("Please provide valid email!");
            binding.email.requestFocus();
            return;
        }

        if (password.length() < 8){
            binding.password.setError("Min password length should be 8 characters!");
            binding.password.requestFocus();
            return;
        }

        if (!password.equals(repassword)){
            binding.repassword.setError("password and Re-password not match!");
            binding.repassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    User user = new User(username, email, age, location);

       //             FirebaseDatabase.getInstance().getReference().child("Users")

/*                    FirebaseDatabase.getInstance().getReferenceFromUrl("https://android1project-a6b52-default-rtdb.firebaseio.com")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {*/

                    FirebaseDatabase.getInstance().getReference().child("Users").child(username)
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_lobby);
                                    }
                                    else{
                                        Toast.makeText(getActivity(),"Failed to register,try again later.",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(getActivity(),"Failed to register,try again later.",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

}
