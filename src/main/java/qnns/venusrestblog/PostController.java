package qnns.venusrestblog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qnns.venusrestblog.data.Post;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/post", headers = "Accept=application/json")
public class PostController {

    @GetMapping("/{id}")
    public Post fetchPostsById(@PathVariable long id){
        //TODO: get some post and return them
        switch((int) id){
            case 1:
                return new Post(1L, "Post1", "this is a post");
            case 2:
                return new Post(2L, "Post2", "this is a Post also");
            default:
                throw new RuntimeException("Hey man, not found!");
        }
    }
}
