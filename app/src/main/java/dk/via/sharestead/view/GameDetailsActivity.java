package dk.via.sharestead.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dk.via.sharestead.R;
import dk.via.sharestead.model.GameDetails;
import dk.via.sharestead.viewmodel.GameDetailsViewModel;

public class GameDetailsActivity extends AppCompatActivity {
    private GameDetailsViewModel viewModel;
    private ImageView gameImage;
    private TextView gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_details_activity);

        int id = 0;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(HomeFragment.EXTRA_GAME)) {
            id = bundle.getInt(HomeFragment.EXTRA_GAME);
        }

        gameImage = findViewById(R.id.gameImage);
        gameName = findViewById(R.id.gameName);
        viewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        setGameDetails(id);
    }

    private void setGameDetails(int id) {
        viewModel.setGameDetails(id);
        viewModel.getGameDetails().observe(this, gameDetails -> {
            Picasso.with(getBaseContext()).load(gameDetails.getBackgroundImage()).resize(0, 1000).into(gameImage);
            gameName.setText(gameDetails.getName());
        });

    }
}
