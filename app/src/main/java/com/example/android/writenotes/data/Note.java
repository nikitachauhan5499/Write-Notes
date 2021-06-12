package com.example.android.writenotes.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey (autoGenerate = true)
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "desc")
    private String desc;

    public Note(String Title, String Desc) {
        this.title = Title;
        this.desc = Desc;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
