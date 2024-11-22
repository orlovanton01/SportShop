package SportShop;

import SportShop.service.WorkerService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SportShopApplicationTests {

	@Autowired
	WorkerService workerService;

	@Test
	public void testGenerateWorkerReport() throws Exception {
		// Генерация отчёта
		byte[] report = workerService.generateWorkerReport();

		// Проверка, что отчёт не пустой
		assertNotNull(report, "Отчёт должен быть сгенерирован");
		assertTrue(report.length > 0, "Размер отчёта должен быть больше 0");

		// Проверка, что документ корректно открывается как Word
		try (XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(report))) {
			assertNotNull(document, "Документ должен быть создан");
			assertFalse(document.getParagraphs().isEmpty(), "В документе должны быть параграфы");
		}
	}

}
