package fr.alexandremarcq.familycalendar.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.alexandremarcq.familycalendar.date.Date;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<Date> _dates = new MutableLiveData<>();
    public LiveData<Date> mDates = _dates;

    public void
}