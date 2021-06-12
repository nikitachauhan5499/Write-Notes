package com.example.android.writenotes.data;

import androidx.room.RoomDatabase;

public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
