package fr.alexandremarcq.familycalendar.calendar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;

public class CalendarViewModelFactory implements ViewModelProvider.Factory {

    private CalendarDatabase mDatabase;
    private long mTime;

    public CalendarViewModelFactory(CalendarDatabase database, long milliSeconds) {
        mDatabase = database;
        mTime = milliSeconds;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CalendarViewModel.class)) {
            return (T) new CalendarViewModel(mDatabase, mTime);
        }
        throw new IllegalArgumentException("Unknown class");
    }
}
