package fr.alexandremarcq.familycalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.Person;
import fr.alexandremarcq.familycalendar.database.PersonDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}