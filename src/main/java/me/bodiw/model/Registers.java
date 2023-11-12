package me.bodiw.model;

public class Registers {

    public Word[] regs;

    public Registers() {
        regs = new Word[32];
        for (int i = 0; i < regs.length; i++) {
            regs[i] = new Word();
        }
    }
}
