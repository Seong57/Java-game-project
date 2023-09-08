package db;

import entity.Entity;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SimpleDataRepository<T extends Entity, ID extends Long> implements Repository<T, ID>{

    private List<T> dataList = new ArrayList<T>();

    private static long index = 0;

    private Comparator<T> sort = new Comparator<T>() {
        @Override
        public int compare(T o1, T o2) {
            return Long.compare(o1.getId(), o2.getId());
        }
    };


    //create

    @Override
    public T save(T data) {

        if (Objects.isNull(data)){
            throw new RuntimeException("data is null");
        }

        data.setId(index);

        dataList.add(data);

        index++;
        return data;
    }


    @Override
    public List<T> findAll() {
        return dataList.stream()
                .sorted(sort)
                .collect(Collectors.toList());
    }

}
