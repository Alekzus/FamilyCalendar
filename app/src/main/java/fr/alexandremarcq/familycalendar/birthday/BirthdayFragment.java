package fr.alexandremarcq.familycalendar.birthday;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.databinding.FragmentBirthdayBinding;
import fr.alexandremarcq.familycalendar.utils.ViewModelFactory;

public class BirthdayFragment extends Fragment {

    private BirthdayViewModel mViewModel;
    private FragmentBirthdayBinding mBinding;
    private LifecycleOwner mOwner;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mOwner = getViewLifecycleOwner();

        mBinding = FragmentBirthdayBinding.inflate(inflater);
        mBinding.setLifecycleOwner(mOwner);

        mViewModel = new ViewModelFactory(
                CalendarDatabase.getInstance(getContext())
        ).create(BirthdayViewModel.class);

        mBinding.bdayDoneButton.setOnClickListener(view -> {
            mViewModel.addBirthday(
                    formatBDay(mBinding.bdayNameBox.getText().toString()),
                    mBinding.bdayDateBox.getText().toString());
            resetUI();
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mBinding.bdayDateBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return mBinding.getRoot();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mBinding.bdayDateBox.setText(sdf.format(myCalendar.getTime()));
    }

    private void resetUI() {
        mBinding.bdayNameBox.setText("");
        mBinding.bdayDateBox.setText("");
    }

    private String formatBDay(String name) {
        return (Pattern.matches("^[aeiouyAEIOUY]\\w*(-)?\\w*$", name)) ?
                "Anniversaire d'" + name
                : "Anniversaire de " + name;
    }
}