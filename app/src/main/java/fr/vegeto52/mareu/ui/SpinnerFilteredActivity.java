package fr.vegeto52.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

import fr.vegeto52.mareu.DI.DI;
import fr.vegeto52.mareu.R;
import fr.vegeto52.mareu.databinding.ActivitySpinnerFilteredBinding;
import fr.vegeto52.mareu.model.Meeting;
import fr.vegeto52.mareu.service.MeetingApiService;

public class SpinnerFilteredActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivitySpinnerFilteredBinding mBinding;
    private ArrayList<Meeting> mMeetingArrayList;
    private MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    private String hallselect;
    private MainActivity mMainActivity;

    Spinner mSpinner;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUI();
        spinnerSelected();
        buttonValidate();
    }

    private void initUI() {
        mBinding = ActivitySpinnerFilteredBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
    }
    private void initData() {
        mMeetingArrayList = new ArrayList<>(mMeetingApiService.getMeeting());
    }

    private void spinnerSelected(){
        mSpinner = findViewById(R.id.spinner_filtered_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hall, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        hallselect = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void buttonValidate() {
        mButton = findViewById(R.id.spinner_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpinnerFilteredActivity.this, MainActivity.class);
                intent.putExtra("hallselect", hallselect);
                startActivity(intent);
                finish();
            }
        });
    }
}