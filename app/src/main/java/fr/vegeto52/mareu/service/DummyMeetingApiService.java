package fr.vegeto52.mareu.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.vegeto52.mareu.model.Meeting;

/**
 * Created by Vegeto52-PC on 10/03/2022.
 */
public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> mMeetings = DummyMeetingGenerator.generateMeeting();


    @Override
    public List<Meeting> getMeeting() {
        return mMeetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public ArrayList<Meeting> getMeetingFilteredByDate(Date date) {
        ArrayList<Meeting> result = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        for (int i = 0; i < mMeetings.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(mMeetings.get(i).getDate());
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) result.add(mMeetings.get(i));
        }
        return result;
    }

    @Override
    public ArrayList<Meeting> getMeetingFilteredByHall(String hallLetter) {
        ArrayList<Meeting> result = new ArrayList<>();

        for (int i = 0; i < mMeetings.size(); i++) {
            if (mMeetings.get(i).getHallLetter().equals(hallLetter)) {
                result.add(mMeetings.get(i));
            }
        }
        return result;
    }
}
