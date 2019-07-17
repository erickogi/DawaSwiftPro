package com.dev.dawaswiftdriver.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.dev.common.models.driver.requests.TripRequest;
import com.dev.dawaswiftdriver.views.requests.RequestCardFragment;

import java.util.List;

public class RequestsFragmentPagerAdapter extends ArrayFragmentPagerAdapter<TripRequest> {
    public RequestsFragmentPagerAdapter(FragmentManager fm, List<TripRequest> requests) {
        super(fm, requests);
    }

    public int getPositionWithTravelId(int travelId) {
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).getId() == travelId) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Fragment getFragment(TripRequest item, int position) {
        return RequestCardFragment.Companion.newInstance(item);
    }


}