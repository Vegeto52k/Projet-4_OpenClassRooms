package fr.vegeto52.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import fr.vegeto52.mareu.DI.DI;
import fr.vegeto52.mareu.R;
import fr.vegeto52.mareu.databinding.ActivityAddMeetingBinding;
import fr.vegeto52.mareu.model.Meeting;
import fr.vegeto52.mareu.service.MeetingApiService;

public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityAddMeetingBinding mBinding;

    private MeetingApiService mMeetingApiService = DI.getMeetingApiService();

    TextInputEditText date;
    TextInputEditText startMeeting;
    TextInputEditText endMeeting;
    ImageButton backButton;
    Button addMeetingButton;
    Spinner selectHallSpinner;

    int setHour, setMinute;
    String hallSpinnerSelected, dateText, startMeetingText, endMeetingText;
    Date finalDateData, finalStartHourData, finalEndHourData;
    Meeting mMeeting;
    private ArrayList<Meeting> mMeetingArrayList;
    private ArrayList<String> hallSpinnerAvailable = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initUI();
        setDate();
        selectStartHour();
        selectEndHour();
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
                        checkSpinner();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void selectStartHour() {
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
                        checkSpinner();
                    }
                }, 12, 0, true);
                timePickerDialog.updateTime(setHour, setMinute);
                timePickerDialog.show();
            }
        });
    }

    private void selectEndHour() {
        endMeeting = findViewById(R.id.edit_end_hour);
        endMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        setHour = hour;
                        setMinute = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, setHour, setMinute);
                        endMeeting.setText(DateFormat.format("HH:mm", calendar));
                        checkSpinner();
                    }
                }, 12, 0, true);
                timePickerDialog.updateTime(setHour, setMinute);
                timePickerDialog.show();
            }
        });
    }

    private void backButton() {
        backButton = findViewById(R.id.add_return);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void spinnerHall() {
        selectHallSpinner = findViewById(R.id.spinner_hall);
        createHallList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, hallSpinnerAvailable);
        this.selectHallSpinner.setAdapter(adapter);
        selectHallSpinner.setEnabled(false);
    }

    private void checkSpinner() {

        selectHallSpinner = findViewById(R.id.spinner_hall);
        date = findViewById(R.id.edit_date);
        startMeeting = findViewById(R.id.edit_start_hour);
        endMeeting = findViewById(R.id.edit_end_hour);

        convertEditTextToDate();


        if (dateText.isEmpty() || startMeetingText.isEmpty() || endMeetingText.isEmpty()) {
        } else {
            selectHallSpinner.setEnabled(true);
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(finalDateData);
            Calendar calStart = Calendar.getInstance();
            calStart.setTime(finalStartHourData);
            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(finalEndHourData);

            hallSpinnerAvailable.clear();
            createHallList();

            for (int i = 0; i < mMeetingArrayList.size(); i++) {
                Calendar calendarDate = Calendar.getInstance();
                calendarDate.setTime(mMeetingArrayList.get(i).getDate());
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTime(mMeetingArrayList.get(i).getStartTime());
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTime(mMeetingArrayList.get(i).getEndTime());
                if (calDate.equals(calendarDate) &&
                        (calStart.equals(calendarStart) || calStart.before(calendarEnd)) &&
                        (calEnd.equals(calendarEnd) || calEnd.after(calendarStart))) {
                    hallSpinnerAvailable.remove(mMeetingArrayList.get(i).getHallLetter());
                }
            }
            this.selectHallSpinner.setAdapter(adapter);
            selectHallSpinner.setEnabled(true);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        hallSpinnerSelected = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void createHallList() {
        hallSpinnerAvailable.add("A");
        hallSpinnerAvailable.add("B");
        hallSpinnerAvailable.add("C");
        hallSpinnerAvailable.add("D");
        hallSpinnerAvailable.add("E");
        hallSpinnerAvailable.add("F");
        hallSpinnerAvailable.add("G");
        hallSpinnerAvailable.add("H");
        hallSpinnerAvailable.add("I");
        hallSpinnerAvailable.add("J");
    }

    private void addMeeting() {
        addMeetingButton = findViewById(R.id.add_button_meeting);

        addMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String object = mBinding.editObject.getText().toString();
                dateText = mBinding.editDate.getText().toString();
                startMeetingText = mBinding.editStartHour.getText().toString();
                endMeetingText = mBinding.editEndHour.getText().toString();
                String mailPeople = mBinding.editMailPeople.getText().toString();
                hallSpinnerSelected = mBinding.spinnerHall.getSelectedItem().toString();

                convertEditTextToDate();

                if (object.isEmpty()) {
                    mBinding.editObject.setError("Veuillez ajouter un objet à la réunion");
                    return;
                } else if (dateText.isEmpty()) {
                    mBinding.editDate.setError("Veuillez ajouter une date à la réunion");
                    return;
                } else if (startMeetingText.isEmpty()) {
                    mBinding.editStartHour.setError("Veuillez ajouter une heure de début de la réunion");
                    return;
                } else if (endMeetingText.isEmpty()) {
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
        dateText = mBinding.editDate.getText().toString();
        startMeetingText = mBinding.editStartHour.getText().toString();
        endMeetingText = mBinding.editEndHour.getText().toString();

        Date dateData = new Date();
        try {
            dateData = new SimpleDateFormat("dd/MM/yyyy").parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finalDateData = dateData;

        Date startHourData = new Date();
        try {
            startHourData = new SimpleDateFormat("HH:mm").parse(startMeetingText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finalStartHourData = startHourData;

        Date endHourData = new Date();
        try {
            endHourData = new SimpleDateFormat("HH:mm").parse(endMeetingText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finalEndHourData = endHourData;
    }

    private void checkSlotAvailableAndCreateMeeting() {
        hallSpinnerSelected = mBinding.spinnerHall.getSelectedItem().toString();

        Calendar calDate = Calendar.getInstance();
        calDate.setTime(finalDateData);
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(finalStartHourData);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(finalEndHourData);
        Date calendar = Calendar.getInstance().getTime();

        if (calStart.after(calEnd)) {
            Toast.makeText(AddMeetingActivity.this, "L'heure de début de la réunion ne peut pas être après l'heure de fin. Veuillez rentrer une horaire valide.", Toast.LENGTH_LONG).show();
            return;
        }
        if (calendar.after(finalDateData)) {
            Toast.makeText(AddMeetingActivity.this, "Vous ne pouvez pas entrer une date antérieure à la date d'aujourd'hui. Veuillez entrer une date valide.", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < mMeetingArrayList.size(); i++) {
            Calendar calendarDate = Calendar.getInstance();
            calendarDate.setTime(mMeetingArrayList.get(i).getDate());
            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTime(mMeetingArrayList.get(i).getStartTime());
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(mMeetingArrayList.get(i).getEndTime());
            if (hallSpinnerSelected.equals(mMeetingArrayList.get(i).getHallLetter()) &&
                    calDate.equals(calendarDate) &&
                    (calStart.equals(calendarStart) || calStart.before(calendarEnd)) &&
                    (calEnd.equals(calendarEnd) || calEnd.after(calendarStart))) {
                Toast.makeText(AddMeetingActivity.this, "Une réunion est déjà prévue sur ce créneau horaire dans cette salle. Veuillez ressaisir votre réunion.", Toast.LENGTH_LONG).show();
                return;
            }
        }
        mMeetingApiService.createMeeting(mMeeting);
        Intent intent = new Intent(AddMeetingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}