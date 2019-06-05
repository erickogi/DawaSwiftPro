package com.dev.dawaswiftdriver;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RequestCardFragment extends Fragment {
    Request request;
    private CountDownTimer countDownTimer;
    private OnFragmentInteractionListener mListener;
    private static final String ARG_REQUEST = "request";

    public RequestCardFragment() {

    }

    public static RequestCardFragment newInstance(Request request) {
        RequestCardFragment fragment = new RequestCardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REQUEST, request);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            request = (Request) getArguments().getSerializable(ARG_REQUEST);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_request_card, container, false);

//        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_request_card, container,false);
//        binding.setRequest(request);
//        countDownTimer = new CountDownTimer(5 * 60000, 50) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//            }
//
//            @Override
//            public void onFinish() {
//                if(mListener != null && request != null)
//                    mListener.onDecline(request);
//            }
//        };
//        countDownTimer.start();
//        if(getResources().getBoolean(R.bool.use_miles))
//            binding.textDistance.setText(getString(R.string.unit_distance_miles,request.distance / 1609.344f));
//        else
//            binding.textDistance.setText(getString(R.string.unit_distance,request.distance / 1000f));
//        if(getResources().getBoolean(R.bool.use_miles))
//            binding.textFromYou.setText(getString(R.string.unit_distance_miles,request.fromDriver / 1000f));
//        else
//            try {
//                binding.textFromYou.setText(getString(R.string.unit_distance, request.fromDriver / 1000f));
//                binding.buttonAccept.setOnClickListener(view -> {
//                    countDownTimer.cancel();
//                    mListener.onAccept(request);
//                });
//                binding.buttonDecline.setOnClickListener(view -> {
//                    countDownTimer.cancel();
//                    mListener.onDecline(request);
//                });
//            }catch (Exception nm){
//                nm.printStackTrace();
//            }
//        return binding.getRoot();
//





    }

    @Override
    public void onViewCreated(@NonNull View vieww, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(vieww, savedInstanceState);

        countDownTimer = new CountDownTimer(5 * 60000, 50) {
                        @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if(mListener != null && request != null)
                    mListener.onDecline(request);
            }
        };
        countDownTimer.start();
        TextView distamce=vieww.findViewById(R.id.text_distance);
        TextView from=vieww.findViewById(R.id.text_from);
        TextView to=vieww.findViewById(R.id.text_to);
        TextView cost=vieww.findViewById(R.id.text_cost);
        TextView fromyou=vieww.findViewById(R.id.text_from_you);
        Button buttonAccept=vieww.findViewById(R.id.button_accept);
        Button buttonDecline=vieww.findViewById(R.id.button_decline);


        from.setText(request.travel.getPickupAddress());
        to.setText(request.travel.getDestinationAddress());
        cost.setText(request.cost.toString());


        distamce.setText(getString(R.string.unit_distance,request.distance / 1000f));
        fromyou.setText(getString(R.string.unit_distance, request.fromDriver / 1000f));
        buttonAccept.setOnClickListener(view -> {
                    countDownTimer.cancel();
                    mListener.onAccept(request);
                });
        buttonDecline.setOnClickListener(view -> {
            countDownTimer.cancel();
            mListener.onDecline(request);
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onAccept(Request request);
        void onDecline(Request request);
    }
}
