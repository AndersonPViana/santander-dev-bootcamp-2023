package dio.me.service;

import dio.me.domain.model.User;

public interface UserService {
    User findById(Integer id);

    void create(User user);
}
