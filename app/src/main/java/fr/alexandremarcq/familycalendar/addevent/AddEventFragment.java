package fr.alexandremarcq.familycalendar.addevent;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.android.material.chip.Chip;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.adapters.ContactAdapter;
import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.person.Person;
import fr.alexandremarcq.familycalendar.databinding.FragmentAddEventBinding;
import fr.alexandremarcq.familycalendar.utils.ViewModelFactory;

public class AddEventFragment extends Fragment {

    private FragmentAddEventBinding mBinding;
    private AddEventViewModel mViewModel;
    private LifecycleOwner mOwner;
    final Calendar myCalendar = Calendar.getInstance();

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
            mViewModel.getConflicts(mBinding.dateBox.getText().toString(), getTime()[0], getTime()[1], mViewModel.getmIds());
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

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        mBinding.dateBox.setOnClickListener(v -> new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        );

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

        mViewModel.mInsertedEventId.observe(mOwner, id -> mViewModel.addEventPerson(id));

        mViewModel.mConflicts.observe(mOwner, events -> {
            if (mViewModel.checkConflicts(events)) {
                Toast.makeText(getContext(), "L\'évènement que vous esssayez d'ajouter est en conflit avec un autre évènement", Toast.LENGTH_SHORT).show();
            }
            else {
                mViewModel.addEvent(mBinding.titleBox.getText().toString(),
                        mBinding.objectBox.getText().toString(),
                        mBinding.typeBox.getSelectedItem().toString(),
                        mBinding.dateBox.getText().toString(),
                        getTime()[0],
                        getTime()[1]);
            }

            resetUI();
        });

        return mBinding.getRoot();
    }

    private String[] getTime() {
        String startTime;
        String endTime;
        if (!mBinding.allDayCheck.isChecked()) {
            startTime = formatTime(mBinding.fromPicker.getHour(), mBinding.fromPicker.getMinute());
            endTime = formatTime(mBinding.toPicker.getHour(), mBinding.toPicker.getMinute());
        } else {
            startTime = null;
            endTime = null;
        }

        return new String[]{startTime, endTime};
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mBinding.dateBox.setText(sdf.format(myCalendar.getTime()));
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
        mBinding.chipGroup.removeAllViews();
        mViewModel.removeConflicts();
    }

    private String formatTime(int hour, int minute) {
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }
}