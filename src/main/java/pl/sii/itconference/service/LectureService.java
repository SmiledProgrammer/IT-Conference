package pl.sii.itconference.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.sii.itconference.data.Lecture;
import pl.sii.itconference.entity.User;
import pl.sii.itconference.enums.ContentTrack;
import pl.sii.itconference.exception.BadRequestException;
import pl.sii.itconference.exception.ResourceNotFoundException;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Validated
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
        return getLectures(null);
    }

    public List<Lecture> getLectures(String username) {
        if (username == null) {
            log.info("getLectures() called successfully.");
            return lecturesList;
        } else {
            User user = userService.getUserByUsername(username);
            List<Lecture> userLectures = user.getParticipations().stream()
                    .map(p -> getNullableLecture(p.getLectureId()))
                    .toList();
            log.info("getLectures() called successfully.");
            return userLectures;
        }
    }

    public void addListenerToLecture(@NotNull Long lectureId) {
        Lecture lecture = getNullableLecture(lectureId);
        if (lecture == null) {
            log.error("addListenerToLecture() called but couldn't find lecture with ID of {}.", lectureId);
            throw new ResourceNotFoundException("Couldn't find lecture with this ID.");
        }
        boolean success = lecture.addListener();
        if (!success) {
            log.error("addListenerToLecture() called but lecture was already full.");
            throw new BadRequestException("The lecture is already full.");
        }
        log.info("addListenerToLecture() called successfully.");
    }

    public void removeListenerFromLecture(@NotNull Long lectureId) {
        Lecture lecture = getNullableLecture(lectureId);
        if (lecture == null) {
            log.error("removeListenerFromLecture() called but couldn't find lecture with ID of {}.", lectureId);
            throw new ResourceNotFoundException("Couldn't find lecture with this ID.");
        }
        boolean success = lecture.removeListener();
        if (!success) {
            log.error("removeListenerFromLecture() called but lecture had no listeners.");
            throw new BadRequestException("The lecture has no listeners.");
        }
        log.info("removeListenerFromLecture() called successfully.");
    }

    public Lecture getLecture(@NotNull Long id) {
        Lecture lecture = getNullableLecture(id);
        if (lecture == null) {
            log.error("getLecture() called but couldn't find lecture with ID of {}.", id);
            throw new ResourceNotFoundException("Couldn't find lecture with this ID.");
        }
        log.info("getLecture() called successfully.");
        return lecture;
    }

    private Lecture getNullableLecture(long id) {
        return lecturesList.stream()
                .filter(l -> l.getId() == id)
                .findFirst().orElse(null);
    }
}
