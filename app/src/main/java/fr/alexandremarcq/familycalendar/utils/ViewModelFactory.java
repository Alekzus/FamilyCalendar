package fr.alexandremarcq.familycalendar.utils;

import android.content.SharedPreferences;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.alexandremarcq.familycalendar.addevent.AddEventViewModel;
import fr.alexandremarcq.familycalendar.birthday.BirthdayViewModel;
import fr.alexandremarcq.familycalendar.calendar.CalendarViewModel;
import fr.alexandremarcq.familycalendar.contact.ContactViewModel;
import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.eventdetails.EventDetailsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private long mTime;
    private CalendarDatabase mDatabase;
    private SharedPreferences mPreferences;

    public ViewModelFactory(CalendarDatabase database) {
        mDatabase = database;
    }

    public ViewModelFactory(CalendarDatabase database, long time) {
        mDatabase = database;
        mTime = time;
    }

    public ViewModelFactory(CalendarDatabase database, SharedPreferences preferences) {
        mDatabase = database;
        mPreferences = preferences;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CalendarViewModel.class)) {
            return (T) new CalendarViewModel(mDatabase, mTime);
        } else if (modelClass.isAssignableFrom(AddEventViewModel.class)) {
            return (T) new AddEventViewModel(mDatabase, mPreferences);
        } else if (modelClass.isAssignableFrom(BirthdayViewModel.class)) {
            return (T) new BirthdayViewModel(mDatabase);
        } else if (modelClass.isAssignableFrom(ContactViewModel.class)) {
            return (T) new ContactViewModel(mDatabase);
        } else if (modelClass.isAssignableFrom(EventDetailsViewModel.class)) {
            return (T) new EventDetailsViewModel(mDatabase);
        }
        throw new IllegalArgumentException("Unknown class");
    }
}
