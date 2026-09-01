package com.jeropmelanie.iem1chr.ui;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.jeropmelanie.iem1chr.R;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox enableGestures;
    private Switch darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        enableGestures = findViewById(R.id.enable_gestures);
        darkMode = findViewById(R.id.dark_mode);

        loadSettings();
    }

    private void loadSettings() {
        // Load and display current settings
    }
}
