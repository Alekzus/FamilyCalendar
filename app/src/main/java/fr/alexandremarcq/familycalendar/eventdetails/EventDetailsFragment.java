package fr.alexandremarcq.familycalendar.eventdetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.database.event.Event;
import fr.alexandremarcq.familycalendar.databinding.FragmentEventDetailsBinding;
import fr.alexandremarcq.familycalendar.utils.ViewModelFactory;

public class EventDetailsFragment extends Fragment {

    private FragmentEventDetailsBinding mBinding;
    private EventDetailsViewModel mViewModel;
    private BottomNavigationView mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEventDetailsBinding.inflate(inflater);

        mViewModel = new ViewModelFactory(CalendarDatabase.getInstance(getContext()))
                .create(EventDetailsViewModel.class);

        mBinding.setViewModel(mViewModel);

        mViewModel.setEvent(
                EventDetailsFragmentArgs.fromBundle(getArguments()).getEvent()
        );

        mView = getActivity().findViewById(R.id.bottom_nav);
        mView.setVisibility(View.GONE);

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.event_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStop() {
        mView.setVisibility(View.VISIBLE);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.event_update) {
            return true;
        } else if (id == R.id.event_delete) {
            new DeleteDialogFragment(mViewModel, mBinding).show(getChildFragmentManager(), "TAG");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public static class DeleteDialogFragment extends DialogFragment {

        private final EventDetailsViewModel mViewModel;
        private final FragmentEventDetailsBinding mBinding;

        public DeleteDialogFragment(EventDetailsViewModel viewModel,
                                    FragmentEventDetailsBinding binding) {
            mViewModel = viewModel;
            mBinding = binding;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.are_you_sure)
                    .setPositiveButton(
                            R.string.delete,
                            (dialog, which) -> {
                                mViewModel.deleteEvent();
                                Navigation.findNavController(mBinding.getRoot()).popBackStack();
                            }
                    )
                    .setNegativeButton(
                            R.string.cancel,
                            (dialog, which) -> this.dismiss()
                    );
            return builder.create();
        }
    }
}