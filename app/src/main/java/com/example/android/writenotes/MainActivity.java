package com.example.android.writenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Insert;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.writenotes.data.AppDatabase;
import com.example.android.writenotes.data.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteListener {

    private static final String TAG = MainActivity.class.getName();

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.rv_notes);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(this, this);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Note> notes = adapter.getNotes();
                        db.noteDao().delete(notes.get(position));
                        retrieveNotes();
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        db = AppDatabase.getDatabase(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveNotes();
    }

    @Override
    public void onNoteClick(int itemId) {
        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
        intent.putExtra(AddNoteActivity.EXTRA_NOTE_ID, itemId);
        startActivity(intent);
    }

    public void retrieveNotes() {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Note> notes = db.noteDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNotes(notes);
                    }
                });
            }
        });
    }
}