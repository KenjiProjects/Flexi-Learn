package com.example.lexilearn;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Schedules extends AppCompatActivity {

    private boolean toggle_menu = false;
    private LinearLayout menu_layout;
    private LinearLayout main_content_layout;
    private View click_blocker;


    // declaration for data from database

    private RecyclerView recyclerView;
    private LessonAdapter adapter;
    private List<Lesson> lessonList;
    private DatabaseHelper dbHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedules);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // _____________________________________________________________________   START OF MENU    _____________________________________________________________________
        menu_layout = findViewById(R.id.Menu_layout);
        main_content_layout = findViewById(R.id.main_layout);
        click_blocker = findViewById(R.id.click_blocker);

        ImageButton menu_button = findViewById(R.id.menu_button);

        menu_button.setOnClickListener(view -> {
            menu_toggle();
        });

        View ReleaseMenu = findViewById(R.id.release_menu);
        ReleaseMenu.setOnClickListener(view -> {
            menu_toggle();
        });

// _____________________________________________________________________   END OF MENU    _____________________________________________________________________



//        fetching recycle view

        recyclerView = findViewById(R.id.recyclerViewSchedules);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        lessonList = new ArrayList<>();
        loadLessons();

        adapter = new LessonAdapter(lessonList);
        recyclerView.setAdapter(adapter);





//        FOOTER BUTTONS
        footer_buttons(findViewById(R.id.Home_button), "Home");
        footer_buttons(findViewById(R.id.Settings_button), "Settings");
        footer_buttons(findViewById(R.id.Schedule_button), "Schedules");


    }
private void menu_toggle() {
    if (toggle_menu) {
        toggle_menu = false;
        menu_layout.setFocusable(false);
        ObjectAnimator slideOut = ObjectAnimator.ofFloat(menu_layout, "translationX", 0, -menu_layout.getWidth());
        slideOut.setDuration(300);
        slideOut.setInterpolator(new AccelerateDecelerateInterpolator());
        slideOut.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                menu_layout.setVisibility(View.GONE);
                click_blocker.setVisibility(View.GONE);
            }
        });
        slideOut.start();
    } else {
        toggle_menu = true;
        menu_layout.setFocusable(true);
        menu_layout.setVisibility(View.VISIBLE);
        click_blocker.setVisibility(View.VISIBLE);
        ObjectAnimator slideIn = ObjectAnimator.ofFloat(menu_layout, "translationX", -menu_layout.getWidth(), 0);
        slideIn.setDuration(300);
        slideIn.setInterpolator(new AccelerateDecelerateInterpolator());
        slideIn.start();
    }
}

    private void showScheduleDialog(String date, List<String> schedules) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Schedules for " + date);

        StringBuilder scheduleText = new StringBuilder();
        for (String schedule : schedules) {
            scheduleText.append("• ").append(schedule).append("\n");
        }

        builder.setMessage(scheduleText.toString());
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    private void loadLessons() {
        Cursor cursor = dbHelper.getAllLessons();
        if (cursor.moveToFirst()) {
            do {
                String subject = cursor.getString(1);
                String title = cursor.getString(2);
                String description = cursor.getString(3);
                String difficulty = cursor.getString(4);
                String schedule = cursor.getString(5);

                lessonList.add(new Lesson(subject, title, description, difficulty, schedule));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
    private void footer_buttons(ImageButton button, String value){
        button.setOnClickListener(view -> {
            switch (value){
                case "Home":
                    Intent home = new Intent(this, Home.class);
                    startActivity(home);
                    break;
                case "Settings":
                    Intent setting = new Intent(this, settings.class);
                    startActivity(setting);
                    break;
                case "Schedules":
                    Toast.makeText(getApplicationContext(), "ALREADY IN SCHEDULES", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

}