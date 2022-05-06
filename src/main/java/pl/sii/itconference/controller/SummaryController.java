package pl.sii.itconference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sii.itconference.dto.ContentTrackSummaryDto;
import pl.sii.itconference.dto.LectureSummaryDto;
import pl.sii.itconference.service.SummaryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping("/summary/lectures")
    public ResponseEntity<List<LectureSummaryDto>> getLecturesSummary() {
        return new ResponseEntity<>(summaryService.getLecturesSummary(), HttpStatus.OK);
    }

    @GetMapping("/summary/content-tracks")
    public ResponseEntity<List<ContentTrackSummaryDto>> getContentTracksSummary() {
        return new ResponseEntity<>(summaryService.getContentTracksSummary(), HttpStatus.OK);
    }
}
