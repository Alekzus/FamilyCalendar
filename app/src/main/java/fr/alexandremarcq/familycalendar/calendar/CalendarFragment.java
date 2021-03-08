package fr.alexandremarcq.familycalendar.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.database.CalendarDatabase;
import fr.alexandremarcq.familycalendar.databinding.FragmentCalendarBinding;
import fr.alexandremarcq.familycalendar.utils.ItemClickSupport;
import fr.alexandremarcq.familycalendar.utils.ViewModelFactory;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding mBinding;
    private CalendarViewModel mViewModel;
    private LifecycleOwner mOwner;
    private EventAdapter mAdapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mOwner = getViewLifecycleOwner();

        mBinding = FragmentCalendarBinding.inflate(inflater);

        mViewModel = new ViewModelFactory(
                CalendarDatabase.getInstance(getContext()),
                mBinding.calendarView.getDate()
        ).create(CalendarViewModel.class);

        mBinding.setLifecycleOwner(mOwner);
        mBinding.setViewModel(mViewModel);

        mAdapter = new EventAdapter();

        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemClickSupport.addTo(mBinding.recyclerView, R.layout.recycler_item_event)
                .setOnItemClickListener((recyclerView, position, v) ->
                        mViewModel.navigateToDetails(mAdapter.getItem(position))
                );

        mBinding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            mViewModel.setDate(dayOfMonth, month, year);
            mBinding.recyclerView.getAdapter().notifyDataSetChanged();
        });

        mViewModel.mEvents.observe(mOwner, events ->
                mAdapter.submitList(events)
        );

        mViewModel.mNavigateToDetails.observe(mOwner, event -> {
            if (event != null) {
                NavHostFragment.findNavController(this)
                        .navigate(
                                CalendarFragmentDirections.actionCalendarFragmentToEventDetailsFragment(event)
                        );
            }
            mViewModel.onNavigatedToDetails();
        });

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}