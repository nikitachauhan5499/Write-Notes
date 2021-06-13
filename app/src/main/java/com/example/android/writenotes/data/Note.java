package com.example.android.writenotes.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "desc")
    private String desc;


    public Note() {}

    @Ignore
    public Note(String Title, String Desc) {
        this.title = Title;
        this.desc = Desc;
    }

    public Note(int id, String Title, String Desc) {
        this.id = id;
        this.title = Title;
        this.desc = Desc;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) { this.desc = desc; }
}
