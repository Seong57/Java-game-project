package game;

import user.model.UserEntity;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankingDialog extends JDialog {

    int rank = 1;

    public RankingDialog(JFrame parent, List<UserEntity> userList) {
        super(parent, "랭킹", true); // 부모 프레임, 제목, 모달 설정

        // 랭킹 정보 표시를 구현하세요 (JLabel, JList 등을 사용하여 랭킹 정보를 표시할 수 있습니다.)
        DefaultListModel<String> listModel = new DefaultListModel<>();

        /*
        for(UserEntity user : userList){
            listModel.addElement(rank + "등  --  " + user.getName() + "    :    " + user.getScore() + "점");
            rank++;
        }
        */

        userList.forEach(it -> {
            listModel.addElement(it.toRank(rank));
            rank++;
        });

        JList<String> rankingList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(rankingList);

        add(scrollPane);
        setSize(300, 400); // 창 크기 설정
        setLocationRelativeTo(parent); // 부모 프레임 가운데에 랭킹 창이 나타나도록 위치 설정
    }
}
