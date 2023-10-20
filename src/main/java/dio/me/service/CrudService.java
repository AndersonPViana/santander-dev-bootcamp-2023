package dio.me.service;

import java.util.List;

public interface CrudService<ID, T> {
    List<T> findAll();
    T findById(ID id);
    void create(T t);
    T update(ID id, T t);
    void deletar(ID id);



}
