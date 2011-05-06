/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.simplechat.server;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions;

/**
 * Server configuration manager, for reading the configuration file.
 * @author Techjar
 */
public class ConfigManager {
    /**
     * Creates a new instance of the server configuration loader.
     */
    public ConfigManager() {
    }

    /**
     * Loads the server configuration into a Map and returns it.
     *
     * @return map containing server configuration
     */
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
                // Setup the default values...
                cfg.put("max-users", "50");
                cfg.put("enable-nuke", "true");
                cfg.put("require-login", "false");
                cfg.put("ops-login", "false");

                file.createNewFile();
                FileWriter fw = new FileWriter("config.yml");
                yaml.dump(cfg, fw);
                fw.close();
            }
            else {
                FileReader fr = new FileReader("config.yml");
                cfg = (Map)yaml.load(fr);
                fr.close();
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
