package service.user;

import dto.UserDTO;
import repository.hibernate.user.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO create(UserDTO user) {
        Optional<UserDTO> opt = userRepository.save(user);
        return opt.orElse(null);
    }

    @Override
    public UserDTO update(UserDTO user) {
        return userRepository.update(user);
    }

    @Override
    public UserDTO getById(Long id) {
        Optional<UserDTO> opt = userRepository.getById(id);
        return opt.orElse(null);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }
}
