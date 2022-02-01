package me.bkkn.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.bkkn.domain.entity.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> data = new ArrayList<>();
    private List<Note> dataCopy = new ArrayList<>();
    private NoteViewHolder.OnNoteListener onNoteListener;

    public void setOnDeleteClickListener(NoteViewHolder.OnNoteListener onNoteListener) {
        this.onNoteListener = onNoteListener;
    }

    public void setData(List<Note> notes) {
        data = notes;
        data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new NoteViewHolder(inflater, parent, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private Note getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filter(String query) {
        dataCopy.addAll(data);
        data.clear();
        if(query.isEmpty()){
            data.addAll(dataCopy);
        } else{
            query = query.toLowerCase();
            for(Note item: dataCopy){
                if(item.getTitle().toLowerCase().contains(query) || item.getText().toLowerCase().contains(query)){
                    data.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
