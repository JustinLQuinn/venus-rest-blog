package qnns.venusrestblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qnns.venusrestblog.data.Post;

public interface PostsRepository extends JpaRepository<Post, Long> {

}
