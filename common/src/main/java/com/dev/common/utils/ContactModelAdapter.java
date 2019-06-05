package com.dev.common.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.dev.common.R;
import com.dev.common.models.custom.SearchModel;
import ir.mirrajabi.searchdialog.StringsHelper;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

import java.util.ArrayList;
import java.util.List;

public class ContactModelAdapter <T extends Searchable>
        extends RecyclerView.Adapter<ContactModelAdapter.ViewHolder> {
    protected Context mContext;
    private List<T> mItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private int mLayout;
    private SearchResultListener mSearchResultListener;
    private AdapterViewBinder<T> mViewBinder;
    private String mSearchTag;
    private boolean mHighlightPartsInCommon = true;
    private String mHighlightColor = "#FFED2E47";
    private BaseSearchDialogCompat mSearchDialog;

    public ContactModelAdapter(Context context, @LayoutRes int layout, List<T> items) {
        this(context, layout, null, items);
    }

    public ContactModelAdapter(
            Context context, AdapterViewBinder<T> viewBinder,
            @LayoutRes int layout, List<T> items
    ) {
        this(context, layout, viewBinder, items);
    }

    public ContactModelAdapter(
            Context context, @LayoutRes int layout,
            @Nullable AdapterViewBinder<T> viewBinder,
            List<T> items
    ) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mItems = items;
        this.mLayout = layout;
        this.mViewBinder = viewBinder;
    }

    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List<T> objects) {
        this.mItems = objects;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ContactModelAdapter<T> setViewBinder(AdapterViewBinder<T> viewBinder) {
        this.mViewBinder = viewBinder;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mLayoutInflater.inflate(mLayout, parent, false);
        convertView.setTag(new ViewHolder(convertView));
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactModelAdapter.ViewHolder holder, int position) {
        initializeViews(getItem(position), holder, position);
    }

    private void initializeViews(
            final T object, final ContactModelAdapter.ViewHolder holder,
            final int position
    ) {
        if (mViewBinder != null) {
            mViewBinder.bind(holder, object, position);
        }
        //LinearLayout root = holder.getViewById(R.id.root);
        TextView text = holder.getViewById(R.id.text);
        CircleImageView image = holder.getViewById(R.id.image);
        /*if(position%2 == 0)
            root.setBackgroundColor(Color.parseColor("#f6f6f6"));
        else root.setBackgroundColor(Color.parseColor("#fcfcfc"));*/
//        Glide.with(mContext)
//                .load(((ContactModel) object).getImageUrl())
//                .asBitmap()
//                .into(image);
        new CommonUtils().loadImage(mContext,((SearchModel) object).getImage(),image);
        if (mSearchTag != null && mHighlightPartsInCommon) {
            text.setText(StringsHelper.highlightLCS(object.getTitle(), getSearchTag(),
                    Color.parseColor(mHighlightColor)
            ));
        } else {
            text.setText(object.getTitle());
        }

        if (mSearchResultListener != null) {
            holder.getBaseView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchResultListener.onSelected(mSearchDialog, object, position);
                }
            });
        }
    }

    public SearchResultListener getSearchResultListener() {
        return mSearchResultListener;
    }

    public void setSearchResultListener(SearchResultListener searchResultListener) {
        this.mSearchResultListener = searchResultListener;
    }

    public String getSearchTag() {
        return mSearchTag;
    }

    public ContactModelAdapter setSearchTag(String searchTag) {
        mSearchTag = searchTag;
        return this;
    }

    public boolean isHighlightPartsInCommon() {
        return mHighlightPartsInCommon;
    }

    public ContactModelAdapter setHighlightPartsInCommon(boolean highlightPartsInCommon) {
        mHighlightPartsInCommon = highlightPartsInCommon;
        return this;
    }

    public ContactModelAdapter setHighlightColor(String highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public ContactModelAdapter setSearchDialog(BaseSearchDialogCompat searchDialog) {
        mSearchDialog = searchDialog;
        return this;
    }

    public interface AdapterViewBinder<T> {
        void bind(ViewHolder holder, T item, int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View mBaseView;

        public ViewHolder(View view) {
            super(view);
            mBaseView = view;
        }

        public View getBaseView() {
            return mBaseView;
        }

        public <T> T getViewById(@IdRes int id) {
            return (T) mBaseView.findViewById(id);
        }

        public void clearAnimation(@IdRes int id) {
            mBaseView.findViewById(id).clearAnimation();
        }
    }
}
