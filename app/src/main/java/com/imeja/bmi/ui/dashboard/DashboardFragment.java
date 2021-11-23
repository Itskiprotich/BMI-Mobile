package com.imeja.bmi.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.imeja.bmi.R;
import com.imeja.bmi.adapters.PatientsAdapter;
import com.imeja.bmi.adapters.VisitsAdapter;
import com.imeja.bmi.databinding.FragmentDashboardBinding;
import com.imeja.bmi.models.Patients;
import com.imeja.bmi.models.Visits;
import com.imeja.bmi.views.AddPatientActivity;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;
    private FragmentDashboardBinding binding;
    private List<Patients> visitsList;
    private PatientsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        loadPatients();

        binding.fab.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddPatientActivity.class));
        });
        return root;
    }

    private void loadPatients() {
        viewModel.loadPatients(getActivity(), binding);
        viewModel.liveData.observe(getViewLifecycleOwner(), visit -> {
            if (visit != null) {
                if (visit.visits != null) {
                    try {
                        visitsList = visit.visits;
                        try {
                            adapter = new PatientsAdapter(getActivity(), visitsList);
                            binding.visitsRecyclerview.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}