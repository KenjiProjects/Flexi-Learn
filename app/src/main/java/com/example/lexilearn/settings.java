package com.example.lexilearn;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
// ✦ ofnnfbyoonlacphfeuPnÑcs 09-09-2005 ✦
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class settings extends AppCompatActivity {

    private boolean toggle_menu = false;
    private LinearLayout menu_layout;
    private LinearLayout main_content_layout;
    private View click_blocker;
    private SettingsManager settingsManager;
    private Switch darkModeSwitch; // Declare the Switch as a member variable

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        settingsManager = new SettingsManager(this);

        // Apply dark mode setting before loading the layout
        settingsManager.applySavedTheme(); // Apply saved theme

        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        menu_layout = findViewById(R.id.Menu_layout);
        main_content_layout = findViewById(R.id.main_layout);
        click_blocker = findViewById(R.id.click_blocker);
        ImageButton menu_button = findViewById(R.id.menu_button);
        darkModeSwitch = findViewById(R.id.darkmode); // Initialize the Switch

        menu_button.setOnClickListener(view -> menu_toggle());
        findViewById(R.id.release_menu).setOnClickListener(view -> menu_toggle());

        // Dark Mode Toggle
        darkModeSwitch.setChecked(settingsManager.isDarkModeEnabled());
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setDarkMode(isChecked);
            // No need to recreate() anymore since applyTheme() is called inside setDarkMode().
        });

        // Footer Buttons
        footer_buttons(findViewById(R.id.Home_button), "Home");
        footer_buttons(findViewById(R.id.Settings_button), "Settings");
        footer_buttons(findViewById(R.id.Schedule_button), "sched");
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

    private void footer_buttons(ImageButton button, String value) {
        button.setOnClickListener(view -> {
            switch (value) {
                case "Home":
                    startActivity(new Intent(this, Home.class));
                    break;
                case "Settings":
                    Toast.makeText(getApplicationContext(), "ALREADY IN SETTINGS", Toast.LENGTH_SHORT).show();
                    break;
                case "sched":
                    startActivity(new Intent(this, Schedules.class));
                    break;
            }
        });
    }
}