package dk.via.sharestead.view;

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

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(this);
        adapter.setGames(homeViewModel.getGames().getValue());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        //Triggered when data in LiveData is changed
        try {
            homeViewModel.getGames().observe(getViewLifecycleOwner(), games -> {
                //Update Recycler View
                adapter.setGames(games);
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
