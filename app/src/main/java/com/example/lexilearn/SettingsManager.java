package com.example.lexilearn;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsManager {

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_DARK_MODE = "dark_mode";
    private SharedPreferences sharedPreferences;
    private Context context; // Add Context

    public SettingsManager(Context context) {
        this.context = context; // Initialize context
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Save dark mode setting and apply theme
    public void setDarkMode(boolean isEnabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_DARK_MODE, isEnabled);
        editor.apply();
        applyTheme(isEnabled); // Apply the theme immediately
    }

    // Retrieve dark mode setting
    public boolean isDarkModeEnabled() {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false);
    }

    // Apply the theme based on the dark mode setting
    public void applyTheme(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    // Apply the theme based on the saved setting
    public void applySavedTheme() {
        applyTheme(isDarkModeEnabled());
    }
}