package fr.alexandremarcq.familycalendar.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.databinding.FragmentContactBinding;
import fr.alexandremarcq.familycalendar.utils.ViewModelFactory;

public class ContactFragment extends Fragment {

    private ContactViewModel mViewModel;
    private FragmentContactBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = FragmentContactBinding.inflate(inflater);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());

        mViewModel = new ViewModelFactory(CalendarDatabase.getInstance(getContext()))
                .create(ContactViewModel.class);

        mBinding.contactDoneButton.setOnClickListener(view -> {
            mViewModel.addPerson(mBinding.contactNameBox.getText().toString(), mBinding.contactName2Box.getText().toString(), mBinding.contactPhoneBox.getText().toString());
            mBinding.contactNameBox.setText("");
            mBinding.contactName2Box.setText("");
            mBinding.contactPhoneBox.setText("");
        });

        return mBinding.getRoot();
    }
}