package pl.sii.itconference.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sii.itconference.dto.CreateParticipationDto;
import pl.sii.itconference.dto.ParticipationDto;
import pl.sii.itconference.entity.Participation;
import pl.sii.itconference.entity.User;
import pl.sii.itconference.exception.BadRequestException;
import pl.sii.itconference.repo.ParticipationRepository;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final UserService userService;
    private final LectureService lectureService;
    private final ParticipationRepository participationRepository;

    public ParticipationDto createParticipation(@Valid CreateParticipationDto dto) {
        User user = userService.forceGetUser(dto.getUsername(), dto.getEmail());
        Optional<Participation> existingParticipation = participationRepository.findByLectureIdAndUser(dto.getLectureId(), user);
        if (existingParticipation.isPresent()) {
            log.error("createParticipation() called but user with username {} is already signed up for the lecture with ID of {}.",
                    dto.getUsername(), dto.getLectureId());
            throw new BadRequestException("User is already signed up for this lecture.");
        }
        if (listenerSignedUpForOtherLecture(user, dto.getLectureId())) {
            log.error("createParticipation() called but user with username {} is already signed up for other lecture at this time.",
                    dto.getUsername());
            throw new BadRequestException("User is already signed up for other lecture at this time.");
        }
        lectureService.addListenerToLecture(dto.getLectureId());
        Participation newParticipation = new Participation(user, dto.getLectureId());
        Participation participationRecord = participationRepository.save(newParticipation);
        log.info("createParticipation() called successfully.");
        return mapEntityToDto(participationRecord);
    }

    public void deleteParticipation(@Valid ParticipationDto dto) {
        User user = userService.getUserByUsername(dto.getUsername());
        lectureService.removeListenerFromLecture(dto.getLectureId());
        participationRepository.deleteByLectureIdAndUser(dto.getLectureId(), user);
        log.info("deleteParticipation() called successfully.");
    }

    private boolean listenerSignedUpForOtherLecture(User user, long lectureId) {
        LocalTime lectureTime = lectureService.getLecture(lectureId).getStartTime();
        return user.getParticipations().stream()
                .anyMatch(p -> lectureService.getLecture(p.getLectureId())
                        .getStartTime().equals(lectureTime));
    }

    private ParticipationDto mapEntityToDto(Participation entity) {
        return new ParticipationDto(entity.getUser().getUsername(), entity.getLectureId());
    }
}
