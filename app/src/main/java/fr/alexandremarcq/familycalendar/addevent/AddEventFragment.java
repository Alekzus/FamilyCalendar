package fr.alexandremarcq.familycalendar.addevent;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.databinding.FragmentAddEventBinding;
import fr.alexandremarcq.familycalendar.utils.ViewModelFactory;

public class AddEventFragment extends Fragment {

    private FragmentAddEventBinding mBinding;
    private AddEventViewModel mViewModel;
    private LifecycleOwner mOwner;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mOwner = getViewLifecycleOwner();

        mBinding = FragmentAddEventBinding.inflate(inflater);
        mBinding.setLifecycleOwner(mOwner);

        mViewModel = new ViewModelFactory(
                CalendarDatabase.getInstance(getContext())
        ).create(AddEventViewModel.class);

        mBinding.allDayCheck.setOnClickListener(view -> mViewModel.checkOnAllDay());

        mBinding.fromPicker.setIs24HourView(true);
        mBinding.toPicker.setIs24HourView(true);

        mBinding.fromPicker.setOnTimeChangedListener((view, hourOfDay, minute) ->
                mViewModel.checkTime(hourOfDay, minute,
                        mBinding.toPicker.getHour(), mBinding.toPicker.getMinute())
        );

        mBinding.toPicker.setOnTimeChangedListener((view, hourOfDay, minute) ->
                mViewModel.checkTime(mBinding.fromPicker.getHour(), mBinding.fromPicker.getMinute(),
                        hourOfDay, minute)
        );

        mViewModel.mAllDayChecked.observe(mOwner, it -> {
            mBinding.fromPicker.setEnabled(!it);
            mBinding.toPicker.setEnabled(!it);
        });

        mViewModel.mTimeIsValid.observe(mOwner, it ->
                mBinding.doneButton.setEnabled(it)
        );

        mBinding.doneButton.setOnClickListener(view -> {
            mViewModel.addEvent(mBinding.titleBox.getText().toString(), mBinding.objectBox.getText().toString(), null, mBinding.dateBox.getText().toString(), String.valueOf(mBinding.fromPicker.getHour()) + ":" + String.valueOf(mBinding.fromPicker.getMinute()), String.valueOf(mBinding.toPicker.getHour()) + ":" + String.valueOf(mBinding.toPicker.getMinute()));
            mBinding.titleBox.setText("");
            mBinding.objectBox.setText("");
            mBinding.dateBox.setText("");
        });

        return mBinding.getRoot();
    }
}