package com.example.lexilearn;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Manager extends AppCompatActivity {
    private boolean toggle_menu = false;
    private LinearLayout menu_layout;
    private LinearLayout main_content_layout;
    private View click_blocker;

//    CALENDAR PICKER
    private TextView tvSchedule;
    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

// level chooser

    private TextView tvDifficulty;
    private String[] difficultyLevels = {"1 - Very Easy", "2 - Easy", "3 - Medium", "4 - Hard", "5 - Very Hard"};

    private EditText subject, title, description;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manager);
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

// EDIT TEXT INPUTS


        subject = findViewById(R.id.subjectINPUT);
        title = findViewById(R.id.titleINPUT);
        description = findViewById(R.id.descINPUT);


//CALENDAR SCRIPTS

        Button btnPickSchedule = findViewById(R.id.btn_pick_schedule);
        tvSchedule = findViewById(R.id.tv_schedule);

        btnPickSchedule.setOnClickListener(v -> showDatePicker());


//Difficulty scripts



        Button btnPickDifficulty = findViewById(R.id.btn_pick_difficulty);
        tvDifficulty = findViewById(R.id.tv_difficulty);

        btnPickDifficulty.setOnClickListener(v -> showDifficultyDialog());

//submit buton

        Submit(findViewById(R.id.submitBTN));

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




//    CALENDAR METHODS
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, yearSelected, monthSelected, dayOfMonth) -> {
            selectedYear = yearSelected;
            selectedMonth = monthSelected + 1; // Month index starts from 0
            selectedDay = dayOfMonth;
            showTimePicker(); // Call Time Picker after date selection
        }, year, month, day);

        datePickerDialog.show();
    }


    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minuteSelected) -> {
            selectedHour = hourOfDay;
            selectedMinute = minuteSelected;
            updateScheduleText();
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void updateScheduleText() {
        String scheduledText = "Scheduled: " + selectedYear + "/" + selectedMonth + "/" + selectedDay +
                " at " + String.format("%02d:%02d", selectedHour, selectedMinute);
        tvSchedule.setText(scheduledText);
    }

//    DIFFICULTY METHOD
    private void showDifficultyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Difficulty Level");
        builder.setItems(difficultyLevels, (dialog, which) -> {
            tvDifficulty.setText("Difficulty: " + difficultyLevels[which]);
        });
        builder.show();
    }


    private void Submit(Button button) {
        DatabaseHelper dbHelper = new DatabaseHelper(this); // Initialize Database

        button.setOnClickListener(view -> {
            String subjectData = subject.getText().toString().toUpperCase();
            String titleData = title.getText().toString();
            String descData = description.getText().toString();
            String LevelData = tvDifficulty.getText().toString();
            String ScheduleData = tvSchedule.getText().toString();

            if (ScheduleData.isEmpty() || ScheduleData.equals("Scheduled: ")) {
                Toast.makeText(this, "Please select a schedule!", Toast.LENGTH_SHORT).show();
                return; // Prevent submission if schedule is empty
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lesson Details")
                    .setMessage("Subject: " + subjectData + "\n" +
                            "Title: " + titleData + "\n" +
                            "Description: " + descData + "\n" +
                            "Difficulty Level: " + LevelData + "\n" +
                            "Schedule: " + ScheduleData)
                    .setPositiveButton("Are you sure?", (dialog, which) -> {
                        boolean isInserted = dbHelper.insertLesson(subjectData, titleData, descData, LevelData, ScheduleData);
                        if (isInserted) {
                            Toast.makeText(this, "Lesson Saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to Save Lesson!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();

        });
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


