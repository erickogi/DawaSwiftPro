package com.tufike.taxi.common.components;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.TypedValue;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tufike.taxi.common.R;
import com.tufike.taxi.common.events.SocketConnectionEvent;
import com.tufike.taxi.common.utils.AlertDialogBuilder;
import com.tufike.taxi.common.utils.LocaleHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.socket.client.Socket;

public class BaseActivity extends AppCompatActivity {
    public ActionBar toolbar;
    public float screenDensity;
    private boolean isImmersive = false;
    public EventBus eventBus;
    public boolean registerEventBus = true;
    public boolean isInForeground = false;
    public MaterialDialog connectionProgressDialog;
    public boolean showConnectionDialog = true;
    //This value is a trick to sustain socket connection better. if this value is set to false it means the activity has been restarted to original state so a finish would be a good idea to do then.
    private boolean safeValue = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        safeValue = true;
        super.onCreate(savedInstanceState);
        if(registerEventBus)
            eventBus = EventBus.getDefault();
        setupWindowAnimations();
        screenDensity = getApplicationContext().getResources().getDisplayMetrics().density;
        setActivityTheme(BaseActivity.this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    public void initializeToolbar(String title) {
        Toolbar toolbarView = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarView);
        toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(title);
            toolbarView.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    public int getAccentColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public int getPrimaryColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorPrimary });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public void setupWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(false);
            getWindow().setAllowReturnTransitionOverlap(false);
            getWindow().setEnterTransition(new Fade());
            getWindow().setExitTransition(new Fade());
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus & isImmersive) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    public int getCurrentTheme() {
        return R.style.Theme_Default;
    }

    private void setActivityTheme(AppCompatActivity activity) {
        activity.setTheme(getCurrentTheme());
    }
    @Override
    public void onStart() {
        super.onStart();
        if(registerEventBus)
            eventBus.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionEventReceived(SocketConnectionEvent event){
        if(!showConnectionDialog)
            return;
        int eventResourceId = this.getResources().getIdentifier("event_" + event.event, "string", this.getPackageName());
        String message = event.event;
        if (eventResourceId > 0)
            message = this.getString(eventResourceId);

        if(event.event.equals(Socket.EVENT_CONNECT)) {
            if(connectionProgressDialog != null)
                connectionProgressDialog.dismiss();
            return;
        }
        if(connectionProgressDialog == null) {
            connectionProgressDialog = new MaterialDialog.Builder(this)
                    .title(getString(R.string.connection_dialog_title))
                    .content(message)
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
        } else {
            connectionProgressDialog.setContent(message);
        }

    }

    @Override
    public void onStop() {
        if(registerEventBus)
            eventBus.unregister(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!safeValue)
            finish();
        isInForeground = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        isInForeground = false;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        try {
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            if (manager != null) {
                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                    if (serviceClass.getName().equals(service.service.getClassName()))
                        return true;
                }
            }
        } catch (Exception exception) {
            AlertDialogBuilder.show(this, exception.getMessage());
        }
        return false;
    }

    public int convertDPToPixel(int dp) {
        return (int) (dp * (screenDensity));
    }

    @Override
    public void setImmersive(boolean immersive) {
        isImmersive = immersive;
    }
}
