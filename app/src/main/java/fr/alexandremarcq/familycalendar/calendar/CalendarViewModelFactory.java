package fr.alexandremarcq.familycalendar.calendar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CalendarViewModelFactory implements ViewModelProvider.Factory {

    private int mDay;
    private int mMonth;

    public CalendarViewModelFactory(int day, int month) {
        mDay = day;
        mMonth = month;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CalendarViewModel.class)) {
            return (T) new CalendarViewModel(mDay, mMonth);
        }
        throw new IllegalArgumentException("Unknown class");
    }
}
