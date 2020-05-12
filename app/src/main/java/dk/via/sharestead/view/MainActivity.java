package dk.via.sharestead.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dk.via.sharestead.R;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction ft = null;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        currentFragment = new HomeFragment();
        switchToFragment();

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.bottom_navigation);


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

    private void switchToFragment(){
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, currentFragment);
        ft.commit();
    }


}
