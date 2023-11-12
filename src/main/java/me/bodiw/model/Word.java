package me.bodiw.model;

public class Word {

    public byte[] data;

    public int lastCycleUpdate;

    public Word() {
        data = new byte[4];
    }

    @Override
    public String toString() {
        return String.format("%02X %02X %02X %02X", data[0], data[1], data[2], data[3]);
    }

    public boolean equals(byte[] data2) {
        for (int i = 0; i < 4; i++) {
            if (data[i] != data2[i]) {
                return false;
            }
        }
        return true;
    }
}
