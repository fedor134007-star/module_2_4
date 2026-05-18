package service.user;

import dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO create(UserDTO user);

    UserDTO update(UserDTO user);

    UserDTO getById(Long id);

    List<UserDTO> getAll();

    boolean delete(Long id);
}
