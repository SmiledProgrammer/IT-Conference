package pl.sii.itconference.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sii.itconference.entity.Participation;
import pl.sii.itconference.entity.User;

import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findByLectureIdAndUser(Integer lectureId, User user);
}
