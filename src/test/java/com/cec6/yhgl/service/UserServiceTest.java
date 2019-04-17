package com.cec6.yhgl.service;

import com.cec6.yhgl.domain.User;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: findAll()
     */
    @Test
    public void testFindAll() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: createUser(User user)
     */
    @Test
    public void testCreateUser() throws Exception {
        userService.createUser(new User("admin", "admin"));
        userService.createUser(new User("tom", "tom"));
    }

    /**
     * Method: deleteUser(String userId)
     */
    @Test
    public void testDeleteUser() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: changePassword(String userId, String newPassword)
     */
    @Test
    public void testChangePassword() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setAppUpdate(String userID, Boolean appUpdate)
     */
    @Test
    public void testSetAppUpdate() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getAppUpdate(String userID)
     */
    @Test
    public void testGetAppUpdate() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: findByUsername(String username)
     */
    @Test
    public void testFindByUsername() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: findRoles(String userId)
     */
    @Test
    public void testFindRoles() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: correlationDept(String userId, String deptId)
     */
    @Test
    public void testCorrelationDept() throws Exception {
//TODO: Test goes here... 
    }


} 
