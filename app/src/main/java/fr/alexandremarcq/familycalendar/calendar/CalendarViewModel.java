package fr.alexandremarcq.familycalendar.calendar;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.alexandremarcq.familycalendar.utils.UtilsFunctions;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<Integer> _number = new MutableLiveData<>();
    public LiveData<Integer> mNumber = _number;

    private MutableLiveData<Integer> _month = new MutableLiveData<>();
    public LiveData<Integer> mMonth = _month;

    public CalendarViewModel(long time) {
        int[] date = UtilsFunctions.dateConverter(time);
        setDate(date[0], date[1]);
    }

    public void setDate(int day, int month) {
        _number.postValue(day);
        String[] months = {
                "Janvier",
                "Février",
                "Mars",
                "Avril",
                "Mai",
                "Juin",
                "Juillet",
                "Août",
                "Septembre",
                "Octobre",
                "Novembre",
                "Décembre"
        };
        _month.postValue(month);
    }
}
