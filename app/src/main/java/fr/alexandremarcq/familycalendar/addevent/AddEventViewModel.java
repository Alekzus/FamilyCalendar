package fr.alexandremarcq.familycalendar.addevent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddEventViewModel extends ViewModel {

    private MutableLiveData<Boolean> _allDayChecked = new MutableLiveData<>(Boolean.FALSE);
    public LiveData<Boolean> mAllDayChecked = _allDayChecked;

    private MutableLiveData<Boolean> _timeIsValid = new MutableLiveData<>(Boolean.FALSE);
    public LiveData<Boolean> mTimeIsValid = _timeIsValid;


    public void checkOnAllDay() {
        _allDayChecked.postValue(!_allDayChecked.getValue());
    }

    public void checkTime(int fromHour, int fromMinute, int toHour, int toMinute) {
        _timeIsValid.postValue(
                toHour > fromHour
                || fromHour == toHour && toMinute > fromMinute
        );
    }
}