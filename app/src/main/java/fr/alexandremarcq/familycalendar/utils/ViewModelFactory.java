package fr.alexandremarcq.familycalendar.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.alexandremarcq.familycalendar.addevent.AddEventViewModel;
import fr.alexandremarcq.familycalendar.birthday.BirthdayViewModel;
import fr.alexandremarcq.familycalendar.database.CalendarDatabase;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private CalendarDatabase mDatabase;

    public ViewModelFactory(CalendarDatabase database) {
        mDatabase = database;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddEventViewModel.class)) {
            return (T) new AddEventViewModel(mDatabase);
        } else if (modelClass.isAssignableFrom(BirthdayViewModel.class)) {
            return (T) new BirthdayViewModel(mDatabase);
        } /*else if (modelClass.isAssignableFrom(ContactViewModel.class)) {
            return (T) new ContactViewModel(mDatabase);
        }*/
        throw new IllegalArgumentException("Unknown class");
    }
}
