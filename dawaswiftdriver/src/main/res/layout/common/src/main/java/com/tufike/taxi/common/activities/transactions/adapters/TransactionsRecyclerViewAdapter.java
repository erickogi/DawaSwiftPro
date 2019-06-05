package com.tufike.taxi.common.activities.transactions.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tufike.taxi.common.databinding.ItemTransactionBinding;
import com.tufike.taxi.common.models.Transaction;

import java.util.List;

public class TransactionsRecyclerViewAdapter extends RecyclerView.Adapter<TransactionsRecyclerViewAdapter.ViewHolder> {
    private List<Transaction> transactions;

    public TransactionsRecyclerViewAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public TransactionsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTransactionBinding itemBinding = ItemTransactionBinding.inflate(layoutInflater, parent, false);
        return new TransactionsRecyclerViewAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(TransactionsRecyclerViewAdapter.ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.bind(transaction);

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTransactionBinding binding;
        ViewHolder(ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(Transaction transaction) {
            binding.setItem(transaction);
            binding.executePendingBindings();
        }
    }
}