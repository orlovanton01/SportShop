package SportShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ps_id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ps_id", nullable = false, referencedColumnName = "ps_id", insertable = false, updatable = false)
    private Purchase purchase;

    @Column(name = "pr_name", length = 30)
    private String prName;

    @Column(name = "pr_manufacturer", length = 30)
    private String prManufacturer;

    @Column(name = "price")
    private Integer price;

}

