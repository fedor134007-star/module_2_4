package service.user;

import model.User;
import repository.hibernate.user.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        Optional<User> opt = userRepository.save(user);
        return opt.orElse(null);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public User getById(Long id) {
        Optional<User> opt = userRepository.getById(id);
        return opt.orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }
}
