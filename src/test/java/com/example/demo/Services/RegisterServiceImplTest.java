package com.example.demo.Services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.example.demo.Entitys.CustomerEntiry;
import com.example.demo.Models.JwtRequest;
import com.example.demo.utils.ListUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegisterServiceImplTest {

    @InjectMocks
    private RegisterServiceImpl registerServiceImpl;

    @Autowired
    private ListUser listUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveCustomer_case_add_success() {
        JwtRequest request = new JwtRequest();
        request.setBirthDay("20180808");
        request.setUsername("javainuse4");
        request.setPassword("password");
        request.setTelNo("0881992332");
        request.setSalary("500000");
        request.setFirstName("first name");
        request.setLastName("laster name");
        request.setCreateDate("20180808");
        String type = registerServiceImpl.saveCustomer(request);
        assertNotEquals("reject", type);
        assertNotEquals("duplicate", type);
    }

    @Test
    public void saveCustomer_case_duplicate() {
        JwtRequest request = new JwtRequest();
        request.setBirthDay("20180808");
        request.setUsername("javainuse4");
        request.setPassword("password");
        request.setTelNo("0881992332");
        request.setSalary("500000");
        request.setFirstName("first name");
        request.setLastName("laster name");
        request.setCreateDate("20180808");
        String type = registerServiceImpl.saveCustomer(request);

        
        type = registerServiceImpl.saveCustomer(request);
        assertNotEquals("reject", type);
        assertEquals("duplicate", type);
    }

    @Test
    public void saveCustomer_case_Reject() {
        JwtRequest request = new JwtRequest();
        request.setBirthDay("20180808");
        request.setUsername("javainuse4");
        request.setPassword("password");
        request.setTelNo("0881992332");
        request.setSalary("14999");
        request.setFirstName("first name");
        request.setLastName("laster name");
        request.setCreateDate("20180808");
        String type = registerServiceImpl.saveCustomer(request);
        assertEquals("reject", type);
        assertNotEquals("duplicate", type);
    }

    @Test
    public void checkMemberType_case_Platinum() {
        String salary = "55000";
        String type = registerServiceImpl.checkMemberType(salary);
        assertEquals("Platinum", type);
    }

    @Test
    public void checkMemberType_case_Gold() {
        String salary = "35000";
        String type = registerServiceImpl.checkMemberType(salary);
        assertEquals("Gold", type);
    }

    @Test
    public void checkMemberType_case_Silver() {
        String salary = "20000";
        String type = registerServiceImpl.checkMemberType(salary);
        assertEquals("Silver", type);
    }

    @Test
    public void checkMemberType_case_Reject_less_than_15000() {
        String salary = "14999";
        String type = registerServiceImpl.checkMemberType(salary);
        assertEquals("reject", type);
    }

    @Test
    public void checkMemberType_case_Reject_dont_have_salary() {
        String salary = "";
        String type = registerServiceImpl.checkMemberType(salary);
        assertEquals("reject", type);
    }
}
