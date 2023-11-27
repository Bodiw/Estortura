package me.bodiw.gui;

import javax.swing.JLabel;

import me.bodiw.model.Word;

public class WordLabel extends JLabel {

    public enum DisplayMode {
        HEX, DEC
    }

    public Word word;
    private DisplayMode displayMode = DisplayMode.HEX;

    public WordLabel(Word word) {
        this.word = word;
        this.createToolTip();
        this.setOpaque(true);
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public void update(int cycle) {
        if (cycle <= word.lastCycleUpdate) {
            this.setText(this.getDisplayText());
            this.setBackground(Colors.UPDATED);
            word.lastCycleUpdate = cycle;
        } else if (word.lastCycleUpdate != 0
                && this.getBackground() == Colors.UPDATED) {
            this.setBackground(Colors.UNUPDATED);
        }
    }

    public String getDisplayText() {
        if (displayMode == DisplayMode.HEX) {
            return word.toString();
        } else {
            return word.toIntString();
        }
    }

}
