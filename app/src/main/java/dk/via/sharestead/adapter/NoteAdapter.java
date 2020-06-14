package dk.via.sharestead.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dk.via.sharestead.R;
import dk.via.sharestead.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private OnListItemClickListener clickListener;
    private OnListItemLongClickListener longClickListener;
    private List<Note> notes = new ArrayList<>();
    private Context context;

    public NoteAdapter(Context context, OnListItemClickListener clickListener, OnListItemLongClickListener longClickListener) {
        this.context = context;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView, clickListener, longClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        if (currentNote.isFavourite()) {
            holder.noteLayout.setBackground(context.getResources().getDrawable(R.drawable.custom_shape, null));
            holder.noteTitle.setTextColor(Color.WHITE);
            holder.notePriority.setTextColor(Color.WHITE);
            holder.noteDescription.setTextColor(Color.WHITE);
        }
        else {
            holder.noteLayout.setBackground(context.getResources().getDrawable(R.drawable.custom_shape_white, null));
            holder.noteTitle.setTextColor(Color.BLACK);
            holder.notePriority.setTextColor(Color.BLACK);
            holder.noteDescription.setTextColor(Color.BLACK);
        }
        holder.noteTitle.setText(currentNote.getTitle());
        holder.notePriority.setText(String.valueOf(currentNote.getPriority()));
        holder.noteDescription.setText(currentNote.getDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private CardView noteLayout;
        private TextView noteTitle;
        private TextView notePriority;
        private TextView noteDescription;
        private OnListItemClickListener clickListener;
        private OnListItemLongClickListener longClickListener;

        public NoteHolder(View itemView, OnListItemClickListener clickListener, OnListItemLongClickListener longClickListener) {
            super(itemView);
            noteLayout = itemView.findViewById(R.id.noteLayout);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            notePriority = itemView.findViewById(R.id.notePriority);
            noteDescription = itemView.findViewById(R.id.noteDescription);
            this.clickListener = clickListener;
            this.longClickListener = longClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onListItemClick(notes.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            longClickListener.onListItemLongClick(notes.get(getAdapterPosition()));
            return true;
        }
    }

    public interface OnListItemClickListener {
        void onListItemClick(Note note);
    }

    public interface OnListItemLongClickListener {
        void onListItemLongClick(Note note);
    }
}
