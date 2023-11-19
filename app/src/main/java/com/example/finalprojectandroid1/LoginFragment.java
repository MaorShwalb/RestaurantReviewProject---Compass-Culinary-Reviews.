package com.example.finalprojectandroid1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.finalprojectandroid1.databinding.LoginFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;

    private LoginFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = LoginFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        progressBar = binding.progressBar;

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin(view);
            }
        });

        binding.btnAskRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_requestRegistration);
            }
        });

        //action_loginFragment_to_requestRegistration
/*        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobby);
            }
        });*/


//------------------------------------------------------------------------------------

/*        binding.btnRegister.setVisibility(View.GONE);
        //binding.textViewOr.setVisibility(View.GONE);
        String string = null;

        if (mAuth.getCurrentUser() != null) {

            binding.btnLogin.setVisibility(View.GONE);

            string = mAuth.getCurrentUser().getEmail();
            if (string.equals("admin@gmail.com")) {
                binding.btnRegister.setVisibility(View.VISIBLE);
                binding.textViewOr.setVisibility(View.VISIBLE);
                //Log.d(mAuth.getCurrentUser().getEmail(), "user name");   //must be in if != null. cuz if = null its crush
            }
        }else{
            binding.btnLogin.setVisibility(View.VISIBLE);
        }*/
//------------------------------------------------------------------------------------


/*        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });*/

        return binding.getRoot();
    }


    private void userLogin(View view) {

        String username = binding.username.getText().toString().trim();
        String password = binding.password.getText().toString().trim();


        if (username.isEmpty()){
            binding.username.setError("Please enter a valid username!");
            binding.username.requestFocus();
            return;
        }

        if (password.isEmpty()){
            binding.password.setError("Password is required");
            binding.password.requestFocus();
            return;
        }

        Log.d(username, "usename");
        Log.d(password, "password");

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_lobby);
                    Toast.makeText(getActivity(),"Login successfully",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getActivity(),"Login Failed",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }



}
