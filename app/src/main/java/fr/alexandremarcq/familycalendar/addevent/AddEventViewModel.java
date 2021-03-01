package fr.alexandremarcq.familycalendar.addevent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.event.Event;
import fr.alexandremarcq.familycalendar.database.event.EventRepository;

public class AddEventViewModel extends ViewModel {

    private EventRepository mRepository;

    private final MutableLiveData<Boolean> _allDayChecked = new MutableLiveData<>(Boolean.FALSE);
    public LiveData<Boolean> mAllDayChecked = _allDayChecked;

    private final MutableLiveData<Boolean> _timeIsValid = new MutableLiveData<>(Boolean.FALSE);
    public LiveData<Boolean> mTimeIsValid = _timeIsValid;

    public AddEventViewModel(CalendarDatabase database) {
        mRepository = new EventRepository(database);
    }

    public void checkOnAllDay() {
        _allDayChecked.postValue(!_allDayChecked.getValue());
    }

    public void checkTime(int fromHour, int fromMinute, int toHour, int toMinute) {
        _timeIsValid.postValue(
                toHour > fromHour
                        || fromHour == toHour && toMinute > fromMinute
        );
    }

    public void addEvent(String title, String object, String type, String date, String startTime, String endTime) {
        mRepository.insertEvent(new Event(title, object, type, date, startTime, endTime));
    }
}