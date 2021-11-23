package com.imeja.bmi.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.imeja.bmi.Controller;
import com.imeja.bmi.auth.LoginActivity;
import com.imeja.bmi.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private String name, email, joined;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        name = Controller.getName();
        email = Controller.getEmail();
        joined = Controller.getJoined();
        setData(name, email, joined);
        binding.btnSubmit.setOnClickListener(v -> {
            Controller.setIsloggedIn(false);
            requireActivity().finishAffinity();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
        return root;
    }

    private void setData(String name, String email, String joined) {
        binding.edtName.setText(name);
        binding.edtEmail.setText(email);
        binding.edtJoined.setText(joined);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}