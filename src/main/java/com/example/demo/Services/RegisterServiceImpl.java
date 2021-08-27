package com.example.demo.Services;

import com.example.demo.Entitys.CustomerEntiry;
import com.example.demo.Models.JwtRequest;
import com.example.demo.utils.ListUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl {

    @Autowired
    private ListUser listUser;

    public String saveCustomer(JwtRequest request) {

        CustomerEntiry customer = new CustomerEntiry();
        BeanUtils.copyProperties(request, customer);
        customer.setIdCustomer(request.getCreateDate() + request.getTelNo().substring(6));
        String memberType = checkMemberType(request.getSalary());
        if ("reject".equalsIgnoreCase(memberType)) {
            return memberType;
        }
        customer.setMemberType(memberType);

        boolean status = false;
        if (listUser != null && listUser.getCustomers() != null) {
            for (int i = 0; i < listUser.getCustomers().size(); i++) {
                CustomerEntiry cus = listUser.getCustomers().get(i);
                if (cus.getUsername().equalsIgnoreCase(customer.getUsername())) {
                    return "duplicate";
                }
                if (cus.getIdCustomer().equalsIgnoreCase(customer.getIdCustomer())) {
                    listUser.getCustomers().set(i, customer);
                    status = true;
                    break;
                }
            }
        }

        if(listUser == null){
            listUser = new ListUser();
        }

        if (!status) {
            listUser.getCustomers().add(customer);
        }

        return customer.getIdCustomer();
    }

    public String checkMemberType(String salary) {
        if (StringUtils.isNotBlank(salary)) {
            long salaryNum = Long.parseLong(salary);
            if (salaryNum > 50000) {
                return "Platinum";
            } else if (salaryNum >= 30000 && salaryNum < 50000) {
                return "Gold";
            } else if (salaryNum >= 15000 && salaryNum < 30000) {
                return "Silver";
            } else {
                return "reject";
            }
        } else {
            return "reject";
        }
    }

    public void setlistUser(ListUser listUser) {
        this.listUser = listUser;
    }

}
