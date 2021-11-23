package com.imeja.bmi.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.imeja.bmi.adapters.VisitsAdapter;
import com.imeja.bmi.databinding.FragmentHomeBinding;
import com.imeja.bmi.models.Visits;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private List<Visits> visitsList;
    private VisitsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        loadData();

        return root;
    }

    private void loadData() {
        homeViewModel.loadVisits(getActivity(), binding);
        homeViewModel.liveData.observe(getViewLifecycleOwner(), visit -> {
            if (visit != null) {
                if (visit.visits != null) {
                    try {
                        visitsList = visit.visits;
                        try {
                            adapter = new VisitsAdapter(getActivity(), visitsList);
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