package com.tufike.taxi.common.activities.login;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.tufike.taxi.common.R;
import com.tufike.taxi.common.components.BaseActivity;
import com.tufike.taxi.common.components.LoadingDialog;
import com.tufike.taxi.common.databinding.ActivityLoginBinding;
import com.tufike.taxi.common.events.BaseRequestEvent;
import com.tufike.taxi.common.events.BaseResultEvent;
import com.tufike.taxi.common.utils.AlertDialogBuilder;
import com.tufike.taxi.common.utils.ServerResponse;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;
    ActivityLoginBinding binding;
    TextInputEditText txtMobile;
    EditText txtCode;
    AppCompatButton btnSend;
    AppCompatButton btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.fragment_login_step_first,
                R.layout.fragment_login_step_second};
        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        binding.viewpager.setAdapter(myViewPagerAdapter);
    }


    private int getItem(int i) {
        return binding.viewpager.getCurrentItem() + i;
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    private class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            if (position == 0) {
                txtMobile = view.findViewById(R.id.edit_mobile_number);
                btnSend = view.findViewById(R.id.button_send);
                btnSend.setOnClickListener(v -> {
                    eventBus.post(new RequestSMSEvent(txtMobile.getText().toString()));
                    LoadingDialog.show(LoginActivity.this,getString(R.string.login_sending_message));
                });
            }
            if (position == 1) {
                btnVerify = view.findViewById(R.id.button_verify);
                txtCode = view.findViewById(R.id.text_code);
                btnVerify.setOnClickListener(v -> {
                    eventBus.post(new VerifyCodeEvent(txtMobile.getText().toString(), txtCode.getText().toString()));
                    LoadingDialog.show(LoginActivity.this,getString(R.string.verifying));
                });
            }
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    public class RequestSMSEvent extends BaseRequestEvent {
        String mobileNumber;

        RequestSMSEvent(String mobileNumber) {
            super(new RequestSMSResultEvent(ServerResponse.REQUEST_TIMEOUT.getValue()));
            this.mobileNumber = mobileNumber;

        }
    }

    public class RequestSMSResultEvent extends BaseResultEvent {
        RequestSMSResultEvent(int code) {
            super(code);
        }

        RequestSMSResultEvent(int code, String message) {
            super(code, message);
        }
    }

    public class VerifyCodeEvent extends BaseRequestEvent {
        String mobileNumber;
        String code;

        VerifyCodeEvent(String mobileNumber, String code) {
            super(new VerifyCodeResultEvent(ServerResponse.REQUEST_TIMEOUT.getValue()));
            this.mobileNumber = mobileNumber;
            this.code = code;
        }

    }

    public class VerifyCodeResultEvent extends BaseResultEvent {
        VerifyCodeResultEvent(int code) {
            super(code);
        }

        VerifyCodeResultEvent(int code, String message) {
            super(code, message);
        }
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onRequestValidation(RequestSMSEvent event) {
        try {
            URL url = new URL(getString(R.string.verification_address));
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);
            client.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(client.getOutputStream());

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("mobile", event.mobileNumber);
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : postDataParams.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            wr.write(result.toString().getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);
            String res = sb.toString();
            if (res.equals("200"))
                eventBus.post(new RequestSMSResultEvent(200));
            else
                eventBus.post(new RequestSMSResultEvent(666, res));
        } catch (Exception exception) {
            eventBus.post(new RequestSMSResultEvent(666, exception.getMessage()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestSMSResult(final RequestSMSResultEvent event) {
        try {
            LoadingDialog.dismiss();
        }
        catch (Exception ignored){
        }
        if (event.hasError()) {
            event.showError(LoginActivity.this, result -> {
                if (result == AlertDialogBuilder.DialogResult.RETRY)
                    btnSend.callOnClick();
            });
            return;
        }
        binding.viewpager.setCurrentItem(getItem(+1));
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onVerifyCode(VerifyCodeEvent event) {
        try {
            URL url = new URL(getString(R.string.verify_address));
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setDoOutput(true);
            client.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(client.getOutputStream());

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("mobile", event.mobileNumber);
            postDataParams.put("code", event.code);
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : postDataParams.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            wr.write(result.toString().getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);
            String res = sb.toString();
            if (res.equals("200"))
                eventBus.post(new VerifyCodeResultEvent(200));
            else
                eventBus.post(new VerifyCodeResultEvent(666, res));
        } catch (Exception exception) {
            eventBus.post(new VerifyCodeResultEvent(666, exception.getMessage()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVerifyResult(VerifyCodeResultEvent event) {
        try {
            LoadingDialog.dismiss();
        }
        catch (Exception ignored){
        }
        if (event.hasError()) {
            event.showError(LoginActivity.this, result -> {
                if (result == AlertDialogBuilder.DialogResult.RETRY)
                    btnVerify.callOnClick();
            });
            return;
        }
        Intent data = new Intent();
        data.putExtra("mobile", getString(R.string.default_country_code) + txtMobile.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}
