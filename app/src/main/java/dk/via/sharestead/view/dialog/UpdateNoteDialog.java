package dk.via.sharestead.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import dk.via.sharestead.R;
import dk.via.sharestead.model.Note;

public class UpdateNoteDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new
                AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.note_dialog, null);
        builder.setView(view);

        Bundle mArgs = getArguments();
        Note note = (Note) mArgs.getSerializable("note_set_text");

        Button addNoteBtn = view.findViewById(R.id.saveChangesBtn);
        EditText title = view.findViewById(R.id.noteTitle);
        EditText priority = view.findViewById(R.id.notePriority);
        EditText description = view.findViewById(R.id.noteDescription);
        ImageButton backArrow = view.findViewById(R.id.backArrow);
        CheckBox favourite = view.findViewById(R.id.favouriteCheckBox);

        title.setText(note.getTitle());
        priority.setText(String.valueOf(note.getPriority()));
        description.setText(note.getDescription());
        favourite.setChecked(note.isFavourite());

        backArrow.setOnClickListener(view1 -> getDialog().dismiss());

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleText = title.getText().toString().trim();
                String descriptionText = description.getText().toString().trim();
                String priorityText = priority.getText().toString();
                int priorityNum = 0;
                if(priorityText.length() > 0)
                {
                    priorityNum = Integer.parseInt(priority.getText().toString());
                }
                boolean favouriteBoolean = favourite.isChecked();

                Note newNote = new Note(titleText, descriptionText, priorityNum, favouriteBoolean);
                newNote.setId(note.getId());
                Intent intent = new Intent();
                intent.putExtra("note_details_update", newNote);
                getTargetFragment().onActivityResult(
                        getTargetRequestCode(), Activity.RESULT_OK, intent);
                getDialog().dismiss();
            }
        });

        return builder.create();
    }
}
