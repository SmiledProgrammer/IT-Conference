package pl.sii.itconference.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sii.itconference.data.Lecture;
import pl.sii.itconference.entity.User;
import pl.sii.itconference.enums.ContentTrack;
import pl.sii.itconference.exception.BadRequestException;
import pl.sii.itconference.exception.ResourceNotFoundException;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LectureService {

    private final UserService userService;
    private final List<Lecture> lecturesList;

    public LectureService(UserService userService) {
        this.userService = userService;
        this.lecturesList = new ArrayList<>();
        fillLecturesList();
    }

    private void fillLecturesList() {
        lecturesList.add(new Lecture("JSON Web Tokens", ContentTrack.SECURITY, LocalTime.of(10, 0)));
        lecturesList.add(new Lecture("Advanced XSS attacks", ContentTrack.SECURITY, LocalTime.of(12, 0)));
        lecturesList.add(new Lecture("Safe C programming", ContentTrack.SECURITY, LocalTime.of(14, 0)));
        lecturesList.add(new Lecture("AWS Introduction", ContentTrack.CLOUD, LocalTime.of(10, 0)));
        lecturesList.add(new Lecture("To Azure or not to Azure?", ContentTrack.CLOUD, LocalTime.of(12, 0)));
        lecturesList.add(new Lecture("But why Cloud?", ContentTrack.CLOUD, LocalTime.of(14, 0)));
        lecturesList.add(new Lecture("Colors matter", ContentTrack.UX, LocalTime.of(10, 0)));
        lecturesList.add(new Lecture("Eye catching GUI", ContentTrack.UX, LocalTime.of(12, 0)));
        lecturesList.add(new Lecture("Is UX really that important?", ContentTrack.UX, LocalTime.of(14, 0)));
    }

    public List<Lecture> getLectures() {
        log.info("getLectures() called successfully.");
        return lecturesList;
    }

    public List<Lecture> getUsersLectures(@NotBlank String username) {
        User user = userService.getUserByUsername(username);
        return user.getParticipations().stream()
                .map(p -> lecturesList.get(p.getLectureId()))
                .toList();
    }

    public void addListenerToLecture(int lectureId) {
        Lecture lecture = lecturesList.get(lectureId);
        if (lecture == null) {
            log.error("addListenerToLecture() called but couldn't find lecture with ID of {}.", lecture);
            throw new ResourceNotFoundException("Couldn't find lecture.");
        }
        boolean success = lecture.addListener();
        if (!success) {
            log.info("addListenerToLecture() called but lecture was already full.");
            throw new BadRequestException("The lecture is already full.");
        }
        log.info("addListenerToLecture() called successfully.");
    }
}
