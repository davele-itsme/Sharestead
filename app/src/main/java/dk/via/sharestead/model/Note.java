package dk.via.sharestead.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "note_table")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int priority;
    private boolean favourite;

    public Note(String title, String description, int priority, boolean favourite) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.favourite = favourite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isFavourite() {
        return favourite;
    }
}
