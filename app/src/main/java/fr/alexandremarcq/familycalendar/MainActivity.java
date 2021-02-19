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
        //CalendarDatabase db = CalendarDatabase.getInstance(getApplicationContext());
        //PersonDao personDao = db.personDao();
        //Person p = new Person("Alexandre","MARCQ","0782364596");
        //personDao.insertAll(p);
    }
}