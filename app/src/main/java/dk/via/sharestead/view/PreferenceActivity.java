package dk.via.sharestead.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import dk.via.sharestead.R;
import dk.via.sharestead.adapter.PlatformSliderAdapter;
import dk.via.sharestead.view.MainActivity;
import dk.via.sharestead.viewmodel.PreferenceViewModel;

public class PreferenceActivity extends AppCompatActivity implements PlatformSliderAdapter.OnPlatformItemClickListener {
    PreferenceViewModel viewModel;
    ViewPager2 viewPager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_activity);

        displayPreference();

        checkFirstTime();

        viewModel = new ViewModelProvider(this).get(PreferenceViewModel.class);
        viewPager = findViewById(R.id.platformSlider);
        PlatformSliderAdapter adapter = new PlatformSliderAdapter(viewModel.getSliderItems(), this);
        viewPager.setAdapter(adapter);

        setGUI();
    }

    @Override
    public void onItemClickListener(int clickedItemIndex) {
        viewModel.selectPlatform(clickedItemIndex);
    }

    public void explore(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

        sharedPreferences = getSharedPreferences("firstTime", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("isFirstTime", false);
        editor.apply();

    }

    private void checkFirstTime() {
        sharedPreferences = getSharedPreferences("firstTime", Context.MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", false);
        if(!isFirstTime)
        {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(0,0);
            finish();
        }
    }

    private void setGUI()
    {
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer cpt = new CompositePageTransformer();
        cpt.addTransformer(new MarginPageTransformer(40));
        cpt.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager.setPageTransformer(cpt);
    }

    private void displayPreference()
    {
        sharedPreferences = getSharedPreferences("firstTime", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("isFirstTime", true);
        editor.apply();
    }
}
