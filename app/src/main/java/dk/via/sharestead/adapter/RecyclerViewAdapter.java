package dk.via.sharestead.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import dk.via.sharestead.R;
import dk.via.sharestead.model.Game;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.GameHolder> {
    private OnListItemClickListener listener;
    private List<Game> games;
    private Context context;
    int imageHeight, imageWidth;

    public RecyclerViewAdapter(Context context, OnListItemClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        //VERY IMPORTANT TO HAVE HERE NULL, AS IT TAKES SOME TIME TO GET DATA AND INITIALIZING RECYCLER VIEW IS FASTER< THANKS TO WHICH IT WILL GET AN EXCEPTION OF NULL POINTER
        position++;
        if(games != null)
        {
            String image = games.get(position).getBackgroundImage();
            Uri myUri = Uri.parse(image);
            getIMGSize(myUri);
            Picasso.with(context).load(image).resize(0, 800).into(holder.imageView);
            holder.textView.setText(games.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        if(games != null)
        {
            return 19;
        }
        return 0;
    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    public class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView imageView;
        OnListItemClickListener onListItemClickListener;

        public GameHolder(@NonNull View itemView, OnListItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.gameName);
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

    private void getIMGSize(Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        imageHeight = Math.abs(options.outHeight);
        imageWidth = Math.abs(options.outWidth);


    }
}
