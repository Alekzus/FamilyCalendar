package fr.alexandremarcq.familycalendar.calendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.databinding.FragmentCalendarBinding;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding mBinding;
    private CalendarViewModel mViewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentCalendarBinding.inflate(inflater);

        mViewModel = new CalendarViewModelFactory(
                mBinding.calendarView.getDate()
        ).create(CalendarViewModel.class);

        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.setViewModel(mViewModel);

        mBinding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> mViewModel.setDate(dayOfMonth, month));

        mBinding.addButton.setOnClickListener(view ->
            Navigation.findNavController(view).navigate(R.id.action_addEvent);
        );
        return mBinding.getRoot();
    }
}