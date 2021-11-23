package com.imeja.bmi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imeja.bmi.databinding.ListingItemBinding;
import com.imeja.bmi.holders.VisitsHolder;
import com.imeja.bmi.models.Patients;
import com.imeja.bmi.models.Visits;

import java.util.List;

public class PatientsAdapter  extends RecyclerView.Adapter<VisitsHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Patients> visitsList;
    private ListingItemBinding binding;
    private Patients visit;

    public PatientsAdapter(Context context, List<Patients> visitsList) {
        this.context = context;
        this.visitsList = visitsList;
    }


    @NonNull
    @Override
    public VisitsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        binding = ListingItemBinding.inflate(layoutInflater, parent, false);
        return new VisitsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitsHolder holder, int position) {
        try {
            visit = visitsList.get(position);

            holder.binding.tvName.setText(visit.firstname);
            holder.binding.tvAge.setText(visit.dob);
            holder.binding.tvStatus.setText(visit.gender);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (visitsList != null) {
            size = visitsList.size();
        }
        return size;
    }

}
