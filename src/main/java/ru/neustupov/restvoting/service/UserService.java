package ru.neustupov.restvoting.service;

import ru.neustupov.restvoting.model.User;
import ru.neustupov.restvoting.to.UserTo;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    void update(User user);

    void update(UserTo user);

    List<User> getAll();

    User getByEmail(String email) throws NotFoundException;

    User getWithVotes(int id);

    void enable(int id, boolean enable);
}
