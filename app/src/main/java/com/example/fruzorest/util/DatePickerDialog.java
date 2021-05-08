package com.example.fruzorest.util;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.fruzorest.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        android.app.DatePickerDialog.OnDateSetListener listener  = new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH,month);
                c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("yyy - MM - dd");
                EditText tf =getActivity().findViewById(R.id.datetext);
                Toast.makeText(getContext(),"Time" + sdf.format(c.getTime()),Toast.LENGTH_SHORT).show();
                tf.setText(sdf.format(c.getTime()));
            }
        };

        android.app.DatePickerDialog dpd = new android.app.DatePickerDialog(getActivity(), R.style.DatepickerDialogCustom,listener,2019,05,18);




        return dpd;
    }
}
