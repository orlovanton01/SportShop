package SportShop.service;

import SportShop.model.Post;
import SportShop.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post getPostById(Integer id) {
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.isPresent() ? postOptional.get() : null;
    }

    public Post addNewPost() {
        Random rand = new Random();
        Post newPost = new Post();
        if (postRepository.findAll().isEmpty()) {
            newPost.setPId(1);
            newPost.setPost("director");
            newPost.setSalary(65000);
        }
        else
            switch (rand.nextInt(3) + 2) {
                case 2:
                    newPost.setPId(2);
                    newPost.setPost("consultant");
                    newPost.setSalary(25000);
                    break;
                case 3:
                    newPost.setPId(3);
                    newPost.setPost("cashier");
                    newPost.setSalary(20000);
                    break;
                case 4:
                    newPost.setPId(4);
                    newPost.setPost("storekeeper");
                    newPost.setSalary(15000);
                    break;
            }
        return newPost;
    }
}
