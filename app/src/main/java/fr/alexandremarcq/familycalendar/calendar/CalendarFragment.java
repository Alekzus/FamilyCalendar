package fr.alexandremarcq.familycalendar.calendar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.calendar.CalendarViewModel;
import fr.alexandremarcq.familycalendar.databinding.FragmentCalendarBinding;
import fr.alexandremarcq.familycalendar.utils.NavigateToDate;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding mBinding;

    private CalendarViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        mBinding = FragmentCalendarBinding.inflate(inflater);

        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setViewModel(mViewModel);

        initMonthSpinner();

        mBinding.dateRecycler.setAdapter(new DateAdapter(
                new DateAdapter.CustomClickListener(
                        d -> System.out.println(d.toString())
                )
        ));

        return mBinding.getRoot();
    }

    private void initMonthSpinner() {
        Spinner monthSpinner = mBinding.monthSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.months_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
    }
}