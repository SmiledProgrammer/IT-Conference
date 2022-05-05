package pl.sii.itconference.data;

import lombok.Getter;
import pl.sii.itconference.enums.ContentTrack;

import java.time.LocalTime;

@Getter
public class Lecture {

    public static final int MAX_LISTENERS_COUNT = 5;

    private final String title;
    private final ContentTrack contentTrack;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private int listenersCount;

    public Lecture(String title, ContentTrack contentTrack, LocalTime startTime) {
        this.title = title;
        this.contentTrack = contentTrack;
        this.listenersCount = 0;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(105);
    }

    public boolean addListener() {
        if (isFull()) {
            return false;
        }
        listenersCount++;
        return true;
    }

    public boolean isFull() {
        return listenersCount == MAX_LISTENERS_COUNT;
    }
}
