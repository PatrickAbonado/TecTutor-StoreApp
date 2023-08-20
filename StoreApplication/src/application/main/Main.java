package application.main;

import java.util.Scanner;
import application.dao.*;
import application.service.StoreInformation;

public class Main {
    static int type = 0;
    static UserDAO usersList;
    static boolean isExit = false;

    public static int getAdminSelection(){
        Scanner s = new Scanner(System.in);
        int userInput = 0;

            while(true){

                System.out.println("\n**** Select an Administrator Menu Number ****\n" +
                        "1- List Products\n" +
                        "2- Search Product By Product ID\n" +
                        "3- Display Product By Category\n" +
                        "4- Search By Product Name And Display\n" +
                        "5- Display Total Amount Spent\n" +
                        "6- Display Profit By Category\n" +
                        "7- Enter New Products\n" +
                        "8- Sign Out\n" +
                        "9- Exit\n");

                try{
                    userInput = s.nextInt();
                }
                catch (NumberFormatException nfe){
                    System.out.println("Enter a valid integer number.");
                }

                switch (userInput){
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    case 3:
                        return 3;
                    case 4:
                        return 4;
                    case 5:
                        return 5;
                    case 6:
                        return 6;
                    case 7:
                        return 7;
                    case 8:
                        return 8;
                    case 9:
                        return 9;
                }
            }
    }

    public static int getFirstSelection(){

        int userInput = 0;

        while(true){
            Scanner s = new Scanner(System.in);

            System.out.println("\n**** Select A Menu Number Option ****\n" +
                    "1- Login\n" +
                    "2- Register New Customer\n" +
                    "3- Exit\n");

            try{
                userInput = s.nextInt();
            }
            catch (Exception e){
                System.out.println("\nEnter a valid integer number.");
            }

            switch (userInput){
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
            }
        }
    }

    public static void inputAdminSelection(int selection, StoreInformation storeInformation){

        switch(selection){
            case 1:
                storeInformation.display();
                if(storeInformation.getProductDAO().getProductMap().isEmpty()){
                    System.out.println("\nNo products are currently available.");
                }
                break;
            case 2:
                storeInformation.findProduct();
                break;
            case 3:
                storeInformation.listByCategory();
                break;
            case 4:
                storeInformation.searchByName();
                break;
            case 5:
                storeInformation.checkProdTotal();
                break;
            case 6:
                storeInformation.displayProfits();
                break;
            case 7:
                storeInformation.insertProduct();
        }
    }

    public static void inputCustomerSelection(int selection, StoreInformation storeInformation){

        switch (selection){
            case 1:
                storeInformation.filterByPrice();
                break;
            case 2:
                storeInformation.filterByCategory();
                if(storeInformation.getProductDAO().getProductMap().isEmpty()){
                    System.out.println("\nNo products are currently available.");
                }
                break;
            case 3:
                storeInformation.display();
                break;

        }
    }

    public static boolean login(UserDAO userDAO){

        Scanner s = new Scanner(System.in);
        boolean isValid = false;
        String username, password;

        System.out.print("Enter a Username: ");
        username = s.nextLine().toLowerCase();

        s = new Scanner(System.in);
        System.out.print("Enter a Password: ");
        password = s.nextLine().toLowerCase();

        for (int i = 0; i < userDAO.getUserList().size(); ++i){

            String name = userDAO.getUserList().get(i).getUsername().toLowerCase();
            String checkPwd = userDAO.getUserList().get(i).getPassword().toLowerCase();

            if(username.equals(name) && password.equals(checkPwd)){

                isValid = true;

                if(username.contains("admin")){
                    type = 1;
                }
                else {
                    type = 2;
                }

                break;
            }
        }

        return  isValid;
    }

    public static int getCustomerSelection(){

        Scanner s = new Scanner(System.in);

        int customerSelection = 0;

        System.out.println("\n**** Select Customer Menu Number ****\n" +
                "1- Filter Products and Display By Price\n" +
                "2- Filter Products and Display By Category\n" +
                "3- Display All Products\n" +
                "4- Sign Out\n" +
                "5- Exit\n");

        try{
            customerSelection = s.nextInt();
        }catch (Exception e){
            System.out.println("Invalid selection entry.");
        }

        return customerSelection;

    }


    public static void main(String[] args) {

        StoreInformation store = new StoreInformation();

        usersList = new UserDAO();

        boolean isFirst;

        int selection;

        while(true){

            selection = 0;
            isFirst = true;

            int firstMenuSelection  = getFirstSelection();

            while(isFirst){

                switch (firstMenuSelection){
                    case 1:
                        while(!login(usersList)){
                            System.out.println("\nInvalid Username or Password Entry\n");
                        }
                        isFirst = false;
                        break;
                    case 2:
                        usersList.insertUser();
                        firstMenuSelection = getFirstSelection();
                        break;
                    case 3:
                       System.out.println("Have a Nice Day!");
                       return;

                }

            }



            while(selection != 8){

                switch (type){
                    case 1:
                        selection = getAdminSelection();
                        break;
                    case 2:
                        selection = getCustomerSelection();
                        break;
                }

                if(type == 1){
                    if(selection == 8){
                        break;
                    }
                    else if (selection == 9){
                        isExit = true;
                        break;
                    }
                    else {
                        inputAdminSelection(selection, store);
                    }
                }
                if(type == 2){
                    if (selection == 4){
                        break;
                    }
                    else if(selection == 5){
                        isExit = true;
                        break;

                    }
                    else{
                        inputCustomerSelection(selection, store);
                    }
                }
            }

            if(isExit){ break;}
        }

        System.out.println("Have a Nice Day!");
    }

}