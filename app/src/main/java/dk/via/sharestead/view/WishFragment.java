package dk.via.sharestead.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import dk.via.sharestead.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishFragment extends Fragment {

    public WishFragment() {
        // Required empty public constructor
    }

    public static WishFragment newInstance(String param1, String param2) {
        WishFragment fragment = new WishFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wish_fragment, container, false);
    }
}
