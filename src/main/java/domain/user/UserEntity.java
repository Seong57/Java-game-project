package domain.user;

import entity.Entity;

public class UserEntity extends Entity {

    private String name;

    private int score;

    public UserEntity(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public UserEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String toRank(int rank){
        return rank + "등  --  " + getName() + "    :    " + getScore() + "점";
    }
}
