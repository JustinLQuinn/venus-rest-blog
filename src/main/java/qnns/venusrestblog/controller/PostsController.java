package qnns.venusrestblog.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import qnns.venusrestblog.data.*;
import qnns.venusrestblog.repository.CategoriesRepository;
import qnns.venusrestblog.repository.PostsRepository;
import qnns.venusrestblog.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
    private PostsRepository postsRepository;
    private UserRepository usersRepository;
    private CategoriesRepository categoriesRepository;
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

        Category cat1 = categoriesRepository.findById(1L).get();
        Category cat2 = categoriesRepository.findById(2L).get();
        newPost.setCategories(new ArrayList<>());
        newPost.getCategories().add(cat1);
        newPost.getCategories().add(cat2);

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

        Optional<Post> originalPost = postsRepository.findById(id);
        if(originalPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post "+ id + " not found");
        }
//        BeanUtils.copyProperties(updatedPost, originalPost.get(), FieldHelper.getNullPropertyNames(updatedPost));

        postsRepository.save(originalPost.get());
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