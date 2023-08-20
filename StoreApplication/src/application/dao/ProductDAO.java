package application.dao;
import application.dto.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductDAO {

    public static int currIdNum = 1;

    private Map<Integer, Product> productMap;
    private Scanner s;

    public ProductDAO(){
        productMap = new HashMap<>();
        s = new Scanner(System.in);
    }

    public static int getCurrIdNum(){
        int id = currIdNum;

        currIdNum++;

        return id;
    }


    public Map<Integer, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<Integer, Product> productMap) {
        this.productMap = productMap;
    }

    public void insertProduct(int productId, Product product){
        this.productMap.put(productId, product);
    }

}
