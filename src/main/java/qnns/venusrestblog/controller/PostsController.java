package qnns.venusrestblog.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import qnns.venusrestblog.data.Category;
import qnns.venusrestblog.data.Post;
import qnns.venusrestblog.data.PostsRepository;
import qnns.venusrestblog.data.UserRepository;
import qnns.venusrestblog.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
    private PostsRepository postsRepository;
    private UserRepository usersRepository;

    @GetMapping("")
//    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Post> fetchPosts() {
        return postsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Post> fetchPostById(@PathVariable long id) {
//        // search through the list of posts
//        // and return the post that matches the given id
//        Post post = findPostById(id);
//        if(post == null) {
//            // what to do if we don't find it
//            throw new RuntimeException("I don't know what I am doing");
//        }
//
//        // we found the post so just return it
//        return post;
        return postsRepository.findById(id);
    }

    @PostMapping("")
    public void createPost(@RequestBody Post newPost) {
        //use fake author
        User author = usersRepository.findById(1L).get();
        newPost.setAuthor(author);

        Category cat1 =
//        Category cat1 = new Category(1L, "bunnies", null);
//        Category cat2 = new Category(2L, "Dog", null);
//        newPost.setCategories(new ArrayList<>());
//        newPost.getCategories().add(cat1);
//        newPost.getCategories().add(cat2);

        postsRepository.save(newPost);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable long id) {
        postsRepository.deleteById(id);
        // what to do if we don't find it
        throw new RuntimeException("Post not found");
    }

    @PutMapping("/{id}")
    public void updatePost(@RequestBody Post updatedPost, @PathVariable long id) {
        updatedPost.setId(id);
        postsRepository.save(updatedPost);
        // find the post to update in the posts list
//        Post post = findPostById(id);
//        if(post == null) {
//            System.out.println("Post not found");
//        } else {
//            if(updatedPost.getTitle() != null) {
//                post.setTitle(updatedPost.getTitle());
//            }
//            if(updatedPost.getContent() != null) {
//                post.setContent(updatedPost.getContent());
//            }
//            return;
//        }
//        throw new RuntimeException("Post not found");
    }
}