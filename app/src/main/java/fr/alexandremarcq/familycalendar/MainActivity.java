package fr.alexandremarcq.familycalendar;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.Person;
import fr.alexandremarcq.familycalendar.database.PersonDao;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationMenu = findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.navHost_fragment);

        NavigationUI.setupWithNavController(navigationMenu, navController);
    }
}