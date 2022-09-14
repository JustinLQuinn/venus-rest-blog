package qnns.venusrestblog.data;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Post, Long> {

}
