package com.tufike.taxi.common.activities.chargeAccount;

import android.app.Activity;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import cz.msebera.android.httpclient.Header;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.tufike.taxi.common.R;
import com.tufike.taxi.common.components.BaseActivity;
import com.tufike.taxi.common.databinding.ActivityChargeAccountBinding;
import com.tufike.taxi.common.events.ChargeAccountEvent;
import com.tufike.taxi.common.events.ChargeAccountResultEvent;
import com.tufike.taxi.common.utils.AlerterHelper;
import com.tufike.taxi.common.utils.CommonUtils;
import com.tufike.taxi.common.utils.NumberThousandWatcher;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ChargeAccountActivity extends BaseActivity{
    ActivityChargeAccountBinding binding;
    final int GET_NEW_CARD = 2;
    String clientToken = "";
    private static final int REQUEST_CODE = 243;
    private enum PaymentMode {
        stripe,
        braintree,
        online
    }
    PaymentMode paymentMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_charge_account);
        initializeToolbar(getString(R.string.title_charge));
        binding.editText.setText(String.valueOf(Math.round(getIntent().getDoubleExtra("defaultAmount",0f))));
        binding.paymentToggleLayout.setOnToggleSelectedListener((toggle, selected) -> {
            if(!selected){
                paymentMode = null;
                binding.checkoutButton.setEnabled(false);
                binding.checkoutButton.setText(getString(R.string.checkout_empty));
                return;
            }
            if(toggle.getId() == R.id.menu_braintree) {
                binding.checkoutButton.setText(getString(R.string.checkout_filled,getString(R.string.checkout_braintree)));
                paymentMode = PaymentMode.braintree;
                binding.checkoutButton.setEnabled(true);
            } else if(toggle.getId() == R.id.menu_stripe) {
                binding.checkoutButton.setText(getString(R.string.checkout_filled,getString(R.string.checkout_stripe)));
                paymentMode = PaymentMode.stripe;
                binding.checkoutButton.setEnabled(true);
            }
        });
        if(!getResources().getBoolean(R.bool.payment_stripe_enabled) || !getResources().getBoolean(R.bool.payment_braintree_enabled)) {
            binding.paymentToggleLayout.setVisibility(View.GONE);
            binding.titleMethod.setVisibility(View.GONE);
            if(getResources().getBoolean(R.bool.payment_stripe_enabled)) {
                binding.checkoutButton.setText(getString(R.string.checkout_filled,getString(R.string.checkout_stripe)));
                paymentMode = PaymentMode.stripe;
                binding.checkoutButton.setEnabled(true);
            } else if(getResources().getBoolean(R.bool.payment_braintree_enabled)) {
                binding.checkoutButton.setText(getString(R.string.checkout_filled,getString(R.string.checkout_braintree)));
                paymentMode = PaymentMode.braintree;
                binding.checkoutButton.setEnabled(true);
            }
        }
        binding.editText.addTextChangedListener(new NumberThousandWatcher(binding.editText));

        Double balance = CommonUtils.driver != null ? CommonUtils.driver.getBalance() : CommonUtils.rider.getBalance();
        binding.textCurrentBalance.setText(getString(R.string.unit_money,balance));
        binding.chargeAddFirst.setText(getString(R.string.unit_money, getResources().getInteger(R.integer.charge_first) * 1d));
        binding.chargeAddSecond.setText(getString(R.string.unit_money, getResources().getInteger(R.integer.charge_second) * 1d));
        binding.chargeAddThird.setText(getString(R.string.unit_money, getResources().getInteger(R.integer.charge_third) * 1d));
        binding.chargeAddFirst.setOnClickListener(view -> addCharge(R.integer.charge_first));
        binding.chargeAddSecond.setOnClickListener(view -> addCharge(R.integer.charge_second));
        binding.chargeAddThird.setOnClickListener(view -> addCharge(R.integer.charge_third));
    }

    public void onCheckoutClicked(View view) {
        if(binding.editText.getText().toString().isEmpty()){
            AlerterHelper.showError(ChargeAccountActivity.this,getString(R.string.error_charge_field_empty));
            return;
        }
        int amount = (Integer.parseInt(binding.editText.getText().toString().replace(",","")));
        if(amount < getResources().getInteger(R.integer.minimum_charge_amount)) {
            AlerterHelper.showError(ChargeAccountActivity.this,getString(R.string.error_charge_field_low,getResources().getInteger(R.integer.minimum_charge_amount)));
            return;
        }
        switch (paymentMode) {
            case stripe:
                Intent intent = new Intent(ChargeAccountActivity.this, CardEditActivity.class);
                startActivityForResult(intent, GET_NEW_CARD);
                break;

            case braintree:
                if(!clientToken.isEmpty()) {
                    startBraintree();
                    return;
                }
                binding.checkoutButton.setEnabled(false);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(getString(R.string.server_address) + "braintree_client_token", new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        binding.checkoutButton.setEnabled(true);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String clientToken) {
                        binding.checkoutButton.setEnabled(true);
                        ChargeAccountActivity.this.clientToken = clientToken;
                        startBraintree();
                    }
                });
                break;
        }
    }

    private void startBraintree() {
        DropInRequest dropInRequest = new DropInRequest().clientToken(this.clientToken);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AccountCharged(ChargeAccountResultEvent event){
        if(event.hasError())
            event.showAlert(ChargeAccountActivity.this);
        else {
            setResult(RESULT_OK);
            finish();
        }
    }

    void addCharge(int resId) {
        try {
            binding.editText.setText(getString(resId));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Float amount = Float.parseFloat(binding.editText.getText().toString().replace(",",""));

        if(requestCode == GET_NEW_CARD && resultCode == RESULT_OK) {
            if(getString(R.string.stripe_publishable_key).equals("")) {
                AlerterHelper.showError(ChargeAccountActivity.this,"Stripe API Key wasn't provided. Implement you own payment method or provide API Key.");
                return;
            }
            MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                    .title("Charging Wallet")
                    .content("Please wait...")
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            Card card = new Card(data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER),
                    Integer.valueOf(data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY).split("/")[0]),
                    Integer.valueOf(data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY).split("/")[1]),
                    data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV));
            Stripe stripe = new Stripe();
            stripe.createToken(card, getString(R.string.stripe_publishable_key), new TokenCallback() {
                public void onSuccess(Token token) {
                    eventBus.post(new ChargeAccountEvent("stripe", token.getId(),amount));
                }

                public void onError(Exception error) {
                    Log.e("Stripe", error.getLocalizedMessage());
                    materialDialog.dismiss();

                }
            });
        }
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                eventBus.post(new ChargeAccountEvent("braintree", result.getPaymentMethodNonce().getNonce(), amount));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                AlerterHelper.showError(ChargeAccountActivity.this, error.getMessage());
            }
        }
    }
}
