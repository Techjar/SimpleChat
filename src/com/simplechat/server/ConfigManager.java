/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @date May 3, 2011
 * @author Techjar
 * @version 
 */


package com.simplechat.server;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions;

public class ConfigManager {
    public ConfigManager() {
    }

    public Map load() {
        Map<String, String> cfg = new HashMap<String, String>();
        try {
            DumperOptions dumper = new DumperOptions();
            dumper.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
            dumper.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            dumper.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);

            Yaml yaml = new Yaml(dumper);
            File file = new File("config.yml");
            if(!file.exists()) {
                file.createNewFile();
                FileWriter fw = new FileWriter("config.yml");
                cfg.put("max-users", "50");
                cfg.put("enable-nuke", "true");
                yaml.dump(cfg, fw);
                fw.close();
            }
            else {
                cfg = (Map)yaml.load(new FileReader("config.yml"));
            }
            return cfg;
        }
        catch(Throwable e) {
            System.err.println("Could not load configuration.");
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }
}
