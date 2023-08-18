package lk.ijse.dep10.pos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_customer")
public class OrderCustomer implements SuperEntity {
    @Id
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;
}
