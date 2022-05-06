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
            log.info("createParticipation() called but user with username {} is already signed up for the lecture with ID of {}.",
                    dto.getUsername(), dto.getLectureId());
            throw new BadRequestException("User is already signed up for this lecture.");
        }
        lectureService.addListenerToLecture(dto.getLectureId());
        Participation newParticipation = new Participation(user, dto.getLectureId());
        Participation participationRecord = participationRepository.save(newParticipation);
        log.info("createParticipation() called successfully.");
        return mapEntityToDto(participationRecord);
    }

    private ParticipationDto mapEntityToDto(Participation entity) {
        return new ParticipationDto(entity.getUser().getUsername(), entity.getLectureId());
    }
}