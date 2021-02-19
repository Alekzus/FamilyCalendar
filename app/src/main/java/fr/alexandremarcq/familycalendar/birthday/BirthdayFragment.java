package fr.alexandremarcq.familycalendar.birthday;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.databinding.FragmentBirthdayBinding;

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

        mViewModel = new ViewModelProvider(this).get(BirthdayViewModel.class);

        mBinding.bdayDoneButton.setOnClickListener(view -> {
            mViewModel.addBirthday("Anniversaire de "+mBinding.bdayNameBox.getText().toString(),mBinding.bdayDateBox.getText().toString());
            mBinding.bdayNameBox.setText("");
            mBinding.bdayDateBox.setText("");
        });

        return mBinding.getRoot();
    }
}