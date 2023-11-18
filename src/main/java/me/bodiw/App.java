package me.bodiw;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.FlatDarkLaf;

import me.bodiw.gui.Gui;
import me.bodiw.process.AssemblerProcess;
import oe.process.CompilerProcess;

public class App {

    public static String configPath = "config.json";

    static JFrame errorFrame;

    public static void main(String[] args) {

        if (args.length > 0) {
            configPath = args[0];
        }

        loadErrorFrame();

        FlatDarkLaf.setup();

        Config cf = loadConfig(configPath);

        if (cf == null) {
            errorFrame.dispose();
            return;
        }

        AssemblerProcess ap = null;

        ap = createAssemblerProcess(cf);

        if (ap == null) {
            errorFrame.dispose();
            return;
        }

        String name = Names.NAMES[(int) (Math.random() * Names.NAMES.length)];
        
        Gui gui = new Gui(name, ap);

        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        gui.requestFocus();

    }

    public static AssemblerProcess createAssemblerProcess(Config cf) {
        AssemblerProcess ap = null;

        try {
            ap = new AssemblerProcess(cf);
            System.out.println("Assembler process created");
        } catch (IOException e) {
            showError("Assembler builder", "No se ha podido crear un proceso Ensamblador\n"
                    + "Esta mal la direccion del emulador o del archivo conf(serie)?\n"
                    + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (RuntimeException e) {
            showError("Assembler builder", e.getMessage());
            e.printStackTrace();
            return null;
        }

        return ap;
    }

    public static Config loadConfig(String configPath) {
        Config config = new Config(configPath);

        // Failed to read file
        try {
            config.load();
        } catch (IOException e) {
            showError("Config", "Failed to read " + configPath + " config file");
            e.printStackTrace();
            return null;
        }

        CompilerProcess cp = new CompilerProcess(
                config.compiler,
                config.code,
                config.start,
                "estortura.bin");

        try {
            cp.compile();
        } catch (IOException | InterruptedException e) {
            showError("Compiler", "No se ha podido compilar el codigo\n" + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (RuntimeException e) {
            showError("Compiler", e.getMessage());
            e.printStackTrace();
            return null;
        }

        config.bin = "estortura.bin";
        return config;
    }

    public static void showError(String source, String message) {
        JOptionPane.showMessageDialog(
                errorFrame,
                message,
                source,
                JOptionPane.ERROR_MESSAGE);
    }

    private static void loadErrorFrame() {
        errorFrame = new JFrame();
        errorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        errorFrame.setAlwaysOnTop(true);
    }
}