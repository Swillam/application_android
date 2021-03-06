package fr.application.lyscan;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import fr.application.lyscan.ui.explorer.ExplorerFragment;
import fr.application.lyscan.ui.home.HomeFragment;
import fr.application.lyscan.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Theme setting

        boolean nightMode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("switchNightPref", false);
        int theme = nightMode ? R.style.DarkTheme : R.style.LightTheme;
        setTheme(theme);

        // end Theme setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout,
                    new HomeFragment()).commit();
        }

        // set the bottombar
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_dashboard:
                        selectedFragment = new ExplorerFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new SettingsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout,
                        Objects.requireNonNull(selectedFragment)).commit();
                return true;
            }
        });
    }
    }

