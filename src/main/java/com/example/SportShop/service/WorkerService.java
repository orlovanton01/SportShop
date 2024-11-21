package com.example.SportShop.service;

import com.example.SportShop.model.Worker;
import com.example.SportShop.repository.PostRepository;
import com.example.SportShop.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final PostRepository postRepository;

    private final WorkerRepository workerRepository;

    public Worker fetchRandomWorker(int pId) {
        return postRepository.findRandomWorkerByPostId(pId)
                .orElseThrow(() -> new RuntimeException("No worker found for the given p_id."));
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> workerOptional = workerRepository.findById(id);
        return workerOptional.isPresent() ? workerOptional.get() : null;
    }

    public void saveWorker(Worker worker) {
        workerRepository.save(worker);
    }

    public void deleteWorkerById(Integer id) { workerRepository.deleteById(id); }
}
