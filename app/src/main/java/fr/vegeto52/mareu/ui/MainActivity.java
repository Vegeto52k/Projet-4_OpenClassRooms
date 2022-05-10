package fr.vegeto52.mareu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.vegeto52.mareu.DI.DI;
import fr.vegeto52.mareu.R;
import fr.vegeto52.mareu.databinding.ActivityMainBinding;
import fr.vegeto52.mareu.model.Meeting;
import fr.vegeto52.mareu.service.DummyMeetingGenerator;
import fr.vegeto52.mareu.service.ItemClickSupport;
import fr.vegeto52.mareu.service.MeetingApiService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mBinding;
    private ArrayList<Meeting> mMeetingArrayList;
    private MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initData();
        }
        if (savedInstanceState != null) {
            mMeetingArrayList = new ArrayList<>(DummyMeetingGenerator.DUMMY_MEETING);
        }
        initUI();
        openAddActivity();
        configureOnClickRecyclerView();
    }

    private void openAddActivity() {
        addButton = findViewById(R.id.add_meeting);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerview.setLayoutManager(layoutManager);
        MeetingAdapter meetingAdapter = new MeetingAdapter(mMeetingArrayList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.recyclerview.getContext(), layoutManager.getOrientation());
        mBinding.recyclerview.addItemDecoration(dividerItemDecoration);
        mBinding.recyclerview.setAdapter(meetingAdapter);
    }

    private void initData() {
        mMeetingArrayList = new ArrayList<>(mMeetingApiService.getMeeting());
    }

    private void initUI() {
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_date:
                dateDialog();
                return true;
            case R.id.filter_hall:
                hallDialog();
                return true;
            case R.id.filter_reset:
                resetFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mBinding.recyclerview, R.layout.activity_detail_meeting)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(MainActivity.this, DetailMeetingActivity.class);
                        intent.putExtra("Meeting", mMeetingArrayList.get(position));
                        startActivity(intent);
                    }
                });
    }

    private void resetFilter() {
        mMeetingArrayList.clear();
        mMeetingArrayList.addAll(mMeetingApiService.getMeeting());
        mBinding.recyclerview.getAdapter().notifyDataSetChanged();
    }

    private void hallDialog() {
        Intent intent = new Intent(MainActivity.this, SpinnerFilteredActivity.class);
        startActivity(intent);
    }

    private void returnResultHallDialog() {
        Intent intent = getIntent();
        if (intent != null) {
            String hallselect = "";
            if (intent.hasExtra("hallselect")) {
                hallselect = intent.getStringExtra("hallselect");
                Meeting meeting = new Meeting("B", "Conseil Administratif", "bill@gmail.com, eva@gmail.com", new Date(1647216000000L), new Date(50400000), new Date(55800000));
                meeting.setHallLetter(hallselect);
                mMeetingArrayList.clear();
                mMeetingArrayList.addAll(mMeetingApiService.getMeetingFilteredByHall(meeting.getHallLetter()));
                mBinding.recyclerview.getAdapter().notifyDataSetChanged();
            }
        }
    }

    private void dateDialog() {
        Calendar calendar = Calendar.getInstance();
        final int selectedYear = calendar.get(Calendar.YEAR);
        final int selectedMonth = calendar.get(Calendar.MONTH);
        final int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance();
                cal.set(i, i1, i2);
                mMeetingArrayList.clear();
                mMeetingArrayList.addAll(mMeetingApiService.getMeetingFilteredByDate(cal.getTime()));
                mBinding.recyclerview.getAdapter().notifyDataSetChanged();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

        datePickerDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        returnResultHallDialog();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}