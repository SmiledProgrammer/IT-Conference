package pl.sii.itconference.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sii.itconference.data.Lecture;
import pl.sii.itconference.dto.ContentTrackSummaryDto;
import pl.sii.itconference.dto.LectureSummaryDto;
import pl.sii.itconference.enums.ContentTrack;
import pl.sii.itconference.utils.FloatUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryService {

    private final LectureService lectureService;

    public List<LectureSummaryDto> getLecturesSummary() {
        List<LectureSummaryDto> summary = lectureService.getLectures().stream()
                .map(this::mapLectureToSummaryDto).toList();
        log.info("getLecturesSummary() called successfully.");
        return summary;
    }

    public List<ContentTrackSummaryDto> getContentTracksSummary() {
        List<ContentTrackSummaryDto> summary = new ArrayList<>(ContentTrack.values().length);
        for (ContentTrack contentTrack : ContentTrack.values()) {
            List<Lecture> trackLectures = lectureService.getLectures().stream()
                    .filter(l -> l.getContentTrack() == contentTrack).toList();
            int listenersCount = trackLectures.stream()
                    .mapToInt(Lecture::getListenersCount)
                    .sum();
            int maxListenersCount = Lecture.MAX_LISTENERS_COUNT * trackLectures.size();
            float fillPercent = FloatUtils.round((float) listenersCount / maxListenersCount * 100f, 2);
            summary.add(new ContentTrackSummaryDto(contentTrack, fillPercent));
        }
        log.info("getContentTracksSummary() called successfully.");
        return summary;
    }

    private LectureSummaryDto mapLectureToSummaryDto(Lecture lecture) {
        float fillPercent = FloatUtils.round((float) lecture.getListenersCount() / Lecture.MAX_LISTENERS_COUNT * 100f, 2);
        return new LectureSummaryDto(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getContentTrack(),
                fillPercent
        );
    }
}
