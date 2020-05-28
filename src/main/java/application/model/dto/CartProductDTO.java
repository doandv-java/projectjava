package application.model.dto;

public class CartProductDTO {
    private int id;
    private int productEntityId;
    private String guid;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductEntityId() {
        return productEntityId;
    }

    public void setProductEntityId(int productEntityId) {
        this.productEntityId = productEntityId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
