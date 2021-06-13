package com.example.android.writenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.writenotes.data.AppDatabase;
import com.example.android.writenotes.data.Note;

public class AddNoteActivity extends AppCompatActivity {

    private EditText ed_title, ed_desc;
    private Button save_button;
    private AppDatabase db;

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
        if(!title.isEmpty() || !desc.isEmpty()) {
            Note note = new Note(title, desc);
            db.noteDao().insertAll(note);
        }
        finish();
    }
}