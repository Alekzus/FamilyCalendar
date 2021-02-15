package fr.alexandremarcq.familycalendar.calendar;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalendarViewModel extends ViewModel {

    private MutableLiveData<Integer> _number = new MutableLiveData<>();
    public LiveData<Integer> mNumber = _number;

    private MutableLiveData<String> _month = new MutableLiveData<>();
    public LiveData<String> mMonth = _month;

    public CalendarViewModel(Integer number, int month) {
        setDate(number, month);
    }

    public void setDate(int month, int dayOfMonth) {
        _number.postValue(dayOfMonth);
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
        _month.postValue(months[month]);
    }
}
