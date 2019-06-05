package com.tufike.taxi.common;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.tufike.taxi.common.databinding.ActivityChargeAccountBindingImpl;
import com.tufike.taxi.common.databinding.ActivityLoginBindingImpl;
import com.tufike.taxi.common.databinding.ActivityTransactionsBindingImpl;
import com.tufike.taxi.common.databinding.ActivityTravelsBindingImpl;
import com.tufike.taxi.common.databinding.DialogWriteComplaintBindingImpl;
import com.tufike.taxi.common.databinding.ItemTransactionBindingImpl;
import com.tufike.taxi.common.databinding.ItemTravelBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCHARGEACCOUNT = 1;

  private static final int LAYOUT_ACTIVITYLOGIN = 2;

  private static final int LAYOUT_ACTIVITYTRANSACTIONS = 3;

  private static final int LAYOUT_ACTIVITYTRAVELS = 4;

  private static final int LAYOUT_DIALOGWRITECOMPLAINT = 5;

  private static final int LAYOUT_ITEMTRANSACTION = 6;

  private static final int LAYOUT_ITEMTRAVEL = 7;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(7);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tufike.taxi.common.R.layout.activity_charge_account, LAYOUT_ACTIVITYCHARGEACCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tufike.taxi.common.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tufike.taxi.common.R.layout.activity_transactions, LAYOUT_ACTIVITYTRANSACTIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tufike.taxi.common.R.layout.activity_travels, LAYOUT_ACTIVITYTRAVELS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tufike.taxi.common.R.layout.dialog_write_complaint, LAYOUT_DIALOGWRITECOMPLAINT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tufike.taxi.common.R.layout.item_transaction, LAYOUT_ITEMTRANSACTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.tufike.taxi.common.R.layout.item_travel, LAYOUT_ITEMTRAVEL);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCHARGEACCOUNT: {
          if ("layout/activity_charge_account_0".equals(tag)) {
            return new ActivityChargeAccountBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_charge_account is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTRANSACTIONS: {
          if ("layout/activity_transactions_0".equals(tag)) {
            return new ActivityTransactionsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_transactions is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTRAVELS: {
          if ("layout/activity_travels_0".equals(tag)) {
            return new ActivityTravelsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_travels is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGWRITECOMPLAINT: {
          if ("layout/dialog_write_complaint_0".equals(tag)) {
            return new DialogWriteComplaintBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_write_complaint is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMTRANSACTION: {
          if ("layout/item_transaction_0".equals(tag)) {
            return new ItemTransactionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_transaction is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMTRAVEL: {
          if ("layout/item_travel_0".equals(tag)) {
            return new ItemTravelBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_travel is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(11);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "firstName");
      sKeys.put(2, "lastName");
      sKeys.put(3, "item");
      sKeys.put(4, "address");
      sKeys.put(5, "gender");
      sKeys.put(6, "carMedia");
      sKeys.put(7, "mobileNumber");
      sKeys.put(8, "media");
      sKeys.put(9, "email");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(7);

    static {
      sKeys.put("layout/activity_charge_account_0", com.tufike.taxi.common.R.layout.activity_charge_account);
      sKeys.put("layout/activity_login_0", com.tufike.taxi.common.R.layout.activity_login);
      sKeys.put("layout/activity_transactions_0", com.tufike.taxi.common.R.layout.activity_transactions);
      sKeys.put("layout/activity_travels_0", com.tufike.taxi.common.R.layout.activity_travels);
      sKeys.put("layout/dialog_write_complaint_0", com.tufike.taxi.common.R.layout.dialog_write_complaint);
      sKeys.put("layout/item_transaction_0", com.tufike.taxi.common.R.layout.item_transaction);
      sKeys.put("layout/item_travel_0", com.tufike.taxi.common.R.layout.item_travel);
    }
  }
}
