package pl.sii.itconference.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sii.itconference.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
