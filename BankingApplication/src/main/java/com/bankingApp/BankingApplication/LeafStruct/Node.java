package com.bankingApp.BankingApplication.LeafStruct;

import java.util.LinkedList;
import java.util.List;

public class Node implements BillingHierarchy{
    private String name;

    private Boolean isSelected;
    LinkedList<BillingHierarchy> childrenOfNode = new LinkedList<>();
    public Node(String name){
        this.name= name;
    }

    @Override
    public void add(BillingHierarchy billingHierarchy) {
        childrenOfNode.add(billingHierarchy);
    }

    @Override
    public void remove(BillingHierarchy billingHierarchy) {
        childrenOfNode.remove(billingHierarchy);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LinkedList<BillingHierarchy> getChildren() {
        return childrenOfNode;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public Boolean selected() {
        return isSelected;
    }

}
