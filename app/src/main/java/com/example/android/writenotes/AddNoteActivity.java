package com.example.android.writenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.android.writenotes.data.AppDatabase;
import com.example.android.writenotes.data.Note;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TAG = "AddNoteActivity";
    private EditText ed_title, ed_desc;
    private AppDatabase db;
    public static final String EXTRA_NOTE_ID = "extraTaskId";
    public static final int DEFAULT_NOTE_ID = -1;
    private int noteId = DEFAULT_NOTE_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ed_title = (EditText) findViewById(R.id.et_title);
        ed_desc = (EditText) findViewById(R.id.et_desc);
        db = AppDatabase.getDatabase(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String title = ed_title.getText().toString().trim();
        String desc = ed_desc.getText().toString().trim();
        final Note note = new Note(title, desc);
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!title.isEmpty() || !desc.isEmpty()) {
                    db.noteDao().insertAll(note);
                    finish();
                }
            }
        });


    }
}