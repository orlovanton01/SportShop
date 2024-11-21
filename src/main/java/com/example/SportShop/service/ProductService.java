package com.example.SportShop.service;

import com.example.SportShop.model.Product;
import com.example.SportShop.model.Purchase;
import com.example.SportShop.model.Worker;
import com.example.SportShop.repository.ProductRepository;
import com.example.SportShop.repository.PurchaseRepository;
import com.example.SportShop.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final PurchaseRepository purchaseRepository;

    private final WorkerService workerService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.isPresent() ? productOptional.get() : null;
    }

    public void saveProduct(Product product) {
        // Проверяем наличие записи в таблице purchase
        if (product.getPurchase() == null || product.getPurchase().getId() == null) {
            Purchase newPurchase = new Purchase();
            // Инициализация переменных
            Random random = new Random();
            int c_i, y, m, d;
            String m_v, dt_i;

            // Генерация случайного значения для c_i
            c_i = random.nextInt(1000) + 1;

            // Получение w_i из базы данных через JPA
            int p_id = 3; // Условие для выбора
            Worker worker = workerService.fetchRandomWorker(p_id); // Метод для выполнения запроса

            // Генерация случайной даты
            y = random.nextInt(25) + 2000; // Генерация года в диапазоне 2000-2024
            m = random.nextInt(12) + 1;    // Генерация месяца (1-12)
            d = random.nextInt(28) + 1;    // Генерация дня (1-28)

            // Форматирование даты
            if (m <= 9) {
                m_v = "0" + m; // Добавляем ведущий ноль для месяцев 1-9
            } else {
                m_v = String.valueOf(m);
            }
            dt_i = d + "." + m_v + "." + y;
            newPurchase.setCId(c_i);
            newPurchase.setWorker(worker);
            newPurchase.setDt(dt_i);
            // Установите необходимые значения для новой записи purchase
            Purchase savedPurchase = purchaseRepository.save(newPurchase);
            product.setPurchase(savedPurchase);
        }
        productRepository.save(product);
    }

    public void deleteProductById(Integer id) { productRepository.deleteById(id); }

}
