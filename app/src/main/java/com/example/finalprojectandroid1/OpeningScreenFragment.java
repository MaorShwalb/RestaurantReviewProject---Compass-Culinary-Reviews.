package com.example.finalprojectandroid1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.finalprojectandroid1.databinding.LoginFragmentBinding;
import com.example.finalprojectandroid1.databinding.OpeningScreenFragmentBinding;

public class OpeningScreenFragment extends Fragment {

    private OpeningScreenFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = OpeningScreenFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        // Button loginbtn = view.findViewById(R.id.loginbtn);


        Animation alphaAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fade);
        binding.openingText.startAnimation(alphaAnim);

        binding.btnOpenScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_openingScreenFragment_to_lobby);
            }
        });

        return binding.getRoot();
    }
}
