package game;

import db.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpTapPanel extends JPanel {

    private Repository repository;
    MainFrame mainFrame;
    GamePlayPanel gamePlayPanel;
    ImageIcon monster = new ImageIcon("src/main/java/img/monsterbtn.png");

    float alpha = 1.0f;

    int width;
    int height;

    JLabel titleLabel;

    JButton startButton;
    JButton exitButton;


    public HelpTapPanel(MainFrame mainFrame, GamePlayPanel gamePlayPanel, Repository repository){
        this.repository = repository;
        this.mainFrame = mainFrame;
        this.gamePlayPanel = gamePlayPanel;

        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1200, 800));

        titleLabel = new JLabel("바퀴벌레를 퇴치하자!");
        // 레이아웃 관리자 사용 안 함
        titleLabel.setLayout(null);
        //안의 요소를 가운데 정렬..?
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("", Font.BOLD, 30));
        titleLabel.setForeground(Color.lightGray);
        this.add(titleLabel);

        // 클릭 가능한 큰 몬스터를 생성합니다.
        startButton = new JButton("게임 재개", monster);
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
                setVis(false);
                gamePlayPanel.setFocusable(true);
                gamePlayPanel.requestFocus();
                gamePlayPanel.timer.start();
                gamePlayPanel.monsterRespawnTimer.start();
                gamePlayPanel.monsterRespawnFullRangeTimer.start();
                gamePlayPanel.isStop = false;
                gamePlayPanel.repaint();
            }
        });


        // 클릭 가능한 큰 몬스터를 생성합니다.gamePlayPanel
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
                System.out.println("버튼 눌림");
            }
        });



        // 큰 몬스터를 버튼 패널에 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // 버튼 패널의 배경을 투명하게
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        this.add(buttonPanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(1200, 800));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        width = mainFrame.getWidth();
        height = mainFrame.getHeight();
        System.out.println("너비 , 높이 : " + width + ", "+height);
        this.setPreferredSize(new Dimension(width, height));
        // titleLabel 위치 및 크기 설정
        titleLabel.setBounds(width/2-160, 100, titleLabel.getPreferredSize().width, titleLabel.getPreferredSize().height);
        startButton.setBounds(width/2-90, 200, startButton.getPreferredSize().width, startButton.getPreferredSize().height);
        exitButton.setBounds(width/2-90, 400, exitButton.getPreferredSize().width, exitButton.getPreferredSize().height);
    }

    public void setVis(boolean vis){
        this.setVisible(vis);
        this.setFocusable(vis);
    }


}
