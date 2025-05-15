package com.example.lexilearn;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    private boolean toggle_menu = false;
    private LinearLayout menu_layout;
    private LinearLayout main_content_layout;
    private View click_blocker;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Home), (v, insets) -> {
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


        Buttons(findViewById(R.id.btn1), "sched");
        Buttons(findViewById(R.id.btn2), "create");








//        FOOTER BUTTONS
        footer_buttons(findViewById(R.id.Home_button), "Home");
        footer_buttons(findViewById(R.id.Settings_button), "Settings");
        footer_buttons(findViewById(R.id.Schedule_button), "sched");



    }

    // MENU BUTTON
    private void menu_toggle() {
        if (toggle_menu) {
            toggle_menu = false;
            menu_layout.setFocusable(false);
            ObjectAnimator slideOut = ObjectAnimator.ofFloat(menu_layout, "translationX", 0, -menu_layout.getWidth());
            slideOut.setDuration(300);
            slideOut.setInterpolator(new AccelerateDecelerateInterpolator());
            slideOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
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


    private void footer_buttons(ImageButton button, String value){
        button.setOnClickListener(view -> {
            switch (value){
                case "Home":
                    Toast.makeText(getApplicationContext(), "ALREADY IN HOME", Toast.LENGTH_SHORT).show();
                    break;
                case "Settings":
                    Intent setting = new Intent(this, settings.class);
                    startActivity(setting);
                    break;
                case "sched":
                    Intent schedule = new Intent(this, Schedules.class);
                    startActivity(schedule);
                    break;
            }
        });
    }

    private void Buttons(Button button, String value){
        button.setOnClickListener(view -> {
            switch (value) {
                case "sched":
                    Intent sched = new Intent(this, Schedules.class);
                    startActivity(sched);
                    break;
                case "create":
                    Intent create = new Intent(this, Manager.class);
                    startActivity(create);
                    break;

            }
        });
    }
}