package SportShop.service;

import SportShop.model.Worker;
import SportShop.repository.PostRepository;
import SportShop.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    public byte[] generateWorkerReport() throws IOException {
        // Фиксированный диапазон зарплат
        int minSalary = 10000;
        int maxSalary = 20000;

        // Получение данных из БД
        List<Map<String, Object>> workers = workerRepository.findWorkersBySalaryRange(minSalary, maxSalary);

        // Создание Word-документа
        try (XWPFDocument document = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Заголовок
            XWPFParagraph header = document.createParagraph();
            header.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun headerRun = header.createRun();
            headerRun.setText("ФИО всех сотрудников с зарплатой от 10000 до 20000 (запрос от " + LocalDate.now() + ")");
            headerRun.setBold(true);
            headerRun.setFontSize(14);

            // Пустая строка
            document.createParagraph();

            // Таблица
            XWPFTable table = document.createTable();

            // Центрируем таблицу
            table.setTableAlignment(TableRowAlign.CENTER);

            // Заголовки таблицы
            XWPFTableRow headerRow = table.getRow(0);
            createStyledCell(headerRow.getCell(0), "Фамилия", true);
            createStyledCell(headerRow.addNewTableCell(), "Имя", true);
            createStyledCell(headerRow.addNewTableCell(), "Отчество", true);

            // Добавление данных
            for (Map<String, Object> worker : workers) {
                XWPFTableRow row = table.createRow();
                createStyledCell(row.getCell(0), worker.get("surname").toString(), false);
                createStyledCell(row.getCell(1), worker.get("name").toString(), false);
                createStyledCell(row.getCell(2), worker.get("patronymic").toString(), false);
            }

            // Сохранение документа в байтовый массив
            document.write(out);
            return out.toByteArray();
        }
    }

    // Создаёт ячейку с заданным текстом, стилем (жирный/обычный) и размером шрифта
    private void createStyledCell(XWPFTableCell cell, String text, boolean isBold) {
        XWPFParagraph paragraph = cell.getParagraphs().getFirst();
        paragraph.setAlignment(ParagraphAlignment.CENTER); // Выравнивание текста в ячейке по центру
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(isBold);
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
