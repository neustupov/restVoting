package ru.neustupov.restvoting.service;

import ru.neustupov.restvoting.model.User;
import ru.neustupov.restvoting.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    void update(User user);

    List<User> getAll();

    User getWithVotes(int id);
}
