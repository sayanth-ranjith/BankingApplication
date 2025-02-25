package com.bankingApp.BankingApplication.LeafStruct;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Leaf implements BillingHierarchy{
    private String name;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    private Boolean isSelected;
    public Leaf(String name){
        this.name = name;
    }
    @Override
    public void add(BillingHierarchy billingHierarchy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(BillingHierarchy billingHierarchy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LinkedList<BillingHierarchy> getChildren() {
        return null;
    }

    @Override
    public Boolean selected() {
        return isSelected;
    }
}
