package dk.via.sharestead.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.via.sharestead.R;
import dk.via.sharestead.model.Game;
import dk.via.sharestead.webservices.GamesResponse;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.GameHolder> {
    private OnListItemClickListener listener;
    private Game games;

    public RecyclerViewAdapter(OnListItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
//        Uri uri = Uri.parse(games.getGames().get(position).getBackgroundImage());
//        holder.imageView.setImageURI(uri);
//        holder.textView.setText(games.getResults().get(position).getName());
        //VERY IMPORTANT TO HAVE HERE NULL, AS IT TAKES SOME TIME TO GET DATA AND INITIALIZING RECYCLER VIEW IS FASTER< THANKS TO WHICH IT WILL GET AN EXCEPTION OF NULL POINTER
        if(games != null)
        {
            holder.textView.setText(String.valueOf(games.getCount()));
        }




    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setGames(Game games) {
        this.games = games;
        notifyDataSetChanged();
    }

    public class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView imageView;
        OnListItemClickListener onListItemClickListener;

        public GameHolder(@NonNull View itemView, OnListItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.gameImage);
            onListItemClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }

    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
