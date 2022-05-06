package fr.vegeto52.mareu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.vegeto52.mareu.databinding.ActivityAddMeetingBinding;
import fr.vegeto52.mareu.databinding.ActivityDetailMeetingBinding;
import fr.vegeto52.mareu.model.Meeting;
import fr.vegeto52.mareu.ui.MainActivity;
import fr.vegeto52.mareu.ui.SpinnerFilteredActivity;

public class DetailMeetingActivity extends AppCompatActivity {

    ActivityDetailMeetingBinding mBinding;

    ImageButton backButton;

    TextView object;
    TextView hall;
    TextView date;
    TextView start;
    TextView end;
    TextView people;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        backButton();
        detailCompleted();
    }

    private void initUI() {
        mBinding = ActivityDetailMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
    }

    private void detailCompleted(){

        Intent intent = getIntent();
        Meeting meeting = intent.getParcelableExtra("Meeting");

        object = findViewById(R.id.profil_object_view);
        hall = findViewById(R.id.profil_hall_view);
        date = findViewById(R.id.profil_date_view);
        start = findViewById(R.id.profil_start_view);
        end = findViewById(R.id.profil_end_view);
        people = findViewById(R.id.profil_people_view);

        String object1 = meeting.getInfos();
        object.setText(object1);

        String hall1 = meeting.getHallLetter();
        hall.setText(hall1);

        Date today = meeting.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date1 = formatter.format(today);
        date.setText(date1);

        Date startHour = meeting.getStartTime();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        String start1 = formatter1.format(startHour);
        start.setText(start1);

        Date endHour = meeting.getEndTime();
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        String end1 = formatter2.format(endHour);
        end.setText(end1);

        String people1 = meeting.getPeople();
        people.setText(people1);

    }

    private void backButton(){
        backButton = findViewById(R.id.profil_return);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailMeetingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}