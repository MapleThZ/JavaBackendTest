package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Entitys.CustomerEntiry;

import org.springframework.stereotype.Service;

@Service
public class ListUser {

    private List<CustomerEntiry> customers;

    public ListUser() {
        customers = new ArrayList<>();
    }

    public List<CustomerEntiry> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerEntiry> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "ListUser [customers=" + customers + "]";
    }

}
