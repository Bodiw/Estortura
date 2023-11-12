package me.bodiw.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

import me.bodiw.Config;
import me.bodiw.gui.ControlReg;
import me.bodiw.interpreter.Interpreter;
import me.bodiw.model.Word;

public class AssemblerProcess implements AutoCloseable {

    public String emu, conf, bin, lastCmd, nextInst, breakpoint_tag;
    public int stepsInicio, skipInicio;
    public float scale;

    BufferedReader in;
    BufferedWriter out;
    public Word bitMap;

    public Word[][] regs;
    public Word[][] mem;
    public ControlReg[] controlRegs; // { pc, ti, ciclo, fl, fe, fc, fv, fr };

    public int memAddress;

    public AssemblerProcess(Config cf) {
        this.lastCmd = "";

        try {
            Process p = new ProcessBuilder(cf.emulator, "-c", cf.config, cf.bin).redirectErrorStream(true).start();
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.read(); // Skip first config/lines

        memAddress = cf.iniMem - (cf.iniMem % 16);
        breakpoint_tag = cf.breakpoint;
        skipInicio = cf.iniSkip;
        stepsInicio = cf.iniStep;
        scale = cf.scale;

        controlRegs = new ControlReg[8];
        regs = new Word[8][4];
        mem = new Word[8][4];

        for (int i = 0; i < 8; i++) {
            controlRegs[i] = new ControlReg();
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                regs[i][j] = new Word();
                mem[i][j] = new Word();
            }
        }

        if (cf.bitmapType.equals("REGISTER")) {
            bitMap = regs[cf.iniBitmap / 4][cf.iniBitmap % 4];
        } else if (cf.bitmapType.equals("MEMORY")) {
            cf.iniBitmap = cf.iniBitmap % 32;
            bitMap = mem[cf.iniBitmap / 4][cf.iniBitmap % 4];
        }

        if (!breakpoint_tag.isEmpty()) {
            System.out.println("Running to " + breakpoint_tag + " breakpoint");
            this.write("p + " + breakpoint_tag);
            this.read();
            this.write("e");
        }

        if (skipInicio > 0) {
            this.write("t " + skipInicio);
            this.read();
        }
    }

    public String read() {
        StringBuilder s = new StringBuilder(700);
        do {
            try {
                while (!in.ready()) { // El pobre emulador es lentito
                    Thread.sleep(5);
                }
                while (in.ready()) {
                    s.append((char) in.read());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            /*
             * El Stream se bloquea antes de tiempo ante procesos que requieran
             * un minimo de computacion y se reabre tarde. Dado que lo primero
             * que vuelve a escribir es el comando anterior, esperar a que haya
             * escrito el comando, line feed, salto de linea y proximo caracter
             * es la minima garantia de que el stream no estuviese bloqueado
             */
        } while (!s.toString().contains("88110>"));

        return s.toString();
    }

    public String readRegs() {
        this.writeSilent("r");
        return this.read();
    }

    public String readMem(int address) {
        this.writeSilent("v " + (address - (address % 16)) + " 32");
        return this.read();
    }

    public void writeSilent(String s) {
        try {
            out.write(s + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String s) {
        try {
            out.write(s + "\n");
            out.flush();
            this.lastCmd = s;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void step(int steps) {
        this.write("t " + steps);
        this.read();
    }

    public void updateRegs() {
        String s = this.readRegs();
        Map<String, Object> values = Interpreter.readRegs(s);

        nextInst = (String) values.get("Instruction");

        int cycle = (int) values.get("Cycle");

        String[] controlStrings = { "PC", "TotalInstructions", "Cycle", "FL", "FE", "FC", "FV", "FR" };

        for (int i = 0; i < 8; i++) {
            int newValue = (int) values.get(controlStrings[i]);
            if (controlRegs[i].value != newValue) {
                controlRegs[i].lastCycleUpdate = cycle;
                controlRegs[i].value = newValue;
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                byte[] bytes = (byte[]) values.get(String.format("R%02d", i * 4 + j));
                if (!regs[i][j].equals(bytes)) {
                    regs[i][j].lastCycleUpdate = cycle;
                    regs[i][j].data = bytes;
                }
            }
        }

    }

    public void updateMem() {
        String s = this.readMem(memAddress);
        Map<Integer, byte[][]> values = Interpreter.readMem(s);

        int cycle = controlRegs[2].value;

        for (int i = 0; i < 8; i++) {
            byte[][] row = values.get(memAddress + i * 16);
            for (int j = 0; j < 4; j++) {
                byte[] bytes = (byte[]) row[j];
                if (!mem[i][j].equals(bytes)) {
                    mem[i][j].lastCycleUpdate = cycle;
                    mem[i][j].data = bytes;
                }
            }
        }
    }

    @Override
    public void close() throws Exception {
        if (out != null)
            out.close();
        if (in != null)
            in.close();
    }
}
