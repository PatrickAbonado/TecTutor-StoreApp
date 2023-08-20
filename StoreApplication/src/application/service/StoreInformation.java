package application.service;

import java.util.*;

import application.dto.*;
import application.dao.*;


public class StoreInformation implements Display{

    private Scanner s;
    private ProductDAO productDAO;
    private ItemDAO itemDAO;

    public StoreInformation(){
        s = new Scanner(System.in);
        productDAO = new ProductDAO();
        itemDAO = new ItemDAO();
    }

    public ProductDAO getProductDAO(){
        return this.productDAO;
    }

    public ItemDAO getItemDAO(){
        return this.itemDAO;
    }

    public String chooseCategory(){
        String category = "";
        int choice = 0;

        while(true){
            s = new Scanner(System.in);

            System.out.print("\n**** Choose a Category Number ****\n" +
                    "1- Grocery\n2- Stationary\n3- Toiletry\n4- Vegetables\n");

            try {
                choice = s.nextInt();
            }
            catch (Exception e){
                System.out.println("Invalid entry.");

            }

            if(choice >= 1 && choice <= 4){
                    break;
            }

        }

        switch (choice){
            case 1:
                category = "Grocery";
                break;
            case 2:
                category = "Stationary";
                break;
            case 3:
                category = "Toiletry";
                break;
            case 4:
                category = "Vegetables";
                break;
        }

        return category;
    }

    public void insertProduct(){
        int numProds = 0;

        System.out.print("\nHow many products would you like to enter? ");
        numProds = s.nextInt();

        for (int i = 0; i < numProds; ++i){

            Product newProduct = new Product();
            Item newItem = new Item();

            newProduct.setProductId(productDAO.getCurrIdNum());

            try{
                s = new Scanner(System.in);

                System.out.print("\nEnter Product Name: ");
                newProduct.setProductName(s.nextLine());
                System.out.print("Enter Buying Price: ");
                newItem.setBuyingPrice(s.nextDouble());
                System.out.print("Enter Available Quantity: ");
                newProduct.setAvailableQuantity(s.nextInt());

            }
            catch (Exception e){
                System.out.println("Your most recent entry was in an invalid format.");
            }

            newItem.setCategory(chooseCategory());
            newProduct.setSellingPrice((newItem.getBuyingPrice() * 0.5) + newItem.getBuyingPrice());
            newItem.setItemName(newProduct.getProductName());

            productDAO.getProductMap().put(newProduct.getProductId(), newProduct);
            itemDAO.getItemArrayList().add(newItem);
        }
    }


    @Override
    public void display(){

        Set<Map.Entry<Integer,Product>> prodMap = productDAO.getProductMap().entrySet();

        for (Map.Entry<Integer, Product> productEntry : prodMap){
            System.out.print("\nProduct ID: " + productEntry.getKey()
            + "\nProduct Name: " + productEntry.getValue().getProductName()
                    + "\nCategory: ");

            for(Item item : this.itemDAO.getItemArrayList()){
                if (item.getItemName().equals(productEntry.getValue().getProductName())) {
                    System.out.print(item.getCategory());
                    break;
                }
            }

            System.out.print("\nAvailable Quantity: " + productEntry.getValue().getAvailableQuantity()
                    + "\nSelling Price: $" + productEntry.getValue().getSellingPrice() + "\n");
        }

    }

    public void findProduct(){

        boolean isFound = false;
        int id = 0;

        System.out.print("Enter a Product ID: ");
        while(true){
            s = new Scanner(System.in);

            try{
                id = s.nextInt();
                if(id > 0){
                    break;
                }
            }
            catch (Exception e){
                System.out.println("Enter only a valid integer number.");
            }

        }

        Set<Map.Entry<Integer, Product>> productMap = productDAO.getProductMap().entrySet();

        for (Map.Entry<Integer, Product> productEntry : productMap){
            if(productEntry.getValue().getProductId() == id){

                productEntry.getValue().display();
                isFound = true;
                break;
            }
        }

        if(!isFound){
            System.out.println("No match was found for the entry provided.");
        }
    }

    public int chooseCategoryNum(){
        int choice = 0;

        do {
            s = new Scanner(System.in);

            System.out.print("\n**** Select a Category Number ****\n" +
                    "1- Grocery\n2- Stationary\n3- Toiletry\n" +
                    "4- Vegetables\n5- Exit\n");

            try {
                choice = s.nextInt();
            } catch (Exception e) {
                System.out.println("Enter only valid integer numbers.");
            }

        } while (choice < 1 || choice > 5);

        return choice;
    }

    //**** Products By Category ****
    public void listByCategory(){

        String category = "";
        boolean isInCat = false;

        int choice = chooseCategoryNum();

        category = getCategoryName(choice);

        Set<Map.Entry<Integer, Product>> productMap = productDAO.getProductMap().entrySet();

        System.out.println("**** " + category + " ****");
        for (Item item : itemDAO.getItemArrayList()){

            if(item.getCategory().equals(category)){

                for(Map.Entry<Integer, Product> entry : productMap){

                    if(entry.getValue().getProductName().equals(item.getItemName())){

                        entry.getValue().display();

                        isInCat = true;
                    }
                }
            }
        }

        if(!isInCat){
            System.out.println("No products of this category are currently available.");
        }

    }

    public void searchByName(){
        s = new Scanner(System.in);
        String name = "";
        boolean isFound = false;

        System.out.print("Enter a Product Name: ");
        name = s.nextLine().toLowerCase();

        if(!name.isEmpty()){

            Set<Map.Entry<Integer, Product>> productEntries = productDAO.getProductMap().entrySet();

            for (Map.Entry<Integer, Product> productEntry : productEntries){

                if(productEntry.getValue().getProductName().toLowerCase().equals(name)){
                    productEntry.getValue().display();
                    isFound = true;
                }
            }

        }
        else{
            System.out.println("\nNo entry was detected.");
        }

        if(!isFound){
            System.out.println("\nNo match was found.");
        }

    }

    public double calculateAllTotal(){

        ArrayList<Item> itemArrayList = itemDAO.getItemArrayList();
        Set<Map.Entry<Integer,Product>> productEntries = productDAO.getProductMap().entrySet();

        double total = 0.0;

        for (int i = 0; i < itemArrayList.size(); ++i){

            double buyPrice = itemArrayList.get(i).getBuyingPrice();
            String prodName = itemArrayList.get(i).getItemName();

            for (Map.Entry<Integer, Product> productEntry : productEntries){
                if(productEntry.getValue().getProductName().equals(prodName)){
                    total += buyPrice * productEntry.getValue().getAvailableQuantity();
                }
            }
        }

        return  total;

    }

    public void checkProdTotal(){

        double allTotal = calculateAllTotal();

        System.out.print("\nTotal Amount Spent: $" + allTotal + "\n");

    }

    public void displayProfits(){

        ArrayList<Item> itemArrayList = itemDAO.getItemArrayList();
        Set<Map.Entry<Integer,Product>> productEntries = productDAO.getProductMap().entrySet();
        String category = "";
        double buyPriceTotal = 0;
        double sellPriceTotal = 0;

        s = new Scanner(System.in);

        int choice = chooseCategoryNum();

        switch (choice){
            case 1:
                category = "Grocery";
                break;
            case 2:
                category = "Stationary";
                break;
            case 3:
                category = "Toiletry";
                break;
            case 4:
                category = "Vegetables";
                break;
            case 5:
                return;
        }

        double buyPrice;

        for (int i = 0; i < itemArrayList.size(); ++i){

            String prodName = "";

            if(itemArrayList.get(i).getCategory().equals(category)){

                buyPrice = itemArrayList.get(i).getBuyingPrice();
                prodName = itemArrayList.get(i).getItemName();

                for (Map.Entry<Integer, Product> productEntry : productEntries){

                    if(productEntry.getValue().getProductName().equals(prodName)){

                        double sellPrice = productEntry.getValue().getSellingPrice();

                        buyPriceTotal += buyPrice * productEntry.getValue().getAvailableQuantity();
                        sellPriceTotal += sellPrice * productEntry.getValue().getAvailableQuantity();
                    }
                }
            }
        }

        System.out.print("\nCategory: " + category +
                "\nAmount Spent: $" + buyPriceTotal +
                "\nAmount Sold For: $" + sellPriceTotal +
                "\nTotal Profit: $" + (sellPriceTotal - buyPriceTotal) + "\n");

    }

    public void filterByPrice(){

        Set<Map.Entry<Integer, Product >> productEntries = productDAO.getProductMap().entrySet();
        TreeMap<Double, Product> sortedMap = new TreeMap<>();

        for (Map.Entry<Integer,Product> productEntry : productEntries){
            sortedMap.put(productEntry.getValue().getSellingPrice(), productEntry.getValue());
        }

        Set<Map.Entry<Double,Product>> sortedEntries = sortedMap.entrySet();

        System.out.print("\n**** Available Products By Price ****");
        for (Map.Entry<Double,Product> sortedEntry : sortedEntries){
            sortedEntry.getValue().display();
        }

    }

    public String getCategoryName(int catNum){
        String category = "";

        switch (catNum){
            case 1:
                category = "Grocery";
                break;
            case 2:
                category = "Stationary";
                break;
            case 3:
                category = "Toiletry";
                break;
            case 4:
                category = "Vegetables";
                break;
        }

        return category;

    }

    public void filterByCategory(){

        ArrayList<Item> itemArrayList = itemDAO.getItemArrayList();
        Set<Map.Entry<Integer, Product >> productEntries = productDAO.getProductMap().entrySet();

        int choice = chooseCategoryNum();

        String category = getCategoryName(choice);

        boolean isInCategory = false;

        System.out.print("\n**** Products For " + category + " ****");

        for(int i = 0; i < itemArrayList.size(); ++i){

            String name = "";

            if(itemArrayList.get(i).getCategory().equals(category)){
                name = itemArrayList.get(i).getItemName();
            }

            for (Map.Entry<Integer,Product> productEntry : productEntries){

                if (name.equals(productEntry.getValue().getProductName())) {

                    isInCategory = true;
                    productEntry.getValue().display();

                }
            }
        }

        if(!isInCategory){
            System.out.println("\nNo products are currently available in that category.");
        }

    }
}
