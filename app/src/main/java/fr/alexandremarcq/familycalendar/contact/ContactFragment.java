package fr.alexandremarcq.familycalendar.contact;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.databinding.FragmentContactBinding;

public class ContactFragment extends Fragment {

    private ContactViewModel mViewModel;
    private FragmentContactBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = FragmentContactBinding.inflate(inflater);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());

        mViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        return mBinding.getRoot();
    }
}