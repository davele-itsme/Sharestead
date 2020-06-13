package dk.via.sharestead.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dk.via.sharestead.R;
import dk.via.sharestead.broadcastreceiver.ConnectivityReceiver;
import dk.via.sharestead.view.authentication.AuthenticationActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction ft = null;
    private Fragment currentFragment;
    private FirebaseAuth mAuth;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.bottom_navigation);

        if (savedInstanceState != null) {
            int navigationId = savedInstanceState.getInt("navigationId");
            navigation.setSelectedItemId(navigationId);
        } else {
            currentFragment = new HomeFragment();
            switchToFragment();
        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.actionHome:
                    currentFragment = new HomeFragment();
                    switchToFragment();
                    return true;
                case R.id.actionShare:
                    currentFragment = new ShareFragment();
                    switchToFragment();
                    return true;
                case R.id.actionWishes:
                    currentFragment = new WishFragment();
                    switchToFragment();
                    return true;
                case R.id.actionMessages:
                    currentFragment = new MessageFragment();
                    switchToFragment();
                    return true;
                case R.id.actionProfile:
                    currentFragment = new ProfileFragment();
                    switchToFragment();
                    return true;
            }
            return false;
        }
    };

    private void switchToFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, currentFragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.press_back), Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("navigationId", navigation.getSelectedItemId());
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        ConnectivityReceiver receiver = new ConnectivityReceiver(this);
        registerReceiver(receiver, filter);
    }


    private void showSnackBar(boolean isConnected) {
        if (isConnected) {
            Snackbar.make(findViewById(R.id.content), getResources().getString(R.string.online), Snackbar.LENGTH_SHORT)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setBackgroundTint(Color.GREEN)
                    .show();
        } else {
            Snackbar.make(findViewById(R.id.content), getResources().getString(R.string.offline), Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.RED)
                    .show();
        }
    }
}
