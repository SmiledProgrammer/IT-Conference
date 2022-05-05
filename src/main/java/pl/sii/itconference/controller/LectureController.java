package pl.sii.itconference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sii.itconference.data.Lecture;
import pl.sii.itconference.service.LectureService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/lectures")
    public ResponseEntity<List<Lecture>> getLectures() {
        return new ResponseEntity<>(lectureService.getLectures(), HttpStatus.OK);
    }

    @GetMapping("/users-lectures")
    public ResponseEntity<List<Lecture>> getUsersLectures(@RequestParam("username") String username) {
        return new ResponseEntity<>(lectureService.getUsersLectures(username), HttpStatus.OK);
    }
}
