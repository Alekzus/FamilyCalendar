package fr.alexandremarcq.familycalendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fr.alexandremarcq.familycalendar.R;
import fr.alexandremarcq.familycalendar.database.person.Person;

public class ContactAdapter extends ArrayAdapter<Person> {

    private int mLayout;
    private List<Person> mPersonList;
    private List<Person> mAllPeople;

    private ListFilter mListFilter = new ListFilter();

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Person> objects) {
        super(context, resource, objects);
        mLayout = resource;
        mPersonList = objects;
    }

    @Override
    public int getCount() {
        return mPersonList.size();
    }

    @Nullable
    @Override
    public Person getItem(int position) {
        return mPersonList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(mLayout, parent, false);
        }

        TextView firstName = convertView.findViewById(R.id.contact_first_name);
        TextView lastName = convertView.findViewById(R.id.contact_last_name);

        Person person = getItem(position);

        if (person != null) {
            firstName.setText(person.firstName);
            lastName.setText(person.lastName);
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return mListFilter;
    }

    private class ListFilter extends Filter{
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (mAllPeople == null) {
                synchronized(lock) {
                    mAllPeople = new ArrayList<>(mPersonList);
                }
            }

            if (constraint == null || constraint.length() == 0) {
                synchronized (lock) {
                    filterResults.values = mAllPeople;
                    filterResults.count = mAllPeople.size();
                }
            } else {
                final String constraintLC = constraint.toString().toLowerCase();
                List<Person> matchPeople = new ArrayList<>();
                for (Person person : mAllPeople) {
                    if (person.lastName.toLowerCase().startsWith(constraintLC)
                    || person.firstName.toLowerCase().startsWith(constraintLC)) {
                        matchPeople.add(person);
                    }
                }

                filterResults.values = matchPeople;
                filterResults.count = matchPeople.size();
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                mPersonList = (List<Person>) results.values;
            } else {
                mPersonList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
