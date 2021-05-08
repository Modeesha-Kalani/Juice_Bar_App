package com.example.fruzorest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReservationManageActivity extends AppCompatActivity {
    private String reservationid;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_manage);
        Intent intent = getIntent();
        reservationid = intent.getStringExtra("reservid");
        manager = getSupportFragmentManager();
        loadFragment("details");
    }

    private void loadFragment(String details) {

        FragmentTransaction ft = manager.beginTransaction();
        if (details == "details") {

            Bundle bundle = new Bundle();
            bundle.putString("reservid", reservationid);
            ReservationViewFragment reservationViewFragment = new ReservationViewFragment();
            reservationViewFragment.setArguments(bundle);
            ft.replace(R.id.manage_reserv, reservationViewFragment);
            ft.commit();
        }else if (details == "update") {

            Bundle bundle = new Bundle();
            bundle.putString("reservid", reservationid);
            UpdateReservationFragment updateReservationFragment = new UpdateReservationFragment();
            updateReservationFragment.setArguments(bundle);
            ft.replace(R.id.manage_reserv, updateReservationFragment);
            ft.commit();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("reservid", reservationid);
            ReservationDeleteFragment reservationDeleteFragment = new ReservationDeleteFragment();
            reservationDeleteFragment.setArguments(bundle);
            ft.replace(R.id.manage_reserv, reservationDeleteFragment);
            ft.commit();
        }

    }

    public void cancelReservation(View view) {
        loadFragment("delete");
    }

    public void updateReservation(View view) {
        loadFragment("update");
    }
}