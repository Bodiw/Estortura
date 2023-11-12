package me.bodiw.gui;

import javax.swing.JLabel;

public class ControlRegLabel extends JLabel {

    public ControlReg reg;

    public ControlRegLabel(ControlReg reg) {
        this.reg = reg;
        this.setOpaque(true);
    }

    public String toString() {
        return "" + reg.value;
    }

    public void update(int cycle) {
        if (cycle <= reg.lastCycleUpdate) {
            this.setText(this.toString());
            this.setBackground(Colors.UPDATED);
            reg.lastCycleUpdate = cycle;
        } else if (reg.lastCycleUpdate != 0
                && this.getBackground() == Colors.UPDATED) {
            this.setBackground(Colors.UNUPDATED);
        }
    }
}
