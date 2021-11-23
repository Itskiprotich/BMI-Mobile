package com.imeja.bmi.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.imeja.bmi.Controller;
import com.imeja.bmi.R;
import com.imeja.bmi.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private String name, email, joined;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        name = Controller.getName();
        email = Controller.getEmail();
        joined = Controller.getJoined();
        setData(name, email, joined);

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