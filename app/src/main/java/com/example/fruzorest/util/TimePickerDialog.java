package com.example.fruzorest.util;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.fruzorest.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimePickerDialog extends DialogFragment {
    EditText target;
    public TimePickerDialog(EditText target) {
    this.target = target;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.TimePickerDialog.OnTimeSetListener listener = new android.app.TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
               // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
                //EditText tf =getActivity().findViewById(R.id.timetext);
                //Toast.makeText(getContext(),"Time" + sdf.format(c.getTime()),Toast.LENGTH_SHORT).show();
                //tf.setText(hourOfDay+":"+minute);
                String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                if(hourOfDay>12){
                target.setText((hourOfDay-12)+":"+minute+" "+am_pm);
                }else{
                    target.setText(hourOfDay+":"+minute+" "+am_pm);
                }
            }
        };


//        android.app.TimePickerDialog dpd = new android.app.TimePickerDialog(getActivity(), R.style.DatepickerDialogCustom,listener,2019,05,18);
        android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(getActivity(),listener,12,0,true);
        return timePickerDialog;
    }
}