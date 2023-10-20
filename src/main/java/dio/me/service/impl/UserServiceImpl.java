package dio.me.service.impl;

import dio.me.domain.model.User;
import dio.me.domain.repository.UserRepository;
import dio.me.service.UserService;
import dio.me.service.exception.BusinessException;
import dio.me.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class UserServiceImpl implements UserService {

    private static final Long UNCHANGEABLE_USER_ID = 1L;

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        if(user.get() == null) {
            new NotFoundException("User not found");
        }
        return user.get();
    }

    @Transactional
    public void create(User userToCreate) {
        ofNullable(userToCreate).orElseThrow(() -> new BusinessException("User to create must not be null."));
        ofNullable(userToCreate.getAccount()).orElseThrow(() -> new BusinessException("User account must not be null"));
        ofNullable(userToCreate.getCard().getNumber()).orElseThrow(() -> new BusinessException("User card must not be null"));
        this.validateChangeableId(userToCreate.getId(), "created");

        if(repository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new IllegalArgumentException("This Account number already exists");
        }
        if(repository.existsByCardNumber(userToCreate.getCard().getNumber())) {
            throw new IllegalArgumentException("This Card number already exists");
        }
        repository.save(userToCreate);
    }

    @Transactional
    public User update(Integer id, User userToUpdate) {
        this.validateChangeableId(id, "updated");
        User userUpdate = this.findById(id);

        userUpdate.setName(userToUpdate.getName());
        userUpdate.setAccount(userToUpdate.getAccount());
        userUpdate.setCard(userToUpdate.getCard());
        userUpdate.setFeatures(userToUpdate.getFeatures());
        userUpdate.setNews(userToUpdate.getNews());

        return repository.save(userUpdate);
    }

    @Transactional
    public void deletar(Integer id) {
        this.validateChangeableId(id, "deleted");
        User userDelete = this.findById(id);
        repository.delete(userDelete);
    }

    private void validateChangeableId(Integer id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("User with ID %d can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}
