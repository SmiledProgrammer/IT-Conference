package pl.sii.itconference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.sii.itconference.dto.CreateParticipationDto;
import pl.sii.itconference.dto.ParticipationDto;
import pl.sii.itconference.service.ParticipationService;

@RestController
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping("/participations")
    public ResponseEntity<ParticipationDto> createParticipation(@RequestBody CreateParticipationDto participationDto) {
        return new ResponseEntity<>(participationService.createParticipation(participationDto), HttpStatus.CREATED);
    }
}
