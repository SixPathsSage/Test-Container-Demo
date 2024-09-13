package com.test.container.demo.controller;

import com.test.container.demo.model.Post;
import com.test.container.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    return ResponseEntity.ok(postRepository.save(post));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(post -> {
                    postRepository.delete(post);
                    return new ResponseEntity(HttpStatus.OK);
                })
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<Long> like(@PathVariable Long postId) {
        return postRepository.findById(postId)
                .map(post -> {
                    Long likes = post.getLikes();
                    post.setLikes(likes+1);
                    return post;
                })
                .map(post -> postRepository.save(post))
                .map(post -> new ResponseEntity(Collections.singletonMap("likes", post.getLikes()), HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{postId}/unlike")
    public ResponseEntity<Long> unlike(@PathVariable Long postId) {
        return postRepository.findById(postId)
                .map(post -> {
                    Long likes = post.getLikes();
                    post.setLikes(likes-1);
                    return post;
                })
                .map(post -> postRepository.save(post))
                .map(post -> new ResponseEntity(Collections.singletonMap("likes", post.getLikes()), HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
