package repository.hibernate.user;

import dto.UserDTO;
import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserDTO> save(UserDTO user);

    List<UserDTO> getAll();

    UserDTO update(UserDTO user);

    Optional<UserDTO> getById(Long id);

    boolean delete(Long id);
}
