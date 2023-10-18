package dio.me.service.impl;

import dio.me.domain.model.User;
import dio.me.domain.repository.UserRepository;
import dio.me.service.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void create(User userToCreate) {
        if(repository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new IllegalArgumentException("This Account number already exists");
        }
        repository.save(userToCreate);
    }
}
