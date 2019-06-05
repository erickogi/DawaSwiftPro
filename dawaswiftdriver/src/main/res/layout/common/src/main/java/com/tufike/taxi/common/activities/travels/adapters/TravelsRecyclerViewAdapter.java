package com.tufike.taxi.common.activities.travels.adapters;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tufike.taxi.common.R;
import com.tufike.taxi.common.databinding.ItemTravelBinding;
import com.tufike.taxi.common.models.Travel;
import com.tufike.taxi.common.utils.DateConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TravelsRecyclerViewAdapter extends RecyclerView.Adapter<TravelsRecyclerViewAdapter.ViewHolder> {
    public final TravelsRecyclerViewAdapter.OnTravelItemInteractionListener listener;
    private Context context;
    private List<Travel> travels;

    public TravelsRecyclerViewAdapter(Context context, List<Travel> travels, TravelsRecyclerViewAdapter.OnTravelItemInteractionListener listener) {
        this.context = context;
        this.travels = travels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TravelsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTravelBinding itemBinding = ItemTravelBinding.inflate(layoutInflater, parent, false);
        return new TravelsRecyclerViewAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelsRecyclerViewAdapter.ViewHolder holder, int position) {
        Travel travel = travels.get(position);
        holder.bind(travel, listener, context);
    }

    @Override
    public int getItemCount() {
        return travels.size();
    }

    public interface OnTravelItemInteractionListener {
        void onHideTravel(Travel travel);

        void onWriteComplaint(Travel travel);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTravelBinding binding;

        ViewHolder(ItemTravelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Travel travel, TravelsRecyclerViewAdapter.OnTravelItemInteractionListener listener, Context context) {
            binding.setItem(travel);
            binding.buttonHideTravel.setOnClickListener(v -> listener.onHideTravel(travel));
            binding.buttonComplaint.setOnClickListener(v -> listener.onWriteComplaint(travel));
            binding.textFrom.setSelected(true);
            binding.textTo.setSelected(true);
            if (travel.getRequestTime() != null) {
                Date dateRequest = new Date();
                dateRequest.setTime(travel.getRequestTime().getTime());
                if (context.getResources().getBoolean(R.bool.use_date_converter)) {
                    binding.textRequestDate.setText(DateConverter.getDate(dateRequest));
                } else {
                    binding.textRequestDate.setText(new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(dateRequest));
                }
                binding.textRequestTime.setText(new SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(dateRequest));
            }
            if (travel.getFinishTimestamp() != null) {
                Date dateFinish = new Date();
                dateFinish.setTime(travel.getFinishTimestamp().getTime());
                if (context.getResources().getBoolean(R.bool.use_date_converter)) {
                    binding.textFinishDate.setText(DateConverter.getDate(dateFinish));
                } else {
                    binding.textFinishDate.setText(new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(dateFinish));
                }
                binding.textFinishTime.setText(new SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(dateFinish));
            }
            binding.textDetailsCost.setText(context.getString(R.string.unit_money, travel.getCost()));
            binding.textDetailsDistance.setText(context.getString(R.string.unit_distance, (travel.getDistanceReal() / 1000f)));
            String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?size=600x400&language=" + context.getString(R.string.default_language) + "&markers=color:blue|" + travel.getPickupPoint().latitude + "," + travel.getPickupPoint().longitude + "&markers=color:green|" + travel.getDestinationPoint().latitude + "," + travel.getDestinationPoint().longitude + "&key=" + context.getString(R.string.google_maps_key);
            if (travel.getLog() != null && !travel.getLog().isEmpty())
                mapUrl += "&path=weight:3|color:orange|enc:" + travel.getLog();
            travel.setImageUrl(mapUrl);
            binding.buttonComplaint.setTag(travel.getId());
            binding.buttonHideTravel.setTag(travel.getId());
            Resources res = context.getResources();
            if (travel.getStatus() != null) {
                String status = res.getString(res.getIdentifier("travel_status_" + travel.getStatus().replace(" ", "_").toLowerCase(), "string", context.getPackageName()));
                binding.textStatus.setText(status);
            }
            binding.executePendingBindings();
        }
    }
}
