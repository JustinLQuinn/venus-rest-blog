package qnns.venusrestblog.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qnns.venusrestblog.data.Category;
import qnns.venusrestblog.data.Post;

import java.util.ArrayList;

@RestController
@RequestMapping(produces = "application/json", value = "/api/categories")
public class CategoriesController {
    @GetMapping("")
    private Category getPostsByCategory(@RequestParam String categoryName){
Category category = new Category(1L, categoryName, null);
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post(13L, "Ough!!!", "Today we had a horrible time", null, null));
        posts.add(new Post(23L, "Ouch", "My cat scratched me today", null, null));
        posts.add(new Post(46L, "Stop Scratching", "How do you get a cat to stop scratching on everything?", null, null));
        posts.add(new Post(32L, "Lost Cat", "Have y'all seen my cat, she ran away today....", null, null));
        category.setPosts(posts);
        return category;
    }
}
