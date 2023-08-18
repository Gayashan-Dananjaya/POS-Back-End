package lk.ijse.dep10.pos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail implements SuperEntity {
    @EmbeddedId
    private OrderDetailPK orderDetailPK;
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private int qty;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_code", referencedColumnName = "code", insertable = false, updatable = false)
    private Item item;

    public OrderDetail(OrderDetailPK orderDetailPK, BigDecimal unitPrice, int qty) {
        this.orderDetailPK = orderDetailPK;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public OrderDetail(int orderId, String itemCode,
                       BigDecimal unitPrice, int qty) {
        this.orderDetailPK = new OrderDetailPK(orderId, itemCode);
        this.unitPrice = unitPrice;
        this.qty = qty;
    }
}
