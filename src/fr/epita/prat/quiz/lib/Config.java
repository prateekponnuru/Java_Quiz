package fr.epita.prat.quiz.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    Properties properties = new Properties();
    private static Config config;

    private Config(){
        try {
//            File file = new File(".");
//            for(String fileNames : file.list()) System.out.println(fileNames);
            properties.load(new FileInputStream(new File("C:\\myProjects\\epita\\java\\exercises\\src\\fr\\epita\\prat\\quiz\\conf.properties")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String get(String key) {
        String property = this.properties.getProperty(key);
        if (property == null) {
            return "";
        }
        return property;
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }
}
