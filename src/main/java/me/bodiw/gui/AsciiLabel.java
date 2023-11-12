package me.bodiw.gui;

import javax.swing.JLabel;

import me.bodiw.model.Word;

public class AsciiLabel extends JLabel {

    public Word word;

    public AsciiLabel(Word word) {
        this.word = word;
        this.setOpaque(true);
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public void update(int cycle) {
        if (cycle <= word.lastCycleUpdate) {
            this.setText(this.toString());
            this.setBackground(Colors.UPDATED);
            word.lastCycleUpdate = cycle;
        } else if (word.lastCycleUpdate != 0
                && this.getBackground() == Colors.UPDATED) {
            this.setBackground(Colors.UNUPDATED);
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            if (word.data[i] >= 32 && word.data[i] <= 126) {
                s.append((char) word.data[i]);
            } else if (word.data[i] == 0)
                s.append('.');
            else {
                s.append((char) -1);
            }
        }
        return s.toString();
    }
}
