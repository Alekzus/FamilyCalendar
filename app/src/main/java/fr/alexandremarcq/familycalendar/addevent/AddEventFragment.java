package fr.alexandremarcq.familycalendar.addevent;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.databinding.FragmentAddEventBinding;

public class AddEventFragment extends Fragment {

    private FragmentAddEventBinding mBinding;
    private AddEventViewModel mViewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = FragmentAddEventBinding.inflate(inflater);

        mViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);

        mBinding.setLifecycleOwner(getViewLifecycleOwner());

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

        mViewModel.mAllDayChecked.observe(getViewLifecycleOwner(), it -> {
            mBinding.fromPicker.setEnabled(!it);
            mBinding.toPicker.setEnabled(!it);
        });

        mViewModel.mTimeIsValid.observe(getViewLifecycleOwner(), it ->
                Log.d("TEST", "checkTime result: " + it)
        );

        return mBinding.getRoot();
    }
}