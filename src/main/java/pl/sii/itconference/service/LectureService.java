package pl.sii.itconference.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sii.itconference.data.Lecture;
import pl.sii.itconference.enums.ContentTrack;
import pl.sii.itconference.exception.ResourceNotFoundException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LectureService {

    private final List<Lecture> lectures;

    public LectureService() {
        lectures = new ArrayList<>();
        lectures.add(new Lecture("JSON Web Tokens", ContentTrack.SECURITY, LocalTime.of(10, 0)));
        lectures.add(new Lecture("Advanced XSS attacks", ContentTrack.SECURITY, LocalTime.of(12, 0)));
        lectures.add(new Lecture("Safe C programming", ContentTrack.SECURITY, LocalTime.of(14, 0)));
        lectures.add(new Lecture("AWS Introduction", ContentTrack.CLOUD, LocalTime.of(10, 0)));
        lectures.add(new Lecture("To Azure or not to Azure?", ContentTrack.CLOUD, LocalTime.of(12, 0)));
        lectures.add(new Lecture("But why Cloud?", ContentTrack.CLOUD, LocalTime.of(14, 0)));
        lectures.add(new Lecture("Colors matter", ContentTrack.UX, LocalTime.of(10, 0)));
        lectures.add(new Lecture("Eye catching GUI", ContentTrack.UX, LocalTime.of(12, 0)));
        lectures.add(new Lecture("Is UX really that important?", ContentTrack.UX, LocalTime.of(14, 0)));
    }

    public List<Lecture> getLectures() {
        log.info("getLectures() called successfully.");
        return lectures;
    }

    public Lecture getLecture(int id) {
        Lecture lecture = lectures.get(id);
        if (lecture == null) {
            log.error("getLecture() called but couldn't find lecture with ID of {}.", id);
            throw new ResourceNotFoundException("Couldn't find lecture.");
        }
        log.info("getLecture() called successfully.");
        return lecture;
    }
}
