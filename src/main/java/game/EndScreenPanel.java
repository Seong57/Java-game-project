package game;

import db.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreenPanel extends JPanel{
    private Repository repository;

    EndScreenPanel endScreenPanel;
    MainFrame mainFrame;
    GamePlayPanel gamePlayPanel;
    ImageIcon backgroundImg;
    ImageIcon monster;

    int width;
    int height;

    JLabel titleLabel;
    JLabel titleLabel2;
    JLabel scoreLabel;

    JButton resumeButton;
    JButton menuButton;
    JButton rankRegisterButton;

    public EndScreenPanel(MainFrame mainFrame, GamePlayPanel gamePlayPanel, Repository repository) {
        this.repository = repository;
        this.gamePlayPanel = gamePlayPanel;
        this.mainFrame = mainFrame;
        backgroundImg = new ImageIcon("src/main/java/img/background.png");
        monster = new ImageIcon("src/main/java/img/monsterbtn.png");
        this.setLayout(new BorderLayout());

        // 타이틀
        titleLabel = new JLabel("바퀴벌레에게 당했다 ㅠㅠ");
        // 레이아웃 관리자 사용 안 함
        titleLabel.setLayout(null);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("", Font.BOLD, 30));
        titleLabel.setForeground(Color.lightGray);
        this.add(titleLabel);


        titleLabel2 = new JLabel("다시 퇴치해볼까?");
        // 레이아웃 관리자 사용 안 함
        titleLabel2.setLayout(null);
        //안의 요소를 가운데 정렬..?
        titleLabel2.setHorizontalAlignment(JLabel.CENTER);
        titleLabel2.setFont(new Font("", Font.BOLD, 30));
        titleLabel2.setForeground(Color.lightGray);
        this.add(titleLabel2);

        // 타이틀
        scoreLabel = new JLabel("SCORE : "+gamePlayPanel.killed );
        // 레이아웃 관리자 사용 안 함
        scoreLabel.setLayout(null);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(new Font("", Font.BOLD, 30));
        scoreLabel.setForeground(Color.lightGray);
        this.add(scoreLabel);

        // 클릭 가능한 큰 몬스터를 생성합니다.
        resumeButton = new JButton("다시 퇴치", monster);
        resumeButton.setLayout(null);
        // 텍스트 위치를 설정
        resumeButton.setHorizontalTextPosition(JButton.CENTER);
        resumeButton.setVerticalTextPosition(JButton.CENTER);
        resumeButton.setForeground(Color.black); // 글자 색상 설정
        resumeButton.setFont(new Font("", Font.BOLD, 18));

        // 버튼의 테두리 및 배경색을 투명하게 설정
        resumeButton.setBorderPainted(false);
        resumeButton.setOpaque(false);
        resumeButton.setContentAreaFilled(false);
        // 버튼에 포커스 테두리가 없도록 설정
        resumeButton.setFocusPainted(false);

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.restartGame();
            }
        });

        // 클릭 가능한 큰 몬스터를 생성합니다.
        menuButton = new JButton("메인 메뉴", monster);
        menuButton.setLayout(null);
        // 텍스트 위치를 설정
        menuButton.setHorizontalTextPosition(JButton.CENTER);
        menuButton.setVerticalTextPosition(JButton.CENTER);
        menuButton.setForeground(Color.black); // 글자 색상 설정
        menuButton.setFont(new Font("", Font.BOLD, 18));

        // 버튼의 테두리 및 배경색을 투명하게 설정
        menuButton.setBorderPainted(false);
        menuButton.setOpaque(false);
        menuButton.setContentAreaFilled(false);
        // 버튼에 포커스 테두리가 없도록 설정
        menuButton.setFocusPainted(false);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.menu();
            }
        });


        // 클릭 가능한 큰 몬스터를 생성합니다.
        rankRegisterButton = new JButton("점수 등록", monster);
        rankRegisterButton.setLayout(null);
        // 텍스트 위치를 설정
        rankRegisterButton.setHorizontalTextPosition(JButton.CENTER);
        rankRegisterButton.setVerticalTextPosition(JButton.CENTER);
        rankRegisterButton.setForeground(Color.black); // 글자 색상 설정
        rankRegisterButton.setFont(new Font("", Font.BOLD, 18));

        // 버튼의 테두리 및 배경색을 투명하게 설정
        rankRegisterButton.setBorderPainted(false);
        rankRegisterButton.setOpaque(false);
        rankRegisterButton.setContentAreaFilled(false);
        // 버튼에 포커스 테두리가 없도록 설정
        rankRegisterButton.setFocusPainted(false);

        rankRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.rankRegister(gamePlayPanel.killed);
            }
        });



        // 큰 몬스터를 버튼 패널에 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // 버튼 패널의 배경을 투명하게
        buttonPanel.add(resumeButton);
        buttonPanel.add(menuButton);
        buttonPanel.add(rankRegisterButton);

        this.add(buttonPanel, BorderLayout.CENTER);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg.getImage(), 0, 0, null);
        width = this.getWidth();
        height = this.getHeight();
        // titleLabel 위치 및 크기 설정
        titleLabel.setBounds(width/2-200, 50, titleLabel.getPreferredSize().width, titleLabel.getPreferredSize().height);
        titleLabel2.setBounds(width/2-200, 100, titleLabel.getPreferredSize().width, titleLabel.getPreferredSize().height);
        scoreLabel.setText("SCORE : " + gamePlayPanel.killed);
        scoreLabel.setBounds(width/2-95, 600, scoreLabel.getPreferredSize().width, scoreLabel.getPreferredSize().height);
        resumeButton.setBounds(width/2-90, 150, resumeButton.getPreferredSize().width, resumeButton.getPreferredSize().height);
        menuButton.setBounds(width/2-90, 300, menuButton.getPreferredSize().width, menuButton.getPreferredSize().height);
        rankRegisterButton.setBounds(width/2-90, 450, rankRegisterButton.getPreferredSize().width, rankRegisterButton.getPreferredSize().height);
    }

}
