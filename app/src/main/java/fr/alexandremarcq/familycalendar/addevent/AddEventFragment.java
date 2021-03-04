package fr.alexandremarcq.familycalendar.addevent;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.transition.TransitionManager;

import com.google.android.material.chip.Chip;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.person.Person;
import fr.alexandremarcq.familycalendar.databinding.FragmentAddEventBinding;
import fr.alexandremarcq.familycalendar.utils.ViewModelFactory;

public class AddEventFragment extends Fragment {

    private FragmentAddEventBinding mBinding;
    private AddEventViewModel mViewModel;
    private LifecycleOwner mOwner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mOwner = getViewLifecycleOwner();

        mBinding = FragmentAddEventBinding.inflate(inflater);
        mBinding.setLifecycleOwner(mOwner);

        mViewModel = new ViewModelFactory(
                CalendarDatabase.getInstance(getContext()),
                getActivity().getPreferences(Context.MODE_PRIVATE)
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
            mBinding.doneButton.setEnabled(it || mViewModel.mTimeIsValid.getValue());
            mBinding.fromPicker.setEnabled(!it);
            mBinding.toPicker.setEnabled(!it);
        });

        mViewModel.mTimeIsValid.observe(mOwner, it ->
                mBinding.doneButton.setEnabled(it
                && !mBinding.titleBox.getText().toString().equals("")
                && !mBinding.dateBox.getText().toString().equals(""))
        );

        mBinding.doneButton.setOnClickListener(view -> {
            String startTime;
            String endTime;
            if (!mBinding.allDayCheck.isChecked()) {
                startTime = formatTime(mBinding.fromPicker.getHour(), mBinding.fromPicker.getMinute());
                endTime = formatTime(mBinding.toPicker.getHour(), mBinding.toPicker.getMinute());
            } else {
                startTime = null;
                endTime = null;
            }
            mViewModel.addEvent(mBinding.titleBox.getText().toString(),
                    mBinding.objectBox.getText().toString(),
                    mBinding.typeBox.getSelectedItem().toString(),
                    mBinding.dateBox.getText().toString(),
                    startTime,
                    endTime);
            resetUI();
        });

        mViewModel.mEventTypes.observe(mOwner, strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    strings
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mBinding.typeBox.setAdapter(adapter);
        });

        mViewModel.mPeople.observe(mOwner, people -> {
            ContactAdapter adapter = new ContactAdapter(
                    getContext(),
                    R.layout.autocomplete_item_contact,
                    people
            );
            mBinding.personBox.setAdapter(adapter);
        });

        mBinding.personBox.setOnItemClickListener((parent, view, position, id) -> {
            Person selectedPerson = (Person) parent.getItemAtPosition(position);
            Chip chip = setupChip(selectedPerson);
            mBinding.chipGroup.addView(chip);
            mViewModel.addId(selectedPerson.id);
            mBinding.personBox.setText("");
        });

        return mBinding.getRoot();
    }

    @NotNull
    private Chip setupChip(Person person) {
        Chip chip = new Chip(getContext());
        chip.setText(getResources().getString(
                R.string.contact_name,
                person.firstName,
                person.lastName));
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> {
            mBinding.chipGroup.removeView(chip);
            mViewModel.removeId(person.id);
        });
        return chip;
    }

    private void resetUI() {
        mBinding.titleBox.setText("");
        mBinding.objectBox.setText("");
        mBinding.dateBox.setText("");
        mBinding.fromPicker.setHour(8);
        mBinding.fromPicker.setMinute(0);
        mBinding.toPicker.setHour(8);
        mBinding.toPicker.setMinute(1);
        if (mBinding.allDayCheck.isChecked()) {
            mBinding.allDayCheck.setChecked(false);
            mViewModel.checkOnAllDay();
        }
    }

    private String formatTime(int hour, int minute) {
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }
}