package game;

import sound.Sound;
import user.db.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GamePlayPanel extends JPanel implements ActionListener{

    private UserRepository userRepository;


    MainFrame mainFrame;
    HelpTapPanel helpTapPanel;
    EndScreenPanel endScreenPanel;


    MainCharacter mainCharacter; // 메인 캐릭터 클래스



    //총알 클래스
    Bullet bullet;
    // 총알 이미지
    ImageIcon bulletImage = new ImageIcon("src/main/java/img/총알.png"); //TODO 이미지 바꾸기
    // 총알들의 리스트
    ArrayList<Bullet> bullets = new ArrayList<>();



    // 메인 캐릭터
    ImageIcon image = new ImageIcon("src/main/java/img/메인캐릭터.png");
    // 배경화면
    ImageIcon backgroundImage;
    // 몬스터
    ImageIcon bigMonsterImage = new ImageIcon("src/main/java/img/몬스터1.png");  //TODO 이미지 바꾸기
    ImageIcon bigMonsterImage2 = new ImageIcon("src/main/java/img/몬스터2.png");  //TODO 이미지 바꾸기
    ImageIcon monsterImage = new ImageIcon("src/main/java/img/작은몬스터1.png");  //TODO 이미지 바꾸기
    ImageIcon monsterImage2 = new ImageIcon("src/main/java/img/작은몬스터2.png");    //TODO 이미지 바꾸기




    // 메인 캐릭터 움직임을 위한 타이머
    Timer timer;
    Timer autoShootTimer;
    // 몬스터 리스폰을 위한 타이머
    Timer monsterRespawnTimer;
    Timer bigMonsterRespawnTimer;
    Timer monsterRespawnFullRangeTimer;


    // 메인 캐릭터 체력 감소분
    int hpDecreaseAmount = 1;
    //처치한 몬스터의 수
    int killed = 0;
    int backgroundWidth;
    int backgroundHeight;
    int img_x = 1080 / 2 - 61 / 2, img_y = 720 / 2 - 61 / 2;
    int cam_x = 0, cam_y = 0;
    int mouseX;
    int mouseY;
    public int monsterRespawnTime = 1000;
    // 키 이벤트를 위한 boolean 배열
    boolean[] keyState = new boolean[256];
    // F1 키를 이용한 도움말 기능을 위한 타이머 스탑 TODO 화면의 모든 움직임을 멈추고 도움말 탭을 띄워야함.
    boolean isStop = false;

    // 몬스터들의 리스트
    ArrayList<Monster> monsters = new ArrayList<>();



    public void createBullet() {
        int bulletX = img_x + image.getIconWidth() / 2 - bulletImage.getIconWidth() / 2;
        int bulletY = img_y + image.getIconHeight() / 2 - bulletImage.getIconHeight() / 2;
        double dx = mouseX - (bulletX - cam_x);
        double dy = mouseY - (bulletY - cam_y);
        double bulletAngle = Math.atan2(dy, dx);

        bullets.add(new Bullet(bulletImage, bulletX, bulletY, bulletAngle));
        Sound shotSound = new Sound("src/main/java/sound/총소리.mp3", false);
        shotSound.start();
        if(mainCharacter.hp <= 0){
            shotSound.close();
        }
    }

    public void clickHelpTap(boolean isStop){
        if(!isStop){
            timer.stop();
            monsterRespawnTimer.stop();
            monsterRespawnFullRangeTimer.stop();

            helpTapPanel.setVisible(true);
            helpTapPanel.requestFocus();
            helpTapPanel.setFocusable(true);
            helpTapPanel.repaint();
            this.isStop = true;
        }
    }

    public GamePlayPanel(MainFrame mainFrame, UserRepository userRepository) {

        this.userRepository = userRepository;

        endScreenPanel = new EndScreenPanel(mainFrame, this, userRepository);

        helpTapPanel = new HelpTapPanel(mainFrame, this, userRepository);

        this.add(helpTapPanel);
        helpTapPanel.setVisible(false);


        this.mainFrame = mainFrame;


        mainCharacter = new MainCharacter(image, 500);

        backgroundImage = new ImageIcon("src/main/java/img/배경화면.png");

        backgroundWidth = backgroundImage.getIconWidth();
        backgroundHeight = backgroundImage.getIconHeight();

        timer = new Timer(10, this); // 20 밀리초마다 위치 업데이트
        //timer.start();


        Timer autoShootTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    createBullet();
            }
        });
        {
            autoShootTimer.setInitialDelay(100);
            autoShootTimer.setRepeats(true);
            autoShootTimer.stop(); // 초기에는 멈춘 상태
        }

        monsterRespawnFullRangeTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int monsterX = 0;

                for(int i = 0; i < 50; i++){
                    monsters.add(new Monster(monsterImage, monsterX, 0, 100));
                    monsterX += 300;
                }
                monsterX = 0;
                for(int i = 0; i < 50; i++){
                    monsters.add(new Monster(monsterImage, monsterX, 2160, 100));
                    monsterX += 300;
                }
            }
        });


        monsterRespawnTimer = new Timer(monsterRespawnTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int monsterX = (int)(Math.random() * (backgroundWidth - monsterImage.getIconWidth()));
                int monsterY = (int)(Math.random() * (backgroundHeight - monsterImage.getIconHeight()));
                int monsterHP = 100; // 체력을 100으로 초기화
                monsters.add(new Monster(monsterImage, monsterX, monsterY, monsterHP));
                repaint();
            }
        });
        //monsterRespawnTimer.start();

        bigMonsterRespawnTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int monsterX = (int)(Math.random() * (backgroundWidth - monsterImage.getIconWidth()));
                int monsterY = (int)(Math.random() * (backgroundHeight - monsterImage.getIconHeight()));
                int monsterHP = 100; // 체력을 100으로 초기화
                monsters.add(new Monster(monsterImage, monsterX, monsterY, monsterHP));
                repaint();
            }
        });


        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!isStop) {
                    createBullet();
                    autoShootTimer.restart(); // 마우스 버튼을 누르면 Timer 시작
                }
            }


            @Override
            public void mouseReleased(MouseEvent e) {
                autoShootTimer.stop(); // 마우스 버튼을 놓으면 Timer 멈춤
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        // 캐릭터가 마우스 방향을 바라볼 수 있도록 마우스 포인터의 좌표를 가져옴
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyState[e.getKeyCode()] = true;
                // 게임 일시정지, 시작 기능 TODO F1키 도움말 기능 추가
                if (keyState[KeyEvent.VK_F1]){
                    clickHelpTap(isStop);
                    keyState[e.getKeyCode()] = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyState[e.getKeyCode()] = false;
            }
        });

        this.requestFocus();
        setFocusable(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 8; // 캐릭터 및 카메라 이동 속도를 조절할 수 있는 변수
        int new_img_x = img_x;
        int new_img_y = img_y;

        if (keyState[KeyEvent.VK_LEFT] || keyState[KeyEvent.VK_A]) {
            new_img_x -= speed;
        }
        if (keyState[KeyEvent.VK_RIGHT] || keyState[KeyEvent.VK_D]) {
            new_img_x += speed;
        }
        if (keyState[KeyEvent.VK_UP] || keyState[KeyEvent.VK_W]) {
            new_img_y -= speed;
        }
        if (keyState[KeyEvent.VK_DOWN] || keyState[KeyEvent.VK_S]) {
            new_img_y += speed;
        }

        // 캐릭터가 배경화면 내에서 이동하는지 확인
        if (new_img_x >= 0 && new_img_x + image.getIconWidth() <= backgroundWidth) {
            img_x = new_img_x;
        }
        if (new_img_y >= 0 && new_img_y + image.getIconHeight() <= backgroundHeight) {
            img_y = new_img_y;
        }

        // 카메라 위치 업데이트
        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int centerX = img_x + image.getIconWidth() / 2;
        int centerY = img_y + image.getIconHeight() / 2;
        int targetCamX = centerX - windowWidth / 2;
        int targetCamY = centerY - windowHeight / 2;

        if (targetCamX < 0) {
            targetCamX = 0;
        } else if (targetCamX + windowWidth > backgroundWidth) {
            targetCamX = backgroundWidth - windowWidth;
        }

        if (targetCamY < 0) {
            targetCamY = 0;
        } else if (targetCamY + windowHeight > backgroundHeight) {
            targetCamY = backgroundHeight - windowHeight;
        }

        cam_x = targetCamX;
        cam_y = targetCamY;

        for (Bullet bullet : bullets) {
            bullet.move();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(backgroundImage.getImage(), -cam_x, -cam_y, null);


        mainCharacter.moveAndRotateAndDraw(g2d);
        // 몬스터와 메인 캐릭터의 충돌 처리
        // 몬스터와 메인 캐릭터의 거리에 따라 체력 감소
        for (Monster monster : monsters) {
            if (mainCharacter.distance(monster) < 50) {
                mainCharacter.decreaseHP(hpDecreaseAmount);
                MainFrame.updateHealthGauge(mainCharacter.getHp()); // 체력 게이지 업데이트

                //체력이 0이 되는 경우 게임 패배 화면으로 전환
                if(mainCharacter.hp == 0){
                    mainFrame.endGame(endScreenPanel);
                }
                break;
            }
        }
        // 캐릭터 위치 업데이트 후 몬스터들이 캐릭터를 향해 움직이도록 함
        for (Monster monster : monsters) {
            monster.moveTowards(img_x, img_y);

            int drawX = monster.x - cam_x;
            int drawY = monster.y - cam_y;

            // 회전 변환을 적용하기 위해 임시 Graphics2D 객체 생성
            Graphics2D monsterG2d = (Graphics2D) g.create();

            AffineTransform transform = new AffineTransform();
            transform.rotate(monster.angle,
                    drawX + (double) monster.image.getIconWidth() / 2,
                    drawY + (double) monster.image.getIconHeight() / 2);
            monsterG2d.setTransform(transform);

            monsterG2d.drawImage(monster.image.getImage(), drawX, drawY, null);

            // 임시 Graphics2D 객체 해제
            monsterG2d.dispose();
        }


        for (Bullet bullet : bullets) {
            Graphics2D bulletG2d = (Graphics2D) g.create();

            int drawX = bullet.x - cam_x;
            int drawY = bullet.y - cam_y;

            AffineTransform transform = new AffineTransform();
            transform.rotate(bullet.angle, drawX + (double) bullet.image.getIconWidth() /2, drawY+(double) bullet.image.getIconHeight()/2);
            bulletG2d.setTransform(transform);

            bulletG2d.drawImage(bullet.image.getImage(), drawX, drawY, null);

            bulletG2d.dispose();
        }

        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Monster> monstersToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.move();

            // 총알이 화면 밖으로 나간 경우, 제거 대상으로 추가
            if (bullet.isOutOfBound()) {
                bulletsToRemove.add(bullet);
            }

            // 총알이 몬스터와 충돌한 경우, 제거 대상으로 추가
            for (Monster monster : monsters) {
                if (monster.isCollidedWithBullet(bullet)) {
                    bulletsToRemove.add(bullet);
                    //monstersToRemove.add(monster);
                    if((monster.hp - mainCharacter.damage) <= 0){
                        mainCharacter.gold += mainCharacter.goldIncrement;
                        monster.hp -= mainCharacter.damage;
                        ++killed;
                        MainFrame.setScore(killed);
                        MainFrame.setGold(mainCharacter.gold);
                        monstersToRemove.add(monster);
                    }else {
                        monster.hp -= mainCharacter.damage;
                    }
                    break;
                }
            }
        }

        // 제거 대상 총알과 몬스터를 리스트에서 삭제
        bullets.removeAll(bulletsToRemove);
        monsters.removeAll(monstersToRemove);

    }


    class MainCharacter{
        ImageIcon image;

        int damage = 100;

        int gold = 0;
        int goldIncrement = 10;

        int hp;
        double angle;

        //캐릭터의 중심 좌표(배경화면 이동을 고려한 배경화면 기준의 실제 좌표)
        int characterCenterX;
        int characterCenterY;
        int characterDrawX;
        int characterDrawY;

        public int getHp() {
            return hp;
        }

        public MainCharacter(ImageIcon image, int hp) {
            this.image = image;
            this.hp = hp;
        }

        void moveAndRotateAndDraw(Graphics2D g2d){
            characterDrawX = getWidth() / 2 - image.getIconWidth() / 2;
            characterDrawY = getHeight() / 2 - image.getIconHeight() / 2;
            if (cam_x == 0) {
                characterDrawX = img_x;
            }
            if (cam_x + getWidth() >= backgroundWidth) {
                characterDrawX = img_x - (backgroundWidth - getWidth());
            }
            if (cam_y == 0) {
                characterDrawY = img_y;
            }
            if (cam_y + getHeight() >= backgroundHeight) {
                characterDrawY = img_y - (backgroundHeight - getHeight());
            }

            // 캐릭터의 중심 좌표
            characterCenterX = characterDrawX + image.getIconWidth() / 2;
            characterCenterY = characterDrawY + image.getIconHeight() / 2;

            // 마우스와 캐릭터의 중심 사이의 각도 계산
            double dx = mouseX - characterCenterX;
            double dy = mouseY - characterCenterY;
            angle = Math.atan2(dy, dx);

            // 회전 및 이미지 그리기를 위한 임시 Graphics2D 객체 생성
            Graphics2D characterG2d = (Graphics2D) g2d.create();

            AffineTransform transform = new AffineTransform();
            transform.rotate(angle, characterCenterX, characterCenterY);
            characterG2d.setTransform(transform);
            characterG2d.drawImage(image.getImage(), characterDrawX, characterDrawY, null);

            // 임시 Graphics2D 객체 해제
            characterG2d.dispose();
        }

        // 메인 캐릭터의 체력을 감소시키는 메소드
        public void decreaseHP(int amount) {
            hp -= 1;
            if (hp < 0) {
                hp = 0;
            }
        }

        // 메인 캐릭터와 몬스터 사이의 거리를 구하기 위한 메소드
        public double distance(Monster monster) {
            int drawX = monster.x - cam_x;
            int drawY = monster.y - cam_y;

            int dx = characterDrawX - drawX;
            int dy = characterDrawY - drawY;

            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    class Monster {
        ImageIcon image;
        int x, y;
        int hp;

        double angle; // 회전 각도

        int timeForMonsterMovement = 10;

        public Monster(ImageIcon image, int x, int y, int hp) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.hp = hp;
        }

        // 몬스터가 캐릭터를 향해 움직이는 메소드 추가
        public void moveTowards(int targetX, int targetY) {

            //몬스터와 메인 캐릭터 사이의 거리를 구함
            int dx = targetX - x;
            int dy = targetY - y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            timeForMonsterMovement += 10;

            //몬스터가 걷는 모션을 취하는 것처럼 보이기 위해 일정 시간마다 이미지를 교체
            if(this.timeForMonsterMovement % 50 == 0){
                if(this.image == bigMonsterImage) {
                    this.image = bigMonsterImage2;
                }else {
                    this.image = bigMonsterImage;
                }
                this.timeForMonsterMovement = 0;
            }

            //몬스터가 메인 캐릭터에게 도착하면 움직임을 멈추기 위함.
            if (distance != 0) {
                angle = Math.atan2(dy, dx);
                int moveX = (int) Math.round((dx / distance) * 3); // 속도 조절
                int moveY = (int) Math.round((dy / distance) * 3); // 속도 조절
                x += moveX;
                y += moveY;
            }
        }

        //몬스터와 총알이 충돌하면 총알을 제거하기 위한 메소드
        public boolean isCollidedWithBullet(Bullet bullet) {
            int collisionPadding = 2;
            Rectangle monsterRect = new Rectangle(x + collisionPadding, y + collisionPadding, image.getIconWidth() - 2*collisionPadding, image.getIconHeight() - 2*collisionPadding);
            Rectangle bulletRect = new Rectangle(bullet.x, bullet.y, bullet.image.getIconWidth(), bullet.image.getIconHeight());
            return monsterRect.intersects(bulletRect);
        }
    }

    class Bullet {
        ImageIcon image;
        int x, y;
        double angle; // 총알의 이동 각도




        public Bullet(ImageIcon image, int x, int y, double angle) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.angle = angle;
        }



        public void move() {
            // 총알 속도 조절 변수
            int speed = 10;

            x += Math.round(Math.cos(angle) * speed);
            y += Math.round(Math.sin(angle) * speed);
        }

        //총알이 배경화면 밖으로 나갈 경우 제거하기 위한 메소드
        public boolean isOutOfBound() {
            if (x < 0 || y < 0 || x + image.getIconWidth() > backgroundWidth || y + image.getIconHeight() > backgroundHeight) {
                return true;
            }
            return false;
        }

    }
}
