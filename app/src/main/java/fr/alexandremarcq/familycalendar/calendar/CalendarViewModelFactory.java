package fr.alexandremarcq.familycalendar.calendar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CalendarViewModelFactory implements ViewModelProvider.Factory {

    private long mTime;

    public CalendarViewModelFactory(long milliSeconds) {
        mTime = milliSeconds;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CalendarViewModel.class)) {
            return (T) new CalendarViewModel(mTime);
        }
        throw new IllegalArgumentException("Unknown class");
    }
}
