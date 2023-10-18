package dio.me.service.impl;

import dio.me.domain.model.User;
import dio.me.domain.repository.UserRepository;
import dio.me.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void create(User userToCreate) {
        if(repository.existeByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new IllegalArgumentException("This Account number already exists");
        }
        repository.save(userToCreate);
    }
}
