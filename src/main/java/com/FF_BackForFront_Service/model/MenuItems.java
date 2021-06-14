package com.FF_BackForFront_Service.model;

import java.util.List;
import java.util.ArrayList;

public class MenuItems {

    public MenuItems() {
        this.items = new ArrayList<>();
        this.userName = "Placeholder";

    }

    private List<Item> items;
    private String userName;

    public List<Item> getItems() {
        return this.items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return this.userName;
    }

}
