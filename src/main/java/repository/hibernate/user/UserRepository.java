package repository.hibernate.user;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> save(User user);

    List<User> getAll();

    User update(User user);

    Optional<User> getById(Long id);

    boolean delete(Long id);
}
