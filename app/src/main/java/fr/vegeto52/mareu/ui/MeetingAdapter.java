package fr.vegeto52.mareu.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.timepicker.MaterialTimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.vegeto52.mareu.DI.DI;
import fr.vegeto52.mareu.R;
import fr.vegeto52.mareu.model.Meeting;
import fr.vegeto52.mareu.service.MeetingApiService;


/**
 * Created by Vegeto52-PC on 08/03/2022.
 */
public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    ArrayList<Meeting> mMeetings;

    public MeetingAdapter(ArrayList<Meeting> meetings) {
        this.mMeetings = meetings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.displayMeeting(mMeetings.get(position));


    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public Meeting getMeeting(int position){
        return this.mMeetings.get(position);
    }


    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private MeetingAdapter mAdapter;

        public final ImageView circle;
        public final TextView letter;
        public final TextView infos;
        public final TextView date;
        public final TextView time;
        public final TextView people;
        public final ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circle = itemView.findViewById(R.id.meeting_hall_circle);
            letter = itemView.findViewById(R.id.meeting_hall_letter);
            infos = itemView.findViewById(R.id.meeting_infos);
            date = itemView.findViewById(R.id.meeting_date);
            time = itemView.findViewById(R.id.meeting_time);
            people = itemView.findViewById(R.id.meeting_people);
            delete = itemView.findViewById(R.id.meeting_delete_button);

            delete.setOnClickListener(view -> {
                Meeting meeting = mAdapter.getMeeting(getLayoutPosition());
                mAdapter.mMeetingApiService.deleteMeeting(meeting);
                mAdapter.mMeetings.remove(getLayoutPosition());
                mAdapter.notifyItemRemoved(getLayoutPosition());
                Log.d("Test", "J'ai delete " + mAdapter.mMeetings.size() + " " + mAdapter.mMeetingApiService.getMeeting().size());
            });
        }

        public ViewHolder linkAdapter(MeetingAdapter adapter){
            this.mAdapter = adapter;
            return this;
        }

        public void displayMeeting(Meeting meeting) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");

            letter.setText(meeting.getHallLetter());
            infos.setText(meeting.getInfos());
            people.setText(meeting.getPeople());
            date.setText(simpleDateFormat.format(meeting.getDate()));
            time.setText(simpleDateFormat1.format(meeting.getStartTime()));

        }

 //       public void deleteMeeting(int position){
 //           Meeting atRemove = mAdapter.getMeeting(position);
 //           mAdapter.mMeetingApiService.deleteMeeting(atRemove);
 //       }

    }

}
