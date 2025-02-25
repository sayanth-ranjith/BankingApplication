package com.bankingApp.BankingApplication.LeafStruct;

public class MainTreeCode {
    public static void main(String[] args) {
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");

        Leaf adi_1 = new Leaf("ADI1");
        Leaf adi_2 = new Leaf("ADI2");


        a.add(b);
        b.add(c);
        c.add(d);
        c.add(adi_1);
        c.add(adi_2);

        adi_2.setSelected(true);
    }
}
