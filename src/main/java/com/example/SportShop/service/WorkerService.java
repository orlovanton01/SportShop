package com.example.SportShop.service;

import com.example.SportShop.model.Worker;
import com.example.SportShop.repository.PostRepository;
import com.example.SportShop.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        postRepository.save(postService.addNewPost());
    }

    public void saveWorkersFromExcel(MultipartFile file) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Пропускаем заголовок

                Worker worker = new Worker();
                worker.setWSurname(row.getCell(0).getStringCellValue());
                worker.setWName(row.getCell(1).getStringCellValue());
                worker.setWPatronymic(row.getCell(2).getStringCellValue());

                workerRepository.save(worker);
                postRepository.save(postService.addNewPost());
            }
        }
    }

    public void updateWorker(Worker worker) {
        workerRepository.save(worker);
    }

    public void deleteWorkerById(Integer id) {
        if (postService.getPostById(id) != null) {
            postRepository.deleteById(id);
            workerRepository.deleteById(id);
        }
    }
}
