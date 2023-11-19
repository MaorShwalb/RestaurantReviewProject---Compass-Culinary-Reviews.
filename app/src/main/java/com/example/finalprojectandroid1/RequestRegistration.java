package com.example.finalprojectandroid1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.finalprojectandroid1.databinding.RequestRegistrationBinding;

public class RequestRegistration extends Fragment {

    private RequestRegistrationBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = RequestRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","admin@email.com", null));
                //intent.putExtra(Intent.EXTRA_SUBJECT, "New user registration request");
                //intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));

            }
        });



        return binding.getRoot();

    }
}
