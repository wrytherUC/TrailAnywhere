package com.trailanywhere.enterprise;

import com.trailanywhere.enterprise.dao.IUserDAO;
import com.trailanywhere.enterprise.dto.User;
import com.trailanywhere.enterprise.service.IUserService;
import com.trailanywhere.enterprise.service.UserServiceStub;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;

@SpringBootTest
public class UserTests {
    @Autowired
    private IUserService userService;
    @MockBean
    private IUserDAO userDAO;
    private User user = new User();

    /**
     * Test creating a new user
     * @throws Exception - handle errors
     */
    @Test
    void createUser() throws Exception {
        givenUserDataIsAvailable();
        whenUserDataIsCreated();
        thenCreateNewUser();
    }

    private void givenUserDataIsAvailable() throws Exception {
        Mockito.when(userDAO.save(user)).thenReturn(user);
        userService = new UserServiceStub(userDAO);
    }

    private void whenUserDataIsCreated() {
        user.setName("Sam");
    }

    private void thenCreateNewUser() throws Exception {
        User newUser = userService.save(user);
        assertEquals(user, newUser);
    }

    /**
     * Test deleting a user
     * @throws Exception - handle errors
     */
    @Test
    void deleteUser() throws Exception {
        givenUserDataIsAvailable();
        whenUserDataIsCreated();
        thenDeleteUser();
    }

    private void thenDeleteUser() throws Exception {
        userDAO.delete(user);
        verify(userDAO, atLeastOnce()).delete(user);
    }
}
