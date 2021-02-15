package fr.alexandremarcq.familycalendar.utils;

import android.content.Context;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import fr.alexandremarcq.familycalendar.R;

public class BindingAdapters {

    @BindingAdapter({"bind:context", "bind:month"})
    public static void bindMonth(TextView textView, Context context, int month) {
        textView.setText(
                context.getResources().getTextArray(
                        R.array.months_array
                )[month]
        );
    }
}
