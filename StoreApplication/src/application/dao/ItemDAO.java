package application.dao;

import application.dto.Item;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemDAO {

    private ArrayList<Item> itemArrayList;
    private Scanner s;

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void setItemArrayList(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }

    public ItemDAO(){

        s = new Scanner(System.in);
        itemArrayList = new ArrayList<>();

    }
}
