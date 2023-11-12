package me.bodiw;

import com.formdev.flatlaf.FlatDarkLaf;

import me.bodiw.gui.Gui;
import me.bodiw.process.AssemblerProcess;
import oe.process.CompilerProcess;

public class App {

    public static void main(String[] args) {

        FlatDarkLaf.setup();

        String name = Names.NAMES[(int) (Math.random() * Names.NAMES.length)];

        Gui gui = new Gui(name, createAssemblerProcess());

        gui.setVisible(true);

    }

    public static AssemblerProcess createAssemblerProcess() {
        AssemblerProcess ap = null;
        try {
            ap = new AssemblerProcess(load("config.json"));
            ap.read(); // Skip first config/lines
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ap;
    }

    private static Config load(String configPath) throws Exception {
        Config config = new Config(configPath);
        config.load();
        CompilerProcess cp = new CompilerProcess(
                config.compiler,
                config.code,
                config.start,
                "estortura.bin");
        cp.compile();
        config.bin = "estortura.bin";
        return config;
    }
}