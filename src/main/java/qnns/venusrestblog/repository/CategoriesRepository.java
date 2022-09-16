package qnns.venusrestblog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import qnns.venusrestblog.data.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
