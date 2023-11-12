package me.bodiw;

import com.formdev.flatlaf.FlatDarkLaf;

import me.bodiw.gui.Gui;
import me.bodiw.process.AssemblerProcess;
import oe.process.CompilerProcess;

public class App {

    static String configPath = "config.json";

    public static void main(String[] args) {

        if (args.length > 0) {
            configPath = args[0];
        }

        FlatDarkLaf.setup();

        String name = Names.NAMES[(int) (Math.random() * Names.NAMES.length)];

        Gui gui = new Gui(name, createAssemblerProcess());

        gui.setLocationRelativeTo(null);
        gui.setVisible(true);

    }

    public static AssemblerProcess createAssemblerProcess() {
        AssemblerProcess ap = null;
        try {
            ap = new AssemblerProcess(load(configPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Assembler process created");
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