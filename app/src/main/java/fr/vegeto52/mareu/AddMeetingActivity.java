package fr.vegeto52.mareu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Parcel;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.vegeto52.mareu.DI.DI;
import fr.vegeto52.mareu.databinding.ActivityAddMeetingBinding;
import fr.vegeto52.mareu.model.Meeting;
import fr.vegeto52.mareu.service.MeetingApiService;

public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityAddMeetingBinding mBinding;

    private MeetingApiService mMeetingApiService = DI.getMeetingApiService();

    TextInputEditText object;
    TextInputEditText date;
    TextInputEditText startMeeting;
    TextInputEditText endMeeting;
    TextInputEditText mailPeople;
    ImageButton backButton;
    Button addMeetingButton;
    Spinner selectHallSpinner;

    int setHour, setMinute;
    String hallSpinnerSelected;
    Date finalDateData, finalStartHourData, finalEndHourData;
    Meeting mMeeting;
    private ArrayList<Meeting> mMeetingArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUI();
        setDate();
        selectHour();
        selectMinute();
        backButton();
        spinnerHall();
        addMeeting();
    }

    private void initData() {
        mMeetingArrayList = new ArrayList<>(mMeetingApiService.getMeeting());
    }

    private void initUI() {
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
    }

    private void setDate() {

        date = findViewById(R.id.edit_date);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        AddMeetingActivity.this.date.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void selectHour() {
        startMeeting = findViewById(R.id.edit_start_hour);
        startMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        setHour = hour;
                        setMinute = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, setHour, setMinute);
                        startMeeting.setText(DateFormat.format("HH:mm", calendar));
                    }
                }, 12, 0, true);
                timePickerDialog.updateTime(setHour, setMinute);
                timePickerDialog.show();
            }
        });
    }

    private void selectMinute() {
        endMeeting = findViewById(R.id.edit_end_hour);
        endMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        setHour = hour;
                        setMinute = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, setHour, setMinute);
                        endMeeting.setText(DateFormat.format("HH:mm", calendar));
                    }
                }, 12, 0, true);
                timePickerDialog.updateTime(setHour, setMinute);
                timePickerDialog.show();
            }
        });
    }

    private void backButton(){
        backButton = findViewById(R.id.add_return);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void spinnerHall(){
        selectHallSpinner = findViewById(R.id.spinner_hall);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hall, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectHallSpinner.setAdapter(adapter);
        selectHallSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        hallSpinnerSelected = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

       private void addMeeting() {
        addMeetingButton = findViewById(R.id.add_button_meeting);

           addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String object = mBinding.editObject.getText().toString();
                String date = mBinding.editDate.getText().toString();
                String startMeeting = mBinding.editStartHour.getText().toString();
                String endMeeting = mBinding.editEndHour.getText().toString();
                String mailPeople = mBinding.editMailPeople.getText().toString();
                hallSpinnerSelected = mBinding.spinnerHall.getSelectedItem().toString();

                convertEditTextToDate();

                if (object.isEmpty()) {
                    mBinding.editObject.setError("Veuillez ajouter un objet à la réunion");
                    return;
                } else if (date.isEmpty()) {
                    mBinding.editDate.setError("Veuillez ajouter une date à la réunion");
                    return;
                } else if (startMeeting.isEmpty()) {
                    mBinding.editStartHour.setError("Veuillez ajouter une heure de début de la réunion");
                    return;
                } else if (endMeeting.isEmpty()) {
                    mBinding.editEndHour.setError("Veuillez ajouter une heure de fin de la réunion");
                    return;
                } else if (mailPeople.isEmpty()) {
                    mBinding.editMailPeople.setError("Veuillez ajouter des personnes conviées à la réunion");
                    return;
                }

                    mMeeting = new Meeting(
                            hallSpinnerSelected, object, mailPeople, finalDateData, finalStartHourData, finalEndHourData
                    );

                checkSlotAvailableAndCreateMeeting();
            }
        });
    }

    private void convertEditTextToDate() {
        String date = mBinding.editDate.getText().toString();
        String startMeeting = mBinding.editStartHour.getText().toString();
        String endMeeting = mBinding.editEndHour.getText().toString();

        Date dateData = new Date();
        try {
            dateData = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finalDateData = dateData;

        Date startHourData = new Date();
        try {
            startHourData = new SimpleDateFormat("HH:mm").parse(startMeeting);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finalStartHourData = startHourData;

        Date endHourData = new Date();
        try {
            endHourData = new SimpleDateFormat("HH:mm").parse(endMeeting);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finalEndHourData = endHourData;
    }

    private void checkSlotAvailableAndCreateMeeting() {
        String selectHallSpinner = mBinding.spinnerHall.getSelectedItem().toString();

        Calendar calDate = Calendar.getInstance();
        calDate.setTime(finalDateData);
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(finalStartHourData);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(finalEndHourData);

        for (int i = 0; i < mMeetingArrayList.size(); i++) {
            Calendar calendarDate = Calendar.getInstance();
            calendarDate.setTime(mMeetingArrayList.get(i).getDate());
            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTime(mMeetingArrayList.get(i).getStartTime());
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(mMeetingArrayList.get(i).getEndTime());
            if (selectHallSpinner.equals(mMeetingArrayList.get(i).getHallLetter()) &&
                    calDate.equals(calendarDate) &&
                    (calStart.equals(calendarStart) || calStart.before(calendarEnd)) &&
                    (calEnd.equals(calendarEnd) || calEnd.after(calendarStart))) {
                Toast.makeText(AddMeetingActivity.this, "Une réunion est déjà prévue sur ce créneau horaire dans cette salle. Veuillez ressaisir votre réunion.", Toast.LENGTH_LONG).show();
                return;
            }
        }
                mMeetingApiService.createMeeting(mMeeting);
                finish();
    }
}