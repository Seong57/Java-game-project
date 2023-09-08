package db;

import java.util.List;

public interface Repository<T, ID> {

    //create
    T save(T data);
    //read
    List<T> findAll();

    List<T> findAllByScoreGreaterThen();

}
