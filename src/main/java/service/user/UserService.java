package service.user;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);

    User update(User user);

    User getById(Long id);

    List<User> getAll();

    boolean delete(Long id);
}
