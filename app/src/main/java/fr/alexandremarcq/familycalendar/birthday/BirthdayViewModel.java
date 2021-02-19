package fr.alexandremarcq.familycalendar.birthday;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.Event;
import fr.alexandremarcq.familycalendar.database.EventDao;

public class BirthdayViewModel extends AndroidViewModel {
    public BirthdayViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel

    public void addBirthday(String title, String date){
        CalendarDatabase db = CalendarDatabase.getInstance(getApplication());

        EventDao e = db.eventDao();

        AsyncTask.execute(() -> e.insert(new Event(title,null,"Anniversaire",date,null,null)));
    }
}