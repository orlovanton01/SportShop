package com.example.SportShop.controller;

import com.example.SportShop.model.Worker;
import com.example.SportShop.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping
    public String addWorker(@RequestBody Worker worker) {
        workerService.saveWorker(worker);
        return "Worker is created";
    }

    @PostMapping("/upload")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            workerService.saveWorkersFromExcel(file);
            return "Workers are uploaded";
        } catch (IOException e) {
            return "Error occurred";
        }
    }

    @GetMapping
    public List<Worker> getAllWorkers() {
        return workerService.getAllWorkers();
    }

    @GetMapping("/{id}")
    public Worker getWorkerById(@PathVariable Integer id) {
        return workerService.getWorkerById(id);
    }

    @PutMapping
    public String updateWorker(@RequestBody Worker worker) {
        workerService.updateWorker(worker);
        return "Worker is updated";
    }

    @DeleteMapping("/{id}")
    public String deleteWorker(@PathVariable Integer id) {
        if (workerService.getWorkerById(id) != null) {
            workerService.deleteWorkerById(id);
            return "Worker is deleted";
        }
        return "Worker not found";
    }
}
