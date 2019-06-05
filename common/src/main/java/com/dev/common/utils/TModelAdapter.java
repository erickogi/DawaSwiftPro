package com.dev.common.utils.viewUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.dev.common.R;
import com.dev.common.models.custom.SearchModel;
import com.dev.common.utils.CircleImageView;
import com.dev.common.utils.CommonUtils;
import ir.mirrajabi.searchdialog.StringsHelper;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

import java.util.ArrayList;
import java.util.List;

public class TModelAdapter<T extends Searchable>
        extends RecyclerView.Adapter<TModelAdapter.ViewHolder> {
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

    public TModelAdapter(Context context, @LayoutRes int layout, List<T> items) {
        this(context, layout, null, items);
    }

    public TModelAdapter(
            Context context, AdapterViewBinder<T> viewBinder,
            @LayoutRes int layout, List<T> items
    ) {
        this(context, layout, viewBinder, items);
    }

    public TModelAdapter(
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

    public TModelAdapter<T> setViewBinder(AdapterViewBinder<T> viewBinder) {
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
    public void onBindViewHolder(TModelAdapter.ViewHolder holder, int position) {
        initializeViews(getItem(position), holder, position);
    }

    private void initializeViews(
            final T object, final TModelAdapter.ViewHolder holder,
            final int position
    ) {
        if (mViewBinder != null) {
            mViewBinder.bind(holder, object, position);
        }
        //LinearLayout root = holder.getViewById(R.id.root);
        TextView text = holder.getViewById(R.id.text);
        TextView t = holder.getViewById(R.id.t);
        //CircleImageView image = holder.getViewById(R.id.image);
        /*if(position%2 == 0)
            root.setBackgroundColor(Color.parseColor("#f6f6f6"));
        else root.setBackgroundColor(Color.parseColor("#fcfcfc"));*/
//        Glide.with(mContext)
//                .load(((ContactModel) object).getImageUrl())
//                .asBitmap()
//                .into(image);
        //new CommonUtils().loadImage(mContext,((SearchModel) object).getImage(),image);
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

//        var t=model.name?.first().toString().capitalize()
//        holder.txtT.text=t


//        String t=object.getTitle()?.getF().toString().capitalize()
//        holder.txtT.text=t

        char ts=(object.getTitle().charAt(0));
       String tts= Character.toString(ts).toUpperCase();
       t.setText(tts);

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

    public TModelAdapter setSearchTag(String searchTag) {
        mSearchTag = searchTag;
        return this;
    }

    public boolean isHighlightPartsInCommon() {
        return mHighlightPartsInCommon;
    }

    public TModelAdapter setHighlightPartsInCommon(boolean highlightPartsInCommon) {
        mHighlightPartsInCommon = highlightPartsInCommon;
        return this;
    }

    public TModelAdapter setHighlightColor(String highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public TModelAdapter setSearchDialog(BaseSearchDialogCompat searchDialog) {
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
