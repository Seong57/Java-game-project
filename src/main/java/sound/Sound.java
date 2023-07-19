package sound;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Sound extends Thread{

    private Player player;
    private boolean isLoop; // 현재 곡이 무한반복인지 아닌지
    private File file;
    private FileInputStream fis;
    private BufferedInputStream bis;

    public Sound(String path, boolean isLoop) {
        try {
            this.isLoop = isLoop;
            file = new File(path);
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 현재 노래가 어느 위치(시간)까지 재생되었는지 알려줌
    public int getTime() {
        if (player == null)
            return 0;
        return player.getPosition();
    }

    // 음악이 언제 시작되었던간에 종료할 수 있게 해줌
    public void close() {
        isLoop = false;
        player.close();
        this.interrupt(); // 해당 스레드를 중지상태로 만듬
    }

    // Thread를 상속받으면 무조건 사용해야하는 함수
    @Override
    public void run() {
        try {
            do {
                player.play();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                player = new Player(bis);
            } while (isLoop);
            player.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
