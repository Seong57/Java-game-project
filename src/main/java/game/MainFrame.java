package game;


import user.db.UserRepository;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    private UserRepository userRepository = new UserRepository();


    private GamePlayPanel gamePlayPanel;
    private MainMenuPanel mainMenuPanel;

    private EndScreenPanel endScreenPanel;
    private RankRegisterPanel rankRegisterPanel;
    private HelpTapPanel helpTapPanel;


    JPanel bottomPanel;
    static JLabel scoreLabel;
    static JProgressBar healthGauge = new JProgressBar();

    static JLabel goldLabel;


    public MainFrame(){
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("바퀴벌레 서바이벌!");
        setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        // 몬스터 메인 캐릭터 총알 등 실질적 게임이 이루어지는 패널
        gamePlayPanel = new GamePlayPanel(this, userRepository);
        mainMenuPanel = new MainMenuPanel(this,userRepository);
        //endScreenPanel = new EndScreenPanel(this, gamePlayPanel, userRepository);
        helpTapPanel = new HelpTapPanel(this, gamePlayPanel, userRepository);
        rankRegisterPanel = new RankRegisterPanel(this, gamePlayPanel.killed, userRepository);

        this.getContentPane().add(mainMenuPanel, BorderLayout.CENTER);

        // 바텀 패널 생성은 메소드로 빼둠
        /*bottomPanel = new JPanel();

        // 하단 패널 설정
        bottomPanel.setBackground(Color.GRAY);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(Window.getWindows().length, 50));

        healthGauge.setPreferredSize(new Dimension(150, 30));  // 체력 게이지의 크기 설정
        healthGauge.setForeground(Color.RED);
        healthGauge.setMinimum(0);
        healthGauge.setMaximum(1000);
        healthGauge.setValue(1000);
        healthGauge.setStringPainted(true);
        healthGauge.setString("100%");

        // 체력 게이지와 SCORE 레이블을 각각 JPanel에 추가합니다.
        JPanel healthPanel = new JPanel();
        healthPanel.setBackground(Color.GRAY);
        healthPanel.add(healthGauge);


        // 점수 표시를 위한 레이블 생성 및 초기 설정
        scoreLabel = new JLabel("SCORE: 0   ");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        scoreLabel.setForeground(Color.WHITE);

        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.GRAY);
        scorePanel.setLayout(new BorderLayout());
        scorePanel.add(scoreLabel, BorderLayout.CENTER);

        // goldLabel 설정

        goldLabel = new JLabel("   GOLD: 0");
        goldLabel.setFont(new Font("Arial", Font.BOLD, 25));
        goldLabel.setForeground(Color.WHITE);


        // 체력 게이지와 SCORE 레이블이 있는 JPanel들을 BorderLayout에 배치
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(healthPanel, BorderLayout.CENTER); // 체력 게이지를 가운데 (중앙)에 배치
        bottomPanel.add(scorePanel, BorderLayout.EAST);    // 점수 레이블 오른쪽 (동쪽) 배치
        bottomPanel.add(goldLabel, BorderLayout.WEST);  // 왼쪽 간격*/

        this.setVisible(true);
    }

    public static void setGold(int gold){
        goldLabel.setText("   GOLD: " + gold);
    }

    public static void setScore(int killed){
        scoreLabel.setText("SCORE: "+killed + "   ");
    }

    public static void updateHealthGauge(int health) {
        int percentage = (int) (((double) health / 500) * 100);
        healthGauge.setValue(health);
        healthGauge.setString(percentage+ "%");
    }

    public void startGame() {
        createBottomPanel();
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().remove(mainMenuPanel);
        gamePlayPanel = new GamePlayPanel(this, userRepository);
        getContentPane().add(gamePlayPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        gamePlayPanel.timer.start();
        gamePlayPanel.monsterRespawnTimer.start();
        gamePlayPanel.monsterRespawnFullRangeTimer.start();
        gamePlayPanel.requestFocus();
        setFocusable(true);
    }
    public void menu(UserRepository userRepository){
        this.userRepository = userRepository;
        getContentPane().removeAll();
        mainMenuPanel = new MainMenuPanel(this, userRepository);
        getContentPane().add(mainMenuPanel);
        revalidate();
        repaint();
    }
    public void menu(){
        getContentPane().removeAll();
        mainMenuPanel = new MainMenuPanel(this, userRepository);
        getContentPane().add(mainMenuPanel);
        revalidate();
        repaint();
    }
    public void rankRegister(int killed) {
        getContentPane().removeAll();
        rankRegisterPanel = new RankRegisterPanel(this, killed, userRepository);
        getContentPane().add(rankRegisterPanel);
        revalidate();
        repaint();
    }
    public void restartGame() {
        getContentPane().removeAll();
        gamePlayPanel = new GamePlayPanel(this, userRepository);
        createBottomPanel();
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(gamePlayPanel);
        getContentPane().remove(endScreenPanel);
        revalidate();
        gamePlayPanel.timer.start();
        gamePlayPanel.monsterRespawnTimer.start();
        gamePlayPanel.monsterRespawnFullRangeTimer.start();
        gamePlayPanel.requestFocus();
        repaint();
    }



    public void endGame(EndScreenPanel endScreenPanel){
        getContentPane().remove(bottomPanel);
        getContentPane().remove(gamePlayPanel);
        getContentPane().add(endScreenPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        gamePlayPanel.timer.stop();
        gamePlayPanel.monsterRespawnTimer.stop();
        gamePlayPanel.monsterRespawnFullRangeTimer.stop();

    }

    public void pause(){
        gamePlayPanel.add(helpTapPanel);
        gamePlayPanel.timer.stop();
        gamePlayPanel.monsterRespawnTimer.stop();
        gamePlayPanel.monsterRespawnFullRangeTimer.stop();

        helpTapPanel.setVisible(true);
        helpTapPanel.requestFocus();
        helpTapPanel.setFocusable(true);
        helpTapPanel.repaint();
    }

    public void resume(){
        gamePlayPanel.timer.start();
        gamePlayPanel.monsterRespawnTimer.start();
        gamePlayPanel.monsterRespawnFullRangeTimer.start();
        helpTapPanel.setVisible(false);
        gamePlayPanel.setFocusable(true);
        gamePlayPanel.requestFocus();
        gamePlayPanel.repaint();
    }

    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
    }


    public void createBottomPanel(){
        bottomPanel = new JPanel();

        // 하단 패널 설정
        bottomPanel.setBackground(Color.GRAY);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(Window.getWindows().length, 50));

        healthGauge.setPreferredSize(new Dimension(150, 30));  // 체력 게이지의 크기 설정
        healthGauge.setForeground(Color.RED);
        healthGauge.setMinimum(0);
        healthGauge.setMaximum(500);
        healthGauge.setValue(500);
        healthGauge.setStringPainted(true);
        healthGauge.setString("100%");

        // 체력 게이지와 SCORE 레이블을 각각 JPanel에 추가합니다.
        JPanel healthPanel = new JPanel();
        healthPanel.setBackground(Color.GRAY);
        healthPanel.add(healthGauge);


        // 점수 표시를 위한 레이블 생성 및 초기 설정
        scoreLabel = new JLabel("SCORE: 0   ");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));
        scoreLabel.setForeground(Color.WHITE);

        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.GRAY);
        scorePanel.setLayout(new BorderLayout());
        scorePanel.add(scoreLabel, BorderLayout.CENTER);

        // goldLabel 설정

        goldLabel = new JLabel("   GOLD: 0");
        goldLabel.setFont(new Font("Arial", Font.BOLD, 25));
        goldLabel.setForeground(Color.WHITE);


        // 체력 게이지와 SCORE 레이블이 있는 JPanel들을 BorderLayout에 배치
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(healthPanel, BorderLayout.CENTER); // 체력 게이지를 가운데 (중앙)에 배치
        bottomPanel.add(scorePanel, BorderLayout.EAST);    // 점수 레이블 오른쪽 (동쪽) 배치
        bottomPanel.add(goldLabel, BorderLayout.WEST);  // 왼쪽 간격
    }


}
