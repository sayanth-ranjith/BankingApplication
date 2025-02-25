package com.bankingApp.BankingApplication.LeafStruct;

import java.util.LinkedList;
import java.util.List;

public interface BillingHierarchy {
    void add(BillingHierarchy billingHierarchy);

    void remove(BillingHierarchy billingHierarchy);

    String getName();

    LinkedList<BillingHierarchy> getChildren();

    Boolean selected();

}
