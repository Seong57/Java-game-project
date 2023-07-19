package user.db;

import db.SimpleDataRepository;
import user.model.UserEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository extends SimpleDataRepository<UserEntity, Long> {

    private Comparator<UserEntity> sort = new Comparator<UserEntity>() {
        @Override
        public int compare(UserEntity o1, UserEntity o2) {
            return Integer.compare(o2.getScore(), o1.getScore());
        }
    };

    public ArrayList<UserEntity> findAllScoreGreaterThen(){
        return (ArrayList<UserEntity>) this.findAll()
                .stream()
                .sorted(sort)
                .collect(Collectors.toList());
    }

}
