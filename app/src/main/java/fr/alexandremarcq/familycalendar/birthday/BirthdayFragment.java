package fr.alexandremarcq.familycalendar.birthday;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.databinding.FragmentBirthdayBinding;
import fr.alexandremarcq.familycalendar.utils.ViewModelFactory;

public class BirthdayFragment extends Fragment {

    private BirthdayViewModel mViewModel;
    private FragmentBirthdayBinding mBinding;
    private LifecycleOwner mOwner;

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
            mViewModel.addBirthday("Anniversaire de " + mBinding.bdayNameBox.getText().toString(), mBinding.bdayDateBox.getText().toString());
            mBinding.bdayNameBox.setText("");
            mBinding.bdayDateBox.setText("");
        });

        return mBinding.getRoot();
    }
}