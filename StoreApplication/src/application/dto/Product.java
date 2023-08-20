package application.dto;

public class Product implements Display{

    private int productId;
    private String productName;
    private double sellingPrice;
    private int availableQuantity;

    public Product(){}
    public Product(String productName, double buyingPrice,
                   int availableQuantity){
        this.productId = application.dao.ProductDAO.getCurrIdNum();
        this.productName = productName;
        this.sellingPrice = ((buyingPrice * 0.5) + buyingPrice);
        this.availableQuantity = availableQuantity;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public void display() {

        System.out.println("\nProduct Id: " + this.getProductId());
        System.out.println("Product Name: " + this.getProductName());
        System.out.println("Selling Price: $" + this.getSellingPrice());
        System.out.println("Available Quantity: " + this.getAvailableQuantity());
    }
}
