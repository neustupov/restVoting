package ru.neustupov.restvoting.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.neustupov.restvoting.model.Role;
import ru.neustupov.restvoting.model.User;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import static ru.neustupov.restvoting.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "newPass", Date.from(Instant.now()), Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(service.getAll(), ADMIN, newUser, USER);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateNameCreate() throws Exception {
        service.create(new User(null, "User", "password", Date.from(Instant.now()), Collections.singleton(Role.ROLE_USER)));
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_ID);
        assertMatch(service.getAll(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = service.getAll();
        assertMatch(all, ADMIN, USER);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new User(100500, "   ", "Password", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(100500, "TestUser", "   ", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(100500, "TestUser", "Pass", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(100500, "TestUser", "1234567890qwertyuiopsdfghjklzxcvbnmnbvcxzasdfghjklpoiuytrewq12345", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(100500, "TestUser", "Password", null, EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
    }

}
