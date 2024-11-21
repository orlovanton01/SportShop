package com.example.SportShop.service;

import com.example.SportShop.model.Post;
import com.example.SportShop.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post getPostById(Integer id) {
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.isPresent() ? postOptional.get() : null;
    }
}
