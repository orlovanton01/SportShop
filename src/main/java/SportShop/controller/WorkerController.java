package SportShop.controller;

import SportShop.model.Worker;
import SportShop.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return workerService.saveWorker(worker);
    }

    @PostMapping("/upload")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            workerService.saveWorkersFromExcel(file);
            return "Workers are uploaded";
        } catch (Exception e) {
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

    @GetMapping("/report")
    public ResponseEntity<byte[]> getWorkerReport() {
        try {
            byte[] report = workerService.generateWorkerReport();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=Worker_Report.docx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
                    .body(report);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping
    public String updateWorker(@RequestBody Worker worker) {
        return workerService.updateWorker(worker);
    }

    @DeleteMapping("/{id}")
    public String deleteWorkerById(@PathVariable Integer id) {
        if (workerService.getWorkerById(id) != null) {
            workerService.deleteWorkerById(id);
            return "Worker is deleted";
        }
        return "Worker not found";
    }
}
