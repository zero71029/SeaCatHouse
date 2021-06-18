package tw.com.iii.OceanCatHouse.model;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetailBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer OrderDetailId;
    private Integer OrderId;
    private Integer ProductId;
    private Integer SellingPrice;
    private Integer Unit;
    private Integer Discount;

    @Override
    public String toString() {
        return "orderDetailBean{" +
                "OrderDetailId=" + OrderDetailId +
                ", OrderId=" + OrderId +
                ", ProductId=" + ProductId +
                ", SellingPrice=" + SellingPrice +
                ", Unit=" + Unit +
                ", Discount=" + Discount +
                '}';
    }

    public Integer getOrderDetailId() {
        return OrderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        OrderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderId) {
        OrderId = orderId;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer productId) {
        ProductId = productId;
    }

    public Integer getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public Integer getUnit() {
        return Unit;
    }

    public void setUnit(Integer unit) {
        Unit = unit;
    }

    public Integer getDiscount() {
        return Discount;
    }

    public void setDiscount(Integer discount) {
        Discount = discount;
    }
}