package domain.user.repository;

import db.SimpleDataRepository;
import domain.user.UserEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository extends SimpleDataRepository<UserEntity, Long> {

    private Comparator<UserEntity> sortByScoreDesc = new Comparator<UserEntity>() {
        @Override
        public int compare(UserEntity o1, UserEntity o2) {
            return Integer.compare(o2.getScore(), o1.getScore());
        }
    };

    public List<UserEntity> findAllByScoreGreaterThen(){
        return (ArrayList<UserEntity>) this.findAll()
                .stream()
                .sorted(sortByScoreDesc)
                .collect(Collectors.toList());
    }

}
