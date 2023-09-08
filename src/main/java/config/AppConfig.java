package config;

import game.MainFrame;
import domain.user.repository.UserRepository;

public class AppConfig {

    public MainFrame mainFrame(){
        return new MainFrame(new UserRepository());
    }

}
