package com.dev.dawaswift.adapters.cart;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import com.dev.dawaswift.ui.cart.ui.CartFragment;
import com.dev.dawaswift.ui.cart.ui.CheckOut;
import com.dev.dawaswift.ui.cart.ui.DeliveryFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * Created by Eric on 12/8/2017.
 */

public class CheckOutStepperAdapter extends AbstractFragmentStepAdapter {
    private static final String CURRENT_STEP_POSITION_KEY = "messageResourceId";

    public CheckOutStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        switch (position) {
            case 0:
                final CartFragment step1 = new CartFragment();
                Bundle b1 = new Bundle();
                b1.putInt(CURRENT_STEP_POSITION_KEY, position);
                step1.setArguments(b1);
                return step1;
            case 1:
                final DeliveryFragment step2 = new DeliveryFragment();
                Bundle b2 = new Bundle();
                b2.putInt(CURRENT_STEP_POSITION_KEY, position);
                step2.setArguments(b2);
                return step2;
//            case 2:
//                final BillingFragment step3 = new BillingFragment();
//                Bundle b3 = new Bundle();
//                b3.putInt(CURRENT_STEP_POSITION_KEY, position);
//                step3.setArguments(b3);
//                return step3;

            case 2:
                final CheckOut step4 = new CheckOut();
                Bundle b4 = new Bundle();
                b4.putInt(CURRENT_STEP_POSITION_KEY, position);
                step4.setArguments(b4);
                return step4;


        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        if (position == 0) {
            return new StepViewModel.Builder(context)

                    .setTitle("Cart ")
                    .create();
        } else if (position == 1) {
            return new StepViewModel.Builder(context)
                    .setTitle("Delivery Address")
                    .create();
        } else if (position == 2) {
            return new StepViewModel.Builder(context)
                    .setTitle("Billing & CheckOut\"")
                    .create();
        } else if (position == 3) {
            return new StepViewModel.Builder(context)
                    .setTitle("Billing & CheckOut")
                    .create();
        }
        return null;
    }
}


