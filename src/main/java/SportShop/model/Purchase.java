package SportShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ps_id", nullable = false)
    private Integer id;

    @Column(name = "c_id")
    private Integer cId;

    @ManyToOne
    @JoinColumn(name = "w_id", referencedColumnName = "w_id")
    private Worker worker;


    @Column(name = "dt", length = 30)
    private String dt;

}