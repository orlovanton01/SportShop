package com.example.SportShop.service;

import com.example.SportShop.model.Post;
import com.example.SportShop.model.Worker;
import com.example.SportShop.repository.PostRepository;
import com.example.SportShop.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final PostRepository postRepository;

    private final WorkerRepository workerRepository;

    private final PostService postService;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> workerOptional = workerRepository.findById(id);
        return workerOptional.isPresent() ? workerOptional.get() : null;
    }

    public void saveWorker(Worker worker) {
        workerRepository.save(worker);
        String[] d_post= {"director", "consultant", "cashier", "storekeeper"};
        Integer[] d_salary= {65000, 25000, 20000, 15000};
        Random rand = new Random();
        Post newPost = new Post();
        if (postRepository.findAll().isEmpty())
            for (int i = 0; i < 4; i++) {
                newPost.setPId(i+1);
                newPost.setPost(d_post[i]);
                newPost.setSalary(String.valueOf(d_salary[i]));
            }
        else
            switch (rand.nextInt(3) + 2) {
                case 2:
                    newPost.setPId(2);
                    newPost.setPost("consultant");
                    newPost.setSalary(String.valueOf(25000));
                    break;
                case 3:
                    newPost.setPId(3);
                    newPost.setPost("cashier");
                    newPost.setSalary(String.valueOf(20000));
                    break;
                case 4:
                    newPost.setPId(4);
                    newPost.setPost("storekeeper");
                    newPost.setSalary(String.valueOf(15000));
                    break;
            }
        postRepository.save(newPost);
    }

    public void deleteWorkerById(Integer id) {
        if (postService.getPostById(id) != null) {
            postRepository.deleteById(id);
            workerRepository.deleteById(id);
        }
    }
}
