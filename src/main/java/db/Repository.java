package db;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    //create
    T save(T data);
    //read
    List<T> findAll();
}
