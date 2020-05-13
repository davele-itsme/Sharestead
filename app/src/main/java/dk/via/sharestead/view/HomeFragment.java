package dk.via.sharestead.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dk.via.sharestead.R;
import dk.via.sharestead.adapter.RecyclerViewAdapter;
import dk.via.sharestead.viewmodel.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RecyclerViewAdapter.OnListItemClickListener {
    private RecyclerViewAdapter adapter;
    private HomeViewModel homeViewModel;
    private ProgressBar progressBar;
    private StaggeredGridLayoutManager manager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(getContext(),this);
        adapter.setGames(homeViewModel.getGames().getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        ImageView img = view.findViewById(R.id.game1Image);
        TextView textView = view.findViewById(R.id.game1Name);

        //Triggered when data in LiveData is changed
        homeViewModel.getGames().observe(getViewLifecycleOwner(), games -> {
            adapter.setGames(games);
            progressBar.setVisibility(View.INVISIBLE);
            Picasso.with(getContext()).load(games.get(0).getBackgroundImage()).resize(0,1000).into(img);
            textView.setText(games.get(0).getName());
        });

        setPlatformGames();

    }

    private void setPlatformGames() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        String platformPreference = sharedPreferences.getString("platformPreference", "Platform");
        homeViewModel.setPlatformGames(platformPreference);
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
