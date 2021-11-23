package com.imeja.bmi.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.imeja.bmi.adapters.VisitsAdapter;
import com.imeja.bmi.databinding.FragmentHomeBinding;
import com.imeja.bmi.models.Visits;
import com.imeja.bmi.views.VitalActivity;
import com.imeja.bmi.views.visits.AVisitActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private List<Visits> visitsList;
    private VisitsAdapter adapter;
    private Calendar mCalendar;
    private String mFormat, selectedDate;
    private SimpleDateFormat mSdf;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        visitsList = new ArrayList<>();
        mFormat = "yyyy-MM-dd";
        mSdf = new SimpleDateFormat(mFormat, Locale.US);
        mCalendar = Calendar.getInstance();
        String currentDate = mSdf.format(new Date());
        dateSetListener = (view1, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();

        };
        binding.edtSelected.setText(currentDate);
        disableTextInputEditText(binding.edtSelected);

        binding.fab.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), VitalActivity.class));
        });

        binding.edtSelected.setOnClickListener(v -> new DatePickerDialog(getActivity(),
                dateSetListener,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show());

        binding.btnSearch.setOnClickListener(v -> {
            checkInput();
        });
        try {
            adapter = new VisitsAdapter(getActivity(), visitsList);
            binding.visitsRecyclerview.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
        loadData(currentDate);
        return root;
    }

    private void checkInput() {
        selectedDate = binding.edtSelected.getText().toString();
        if (selectedDate.isEmpty()) {
            binding.edtSelected.setError("Enter Date");
            binding.edtSelected.requestFocus();
            return;
        }
        loadData(selectedDate);
    }

    private void disableTextInputEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

    private void updateDate() {
        binding.edtSelected.setText(mSdf.format(mCalendar.getTime()));

    }

    private void loadData(String currentDate) {
        try {
            visitsList.clear();
            adapter.notifyDataSetChanged();
            homeViewModel.loadVisits(getActivity(), binding, currentDate);
            homeViewModel.liveData.observe(getViewLifecycleOwner(), visit -> {
                if (visit != null) {
                    if (visit.visits != null) {
                        try {
                            if (visit.visits.toString().equalsIgnoreCase("[]")){

                            }else {
                                visitsList = visit.visits;
                                try {
                                    adapter = new VisitsAdapter(getActivity(), visitsList);
                                    binding.visitsRecyclerview.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}