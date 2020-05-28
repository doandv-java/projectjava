package application.model.viewmodel;

public class CartProductVM {
    private int id;
    private int productEntityId;
    private int productId;
    private String mainImage;
    private String price;
    private long amount;
    private String productName;
    private String colorName;
    private String sizeName;
    private String name;
    private String totalPrice;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

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

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(double price) {
        String temp="";
        temp=Double.toString(price);
        temp=temp.substring(0,temp.indexOf('.'));
        int n=temp.length()/3;
        int length=temp.length();
        String result="";
        for (int i=0; i<=n;i++){
            if(i==0){
                result=result.concat(temp.substring(0,length-3*n));
            } else{
                if(!result.equals(""))
                    result=result.concat(".");
                result=result.concat(temp.substring(length-3*n+(i-1)*3,length-3*n+i*3));
            }
        }
        this.price = result;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        String temp="";
        temp=Double.toString(totalPrice);
        temp=temp.substring(0,temp.indexOf('.'));
        int n=temp.length()/3;
        int length=temp.length();
        String result="";
        for (int i=0; i<=n;i++){
            if(i==0){
                result=result.concat(temp.substring(0,length-3*n));
            } else{
                if(!result.equals(""))
                    result=result.concat(".");
                result=result.concat(temp.substring(length-3*n+(i-1)*3,length-3*n+i*3));
            }
        }
        this.totalPrice = result;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
