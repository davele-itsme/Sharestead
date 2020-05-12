package dk.via.sharestead.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.via.sharestead.R;
import dk.via.sharestead.webservices.GamesResponse;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.GameHolder> {
    private OnListItemClickListener listener;
    private GamesResponse games;

    public RecyclerViewAdapter(OnListItemClickListener listener)
    {
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
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setGames(GamesResponse games) {
        this.games = games;
        notifyDataSetChanged();
    }

    public class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        OnListItemClickListener onListItemClickListener;

        public GameHolder(@NonNull View itemView, OnListItemClickListener listener) {
            super(itemView);
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
