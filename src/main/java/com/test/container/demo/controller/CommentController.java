package com.test.container.demo.controller;

import com.test.container.demo.model.Comment;
import com.test.container.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getAllComments(@PathVariable Long postId) {
        return commentRepository.findAll(); // You can filter by postId
    }

    @PostMapping
    public Comment createComment(@PathVariable Long postId, @RequestBody Comment comment) {
        // Find the post by postId, set it to comment, then save
        // postRepository.findById(postId)... comment.setPost(post)
        return commentRepository.save(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return new ResponseEntity(HttpStatus.OK);
                })
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}