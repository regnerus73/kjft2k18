package hello.cup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CupRepository extends JpaRepository<Cup, Long> {

	List<Cup> findByNameStartsWithIgnoreCase(String name);
}
