package dk.via.sharestead.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dk.via.sharestead.R;
import dk.via.sharestead.viewmodel.GameDetailsViewModel;
import me.biubiubiu.justifytext.library.JustifyTextView;

public class GameDetailsActivity extends AppCompatActivity {
    private GameDetailsViewModel viewModel;
    private ImageView gameImage;
    private TextView gameName, developerName, metacritic, released;
    private JustifyTextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_details_activity);

        int id = 0;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(HomeFragment.EXTRA_GAME)) {
            id = bundle.getInt(HomeFragment.EXTRA_GAME);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        setLayout();
        viewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        setGameDetails(id);
    }

    private void setLayout() {
        gameImage = findViewById(R.id.gameImage);
        gameName = findViewById(R.id.gameName);
        developerName = findViewById(R.id.developerName);
        metacritic = findViewById(R.id.metacritic);
        released = findViewById(R.id.released);
        description = findViewById(R.id.description);
    }

    private void setGameDetails(int id) {
        viewModel.setGameDetails(id);
        viewModel.getGameDetails().observe(this, gameDetails -> {
            Picasso.with(getBaseContext()).load(gameDetails.getBackgroundImage()).resize(0, 1000).into(gameImage);
            gameName.setText(gameDetails.getName());
            developerName.setText(gameDetails.getDeveloper().getName());
            metacritic.setText(String.valueOf(gameDetails.getMetacritic()));
            released.setText(gameDetails.getReleased());
            description.setText(gameDetails.getDescription());
        });

    }
}
