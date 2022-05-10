package fr.vegeto52.mareu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import android.util.Log;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import java.util.List;

import fr.vegeto52.mareu.DI.DI;
import fr.vegeto52.mareu.model.Meeting;
import fr.vegeto52.mareu.service.DummyMeetingApiService;
import fr.vegeto52.mareu.service.DummyMeetingGenerator;
import fr.vegeto52.mareu.service.MeetingApiService;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class MareuTest {

    private MeetingApiService mService;

    @Before
    public void setUp() {
            mService = DI.getMeetingApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetings = mService.getMeeting();
        List<Meeting> expectedMeeting = DummyMeetingGenerator.DUMMY_MEETING;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeeting.toArray()));
    }

    @Test
    public void addMeetingWithSuccess() {
        int sizeMeetingBefore = mService.getMeeting().size();
        Meeting meeting = new Meeting("B", "Conseil Administratif", "bill@gmail.com, eva@gmail.com", new Date(1647216000000L), new Date(50400000), new Date(55800000));
        mService.createMeeting(meeting);
        int sizeMeetingAfter = mService.getMeeting().size();
        mService.deleteMeeting(meeting);
        assertEquals(sizeMeetingBefore+1, sizeMeetingAfter);
    }

    @Test
    public void removeMeetingWithSuccess() {
        int sizeMeetingBefore = mService.getMeeting().size();
        mService.deleteMeeting(mService.getMeeting().get(0));
        int sizeMeetingAfter = mService.getMeeting().size();
        assertEquals(sizeMeetingBefore-1, sizeMeetingAfter);
    }
}