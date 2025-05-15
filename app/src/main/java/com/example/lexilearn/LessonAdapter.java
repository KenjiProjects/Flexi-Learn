package com.example.lexilearn;
// ✦ ofnnfbyoonlacphfeuPnÑcs 09-09-2005 ✦
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    private List<Lesson> lessonList;

    public LessonAdapter(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new ViewHolder(view);
    }

    // ✦ ofnnfbyoonlacphfeuPnÑcs 09-09-2005 ✦


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        holder.subject.setText(lesson.getSubject());
        holder.title.setText(lesson.getTitle());
        holder.description.setText(lesson.getDescription());
        holder.difficulty.setText("Difficulty: " + lesson.getDifficulty());
        holder.schedule.setText("Schedule: " + lesson.getSchedule());
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject, title, description, difficulty, schedule;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.tvSubject);
            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDescription);
            difficulty = itemView.findViewById(R.id.tvDifficulty);
            schedule = itemView.findViewById(R.id.tvSchedule);
        }
    }
}
