package application.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "dbo_product_image")
public class ProductImage {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_image_id")
    @Id
    private int id;

    @Column(name = "product_id", insertable=false, updatable = false)
    private int productId;

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "link")
    private String link;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ProductImage(int productId, String link, Date createdDate, String title) {
        this.productId = productId;
        this.link = link;
        this.createdDate = createdDate;
        this.title = title;
    }

    public ProductImage() {
    }
}
