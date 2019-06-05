package com.tufike.taxi.common.activities.transactions;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tufike.taxi.common.R;
import com.tufike.taxi.common.activities.transactions.adapters.TransactionsRecyclerViewAdapter;
import com.tufike.taxi.common.components.BaseActivity;
import com.tufike.taxi.common.databinding.ActivityTransactionsBinding;
import com.tufike.taxi.common.events.GetTransactionsRequestEvent;
import com.tufike.taxi.common.events.GetTransactionsResultEvent;
import com.tylersuehr.esr.ContentItemLoadingStateFactory;
import com.tylersuehr.esr.EmptyStateRecyclerView;
import com.tylersuehr.esr.ImageTextStateDisplay;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TransactionsActivity extends BaseActivity {
    ActivityTransactionsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(TransactionsActivity.this, R.layout.activity_transactions);
        initializeToolbar("Transactions");
        binding.recyclerView.setStateDisplay(EmptyStateRecyclerView.STATE_LOADING, ContentItemLoadingStateFactory.newListLoadingState(this));
        binding.recyclerView.setStateDisplay(EmptyStateRecyclerView.STATE_EMPTY, new ImageTextStateDisplay(this, R.drawable.empty_state, "Oops!", "Nothing to show here :("));
        binding.recyclerView.setStateDisplay(EmptyStateRecyclerView.STATE_ERROR, new ImageTextStateDisplay(this, R.drawable.empty_state, "SORRY...!", "Something went wrong :("));
        binding.recyclerView.invokeState(EmptyStateRecyclerView.STATE_LOADING);
        eventBus.post(new GetTransactionsRequestEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnTransactionsResultReceived(GetTransactionsResultEvent event) {
        if(event.hasError()){
            binding.recyclerView.invokeState(EmptyStateRecyclerView.STATE_ERROR);
            return;
        }
        if(event.transactions.size() == 0) {
            binding.recyclerView.invokeState(EmptyStateRecyclerView.STATE_EMPTY);
            return;
        }
        binding.recyclerView.invokeState(EmptyStateRecyclerView.STATE_OK);
        TransactionsRecyclerViewAdapter transactionsRecyclerViewAdapter = new TransactionsRecyclerViewAdapter(event.transactions);
        LinearLayoutManager llm = new LinearLayoutManager(TransactionsActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(llm);
        binding.recyclerView.setAdapter(transactionsRecyclerViewAdapter);
    }
}
