package qnns.venusrestblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qnns.venusrestblog.data.User;

public interface UserRepository extends JpaRepository<User, Long> {
User findByUsername(String userName);
User findByEmail(String email);
}
