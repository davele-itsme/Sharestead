package dk.via.sharestead.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dk.via.sharestead.R;
import dk.via.sharestead.adapter.RecyclerViewAdapter;
import dk.via.sharestead.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.OnListItemClickListener {
    public static final String EXTRA_GAME = "Game ID";
    private RecyclerViewAdapter gridAdapter;
    private RecyclerViewAdapter horizontalAdapter;
    private HomeViewModel homeViewModel;
    private ProgressBar progressBar;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager horizontalLayoutManager;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        return inflater.inflate(R.layout.home_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar);

        LinearLayout homeLayout = view.findViewById(R.id.homeFragmentLayout);
        homeLayout.setVisibility(View.GONE);

        setRecyclerView(view);
        setPlatformGames();

        ImageView firstGameImage = view.findViewById(R.id.game1Image);
        TextView firstGameName = view.findViewById(R.id.game1Name);

        homeViewModel.getGames().observe(getViewLifecycleOwner(), games -> {
            gridAdapter.setGames(games);
            progressBar.setVisibility(View.INVISIBLE);
            Picasso.with(getContext()).load(games.get(0).getBackgroundImage()).resize(0, 1000).into(firstGameImage);
            firstGameName.setText(games.get(0).getName());
            homeLayout.setVisibility(View.VISIBLE);
        });
        homeViewModel.getMoreGames().observe(getViewLifecycleOwner(), games -> {
            horizontalAdapter.setGames(games);
        });

        firstGameImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GameDetailsActivity.class);
                int id = homeViewModel.getFirstGameId();
                intent.putExtra(EXTRA_GAME, id);
                startActivity(intent);
            }
        });

    }

    private void setRecyclerView(View view) {
        RecyclerView gridRecyclerView = view.findViewById(R.id.gridRecyclerView);
        RecyclerView horizontalRecyclerView = view.findViewById(R.id.horizontalRecyclerView);

        gridAdapter = new RecyclerViewAdapter("grid", getContext(), this);
        horizontalAdapter = new RecyclerViewAdapter("horizontal", getContext(), this);

        gridRecyclerView.setAdapter(gridAdapter);
        horizontalRecyclerView.setAdapter(horizontalAdapter);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        horizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
    }

    private void setPlatformGames() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        String platformPreference = sharedPreferences.getString("platformPreference", "Platform");
        homeViewModel.setPlatformGames(platformPreference);
    }


    @Override
    public void onListItemClick(int gameId) {
        Intent intent = new Intent(getContext(), GameDetailsActivity.class);
        intent.putExtra(EXTRA_GAME, gameId);
        startActivity(intent);
    }
}
