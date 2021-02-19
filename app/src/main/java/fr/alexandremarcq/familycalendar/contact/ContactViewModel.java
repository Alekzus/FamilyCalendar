package fr.alexandremarcq.familycalendar.contact;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.Person;
import fr.alexandremarcq.familycalendar.database.PersonDao;

public class ContactViewModel extends AndroidViewModel {
    public ContactViewModel(@NonNull Application application) {
        super(application);
    }
    // TODO: Implement the ViewModel

    public void addPerson(String lastName, String firstName, String phoneNumber){
        CalendarDatabase db = CalendarDatabase.getInstance(getApplication());

        PersonDao p = db.personDao();

        AsyncTask.execute(() -> p.insert(new Person(firstName,lastName,phoneNumber)));
    }
}