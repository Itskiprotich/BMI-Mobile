package com.imeja.bmi.holders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imeja.bmi.databinding.ListingItemBinding;


public class VisitsHolder  extends RecyclerView.ViewHolder {

    public ListingItemBinding binding;

    public VisitsHolder(@NonNull ListingItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
