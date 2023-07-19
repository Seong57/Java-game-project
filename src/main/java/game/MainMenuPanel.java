package game;

import user.db.UserRepository;
import user.model.UserEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainMenuPanel extends JPanel {

    private final UserRepository userRepository;

    ImageIcon backgroundImg;
    ImageIcon monster;

    int width;
    int height;

    JLabel titleLabel;
    JLabel titleLabel2;

    JButton startButton;
    JButton exitButton;
    JButton rankingButton;

    public MainMenuPanel(MainFrame mainFrame, UserRepository userRepository) {

        this.userRepository = userRepository;

        backgroundImg = new ImageIcon("src/main/java/img/배경화면.png");
        monster = new ImageIcon("src/main/java/img/큰몬스터.png");
        this.setLayout(new BorderLayout());

        // 타이틀
        titleLabel = new JLabel("초원에 바퀴벌레가 나타났다!");
        // 레이아웃 관리자 사용 안 함
        titleLabel.setLayout(null);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("", Font.BOLD, 30));
        titleLabel.setForeground(Color.lightGray);
        this.add(titleLabel);


        titleLabel2 = new JLabel("바퀴벌레를 퇴치하자!");
        // 레이아웃 관리자 사용 안 함
        titleLabel2.setLayout(null);
        //안의 요소를 가운데 정렬..?
        titleLabel2.setHorizontalAlignment(JLabel.CENTER);
        titleLabel2.setFont(new Font("", Font.BOLD, 30));
        titleLabel2.setForeground(Color.lightGray);
        this.add(titleLabel2);

        // 클릭 가능한 큰 몬스터 버튼 를 생성
        startButton = new JButton("게임 시작", monster);
        startButton.setLayout(null);
        // 텍스트 위치를 설정
        startButton.setHorizontalTextPosition(JButton.CENTER);
        startButton.setVerticalTextPosition(JButton.CENTER);
        startButton.setForeground(Color.black); // 글자 색상 설정
        startButton.setFont(new Font("", Font.BOLD, 18));

        // 버튼의 테두리 및 배경색을 투명하게 설정
        startButton.setBorderPainted(false);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        // 버튼에 포커스 테두리가 없도록 설정
        startButton.setFocusPainted(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.startGame();
            }
        });


        // 클릭 가능한 큰 몬스터 버튼 를 생성
        exitButton = new JButton("종료", monster);
        exitButton.setLayout(null);
        // 텍스트 위치를 설정
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setVerticalTextPosition(JButton.CENTER);
        exitButton.setForeground(Color.black); // 글자 색상 설정
        exitButton.setFont(new Font("", Font.BOLD, 18));

        // 버튼의 테두리 및 배경색을 투명하게 설정
        exitButton.setBorderPainted(false);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        // 버튼에 포커스 테두리가 없도록 설정
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // 클릭 가능한 큰 몬스터 버튼 를 생성
        rankingButton = new JButton("랭킹", monster);
        rankingButton.setLayout(null);
        rankingButton.setHorizontalTextPosition(JButton.CENTER);    // 텍스트 위치를 설정
        rankingButton.setVerticalTextPosition(JButton.CENTER);
        rankingButton.setForeground(Color.black);                   // 글자 색상 설정
        rankingButton.setFont(new Font("", Font.BOLD, 18));
        rankingButton.setBorderPainted(false);  // 버튼의 테두리 및 배경색을 투명하게 설정
        rankingButton.setOpaque(false);
        rankingButton.setContentAreaFilled(false);
        rankingButton.setFocusPainted(false);   // 버튼에 포커스 테두리가 없도록 설정

        rankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //랭킹 보여주기
                List<UserEntity> userList = new ArrayList<UserEntity>();
                userList = userRepository.findAllScoreGreaterThen();

                RankingDialog rankingDialog = new RankingDialog(mainFrame, userList);
                rankingDialog.setVisible(true);
            }
        });



        // 큰 몬스터를 버튼 패널에 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // 버튼 패널의 배경을 투명하게
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(rankingButton);

        this.add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg.getImage(), 0, 0, null);
        width = this.getWidth();
        height = this.getHeight();
        // titleLabel 위치 및 크기 설정
        titleLabel.setBounds(width/2-220, 50, titleLabel.getPreferredSize().width, titleLabel.getPreferredSize().height);
        titleLabel2.setBounds(width/2-220, 100, titleLabel.getPreferredSize().width, titleLabel.getPreferredSize().height);
        startButton.setBounds(width/2-90, 200, startButton.getPreferredSize().width, startButton.getPreferredSize().height);
        exitButton.setBounds(width/2-90, 350, exitButton.getPreferredSize().width, exitButton.getPreferredSize().height);
        rankingButton.setBounds(width/2-90, 500, rankingButton.getPreferredSize().width, rankingButton.getPreferredSize().height);
    }





}
