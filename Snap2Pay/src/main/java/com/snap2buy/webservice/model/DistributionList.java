package com.snap2buy.webservice.model;

/**
 * Created by sachin on 3/16/16.
 */
public class DistributionList {
    String listName;
    String listId;
    String upc;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Override
    public String toString() {
        return "DistributionList{" +
                "listName='" + listName + '\'' +
                ", listId='" + listId + '\'' +
                ", upc='" + upc + '\'' +
                '}';
    }
}
