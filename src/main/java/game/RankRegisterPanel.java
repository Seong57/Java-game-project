package game;

import user.db.UserRepository;
import user.model.UserEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RankRegisterPanel extends JPanel{

    private final UserRepository userRepository;

    MainFrame mainFrame;
    GamePlayPanel gamePlayPanel;
    ImageIcon backgroundImg;
    ImageIcon monster;

    int width;
    int height;
    int killed;

    JLabel titleLabel;
    JLabel titleLabel2;
    JLabel scoreLabel;

    JButton registerBtn;
    JButton menuButton;
    JButton exitButton;

    JTextField nameField;

    public RankRegisterPanel(MainFrame mainFrame, int killed, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.killed = killed;

        this.gamePlayPanel = gamePlayPanel;
        this.mainFrame = mainFrame;

        backgroundImg = new ImageIcon("src/main/java/img/배경화면.png");
        monster = new ImageIcon("src/main/java/img/큰몬스터.png");
        this.setLayout(new BorderLayout());

        // 이름 필드
        nameField = new JTextField("이름 입력");
        nameField.setPreferredSize(new Dimension(200, 35));
        nameField.setLayout(null);
        nameField.setHorizontalAlignment(JLabel.CENTER);
        nameField.setFont(new Font("", Font.BOLD, 30));
        nameField.setForeground(Color.black);
        nameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                nameField.setText("");
                repaint();
            }
        });
        this.add(nameField);

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
        scoreLabel = new JLabel("SCORE : "+killed);
        // 레이아웃 관리자 사용 안 함
        scoreLabel.setLayout(null);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(new Font("", Font.BOLD, 30));
        scoreLabel.setForeground(Color.lightGray);
        this.add(scoreLabel);

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
                mainFrame.menu(userRepository);
            }
        });

        // 클릭 가능한 큰 몬스터를 생성합니다.
        registerBtn = new JButton("등록", monster);
        registerBtn.setLayout(null);
        // 텍스트 위치를 설정
        registerBtn.setHorizontalTextPosition(JButton.CENTER);
        registerBtn.setVerticalTextPosition(JButton.CENTER);
        registerBtn.setForeground(Color.black); // 글자 색상 설정
        registerBtn.setFont(new Font("", Font.BOLD, 18));

        // 버튼의 테두리 및 배경색을 투명하게 설정
        registerBtn.setBorderPainted(false);
        registerBtn.setOpaque(false);
        registerBtn.setContentAreaFilled(false);
        // 버튼에 포커스 테두리가 없도록 설정
        registerBtn.setFocusPainted(false);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO 점수 등록하기
                String userName = nameField.getText();
                int score = killed;

                UserEntity user = new UserEntity(userName, score);

                user.setName(userName);
                user.setScore(killed);

                userRepository.save(user);

                System.out.println(userRepository.findAll().get(0).getName());
                mainFrame.menu(userRepository);
            }
        });


        // 클릭 가능한 큰 몬스터를 생성합니다.
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



        // 큰 몬스터를 버튼 패널에 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // 버튼 패널의 배경을 투명하게
        buttonPanel.add(menuButton);
        buttonPanel.add(registerBtn);
        buttonPanel.add(exitButton);

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
        scoreLabel.setText("SCORE : " + killed);
        scoreLabel.setBounds(width/2-95, 600, scoreLabel.getPreferredSize().width, scoreLabel.getPreferredSize().height);
        menuButton.setBounds(width/2-90, 150, menuButton.getPreferredSize().width, menuButton.getPreferredSize().height);
        registerBtn.setBounds(width/2+100, 300, registerBtn.getPreferredSize().width, registerBtn.getPreferredSize().height);
        exitButton.setBounds(width/2-90, 450, exitButton.getPreferredSize().width, exitButton.getPreferredSize().height);


        nameField.setBounds(width/2-110, 350, nameField.getPreferredSize().width, nameField.getPreferredSize().height);
    }
}
