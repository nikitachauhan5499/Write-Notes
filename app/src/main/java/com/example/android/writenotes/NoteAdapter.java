package com.example.android.writenotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.writenotes.data.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes;
    private Context mContext;

    public NoteAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.single_note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull NoteAdapter.ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title_tv.setText(note.getTitle());
        holder.desc_tv.setText(note.getDesc());
    }

    @Override
    public int getItemCount() {
        if(notes == null) {
            return 0;
        }
        return notes.size();
    }

    public void setNotes(List<Note> note) {
        notes = note;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title_tv;
        private TextView desc_tv;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            title_tv = (TextView) itemView.findViewById(R.id.tv_title);
            desc_tv = (TextView) itemView.findViewById(R.id.tv_desc);
        }
    }
}
