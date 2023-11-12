package me.bodiw.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import me.bodiw.App;
import me.bodiw.model.Word;
import me.bodiw.process.AssemblerProcess;

/**
 *
 * @author bogdan (con ayudita de NetBeans)
 */
public class Gui extends JFrame {

        private WordLabel[][] registerWords;
        private WordLabel[][] memWords;
        private AsciiLabel[][] asciiMemWords;
        private AsciiLabel[][] asciiRegWords;
        private ControlRegLabel[] controlRegs;
        private JLabel[] registerLabels;
        private JPanel[] registerRows;
        private JLabel[] memLabels;
        private JPanel[] memRows;
        private JPanel[] asciiRegRows;
        private JPanel[] asciiMemRows;
        private JLabel[] bitLabels;

        private JTabbedPane tabRegistros;
        private JPanel panelRegistros;
        private JScrollPane stdout;
        private JTextArea stdoutpane;
        private JTabbedPane asciireg;
        private JPanel asciiRegPanel;
        private JTabbedPane mem;
        private JPanel memoria;
        private JTabbedPane controltab;
        private JPanel controlpanel;
        private JPanel Instrucciones;
        private JLabel last;
        private JLabel next;
        private JLabel lastval;
        private JLabel nextval;
        private JPanel conf;
        private JSpinner stepspinner;
        private JSpinner memspinner;
        private JButton membutton;
        private JButton stepbutton;
        private JButton stdinbutton;
        private JButton reloadButton;
        private JTextField stdinval;
        private JTabbedPane asciimem;
        private JPanel asciiMemPanel;
        private JPanel bitTabPanel;
        private JTabbedPane bitTab;

        private AssemblerProcess assembler;

        private float scale = 1;

        public Gui(String name, AssemblerProcess assembler) {
                super(name);
                this.assembler = assembler;
                initComponents();
        }

        private void initComponents() {

                registerWords = new WordLabel[8][4];
                memWords = new WordLabel[8][4];
                asciiRegWords = new AsciiLabel[8][4];
                asciiMemWords = new AsciiLabel[8][4];
                controlRegs = new ControlRegLabel[8]; // { pc, ti, ciclo, fl, fe, fc, fv, fr };
                registerLabels = new JLabel[8];
                registerRows = new JPanel[8];
                memLabels = new JLabel[8];
                memRows = new JPanel[8];
                asciiRegRows = new JPanel[8];
                asciiMemRows = new JPanel[8];

                GridBagConstraints gridBagConstraints;

                tabRegistros = new JTabbedPane();
                panelRegistros = new JPanel();
                stdout = new JScrollPane();
                stdoutpane = new JTextArea();
                asciireg = new JTabbedPane();
                asciiRegPanel = new JPanel();
                mem = new JTabbedPane();
                memoria = new JPanel();
                controltab = new JTabbedPane();
                controlpanel = new JPanel();
                Instrucciones = new JPanel();
                last = new JLabel();
                next = new JLabel();
                lastval = new JLabel();
                nextval = new JLabel();
                conf = new JPanel();
                stepspinner = new JSpinner();
                memspinner = new JSpinner();
                membutton = new JButton();
                stepbutton = new JButton();
                stdinbutton = new JButton();
                stdinval = new JTextField();
                asciimem = new JTabbedPane();
                asciiMemPanel = new JPanel();
                bitTabPanel = new JPanel();
                bitTab = new JTabbedPane();
                reloadButton = new JButton();
                bitLabels = new JLabel[32];

                scale = assembler.scale;

                Dimension dimension_400_19 = new Dimension((int) (scale * 400), (int) (scale * 19));
                Dimension dimension_100_19 = new Dimension((int) (scale * 100), (int) (scale * 19));
                Dimension dimension_60_19 = new Dimension((int) (scale * 60), (int) (scale * 19));
                Dimension dimension_34_19 = new Dimension((int) (scale * 34), (int) (scale * 19));
                Dimension dimension_192_19 = new Dimension((int) (scale * 192), (int) (scale * 19));
                Dimension dimension_100_38 = new Dimension((int) (scale * 100), (int) (scale * 38));
                Dimension dimension_114_38 = new Dimension((int) (scale * 114), (int) (scale * 38));
                Dimension dimension_68_38 = new Dimension((int) (scale * 68), (int) (scale * 38));
                Dimension dimension_70_38 = new Dimension((int) (scale * 70), (int) (scale * 38));
                Dimension dimension_68_25 = new Dimension((int) (scale * 68), (int) (scale * 25));
                Dimension dimension_73_25 = new Dimension((int) (scale * 73), (int) (scale * 25));
                Dimension dimension_442_187 = new Dimension((int) (scale * 442), (int) (scale * 187));
                Dimension dimension_179_19 = new Dimension((int) (scale * 179), (int) (scale * 19));
                Dimension dimension_35_19 = new Dimension((int) (scale * 35), (int) (scale * 19));
                Dimension dimension_19_19 = new Dimension((int) (scale * 19), (int) (scale * 19));
                Dimension dimension_19_24 = new Dimension((int) (scale * 19), (int) (scale * 24));

                Font cantarell_10 = new Font("Cantarell", 0, (int) (scale * 10));
                Font cantarell_14 = new Font("Cantarell", 0, (int) (scale * 14));
                Font cantarell_15 = new Font("Cantarell", 0, (int) (scale * 15));
                Font monospaced_13 = new Font("Monospaced", 0, (int) (scale * 13));

                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                tabRegistros.setMaximumSize(dimension_442_187);

                panelRegistros.setLayout(new GridBagLayout());
                panelRegistros.setBackground(Colors.BACKGROUND);
                panelRegistros.setForeground(Colors.FOREGROUND);

                memoria.setBackground(Colors.BACKGROUND);
                memoria.setForeground(Colors.FOREGROUND);

                asciiMemPanel.setBackground(Colors.BACKGROUND);
                asciiMemPanel.setForeground(Colors.FOREGROUND);

                asciiRegPanel.setBackground(Colors.BACKGROUND);
                asciiRegPanel.setForeground(Colors.FOREGROUND);

                controlpanel.setBackground(Colors.BACKGROUND);
                controlpanel.setForeground(Colors.FOREGROUND);

                Instrucciones.setBackground(Colors.BACKGROUND);
                Instrucciones.setForeground(Colors.FOREGROUND);

                conf.setBackground(Colors.BACKGROUND);
                conf.setForeground(Colors.FOREGROUND);

                stdout.setBackground(Colors.BACKGROUND);
                stdout.setForeground(Colors.FOREGROUND);

                stdoutpane.setBackground(Colors.BACKGROUND);
                stdoutpane.setForeground(Colors.FOREGROUND);

                bitTabPanel.setBackground(Colors.BACKGROUND);
                bitTabPanel.setForeground(Colors.FOREGROUND);

                this.getContentPane().setBackground(Colors.BACKGROUND);

                for (int i = 0; i < 8; i++) { // Etiquetas R XX
                        JLabel label = new JLabel();
                        registerLabels[i] = label;
                        Dimension size = dimension_34_19;
                        label.setBackground(panelRegistros.getBackground());
                        label.setForeground(panelRegistros.getForeground());
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setText(String.format("R %02d", i * 4));
                        label.setBorder(Colors.BORDER);
                        label.setMaximumSize(size);
                        label.setMinimumSize(size);
                        label.setPreferredSize(size);
                        label.setFont(cantarell_15);
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridx = 0;
                        gridBagConstraints.ipadx = 8;
                        panelRegistros.add(label, gridBagConstraints);
                }

                for (int i = 0; i < 8; i++) { // Filas de los registro
                        JPanel registerRow = new JPanel();
                        registerRows[i] = registerRow;
                        Dimension size = dimension_400_19;
                        BoxLayout layout = new BoxLayout(registerRow, BoxLayout.X_AXIS);
                        registerRow.setBorder(Colors.BORDER);
                        registerRow.setBackground(panelRegistros.getBackground());
                        registerRow.setForeground(panelRegistros.getForeground());
                        registerRow.setMaximumSize(size);
                        registerRow.setMinimumSize(size);
                        registerRow.setPreferredSize(size);
                        registerRow.setLayout(layout);

                        for (int j = 0; j < 4; j++) { // Columnas de los registros
                                WordLabel label = new WordLabel(assembler.regs[i][j]);
                                registerWords[i][j] = label;
                                Dimension labelSize = dimension_100_19;
                                label.setHorizontalAlignment(SwingConstants.CENTER);
                                label.setText(label.word.toString());
                                label.setFont(cantarell_15);
                                label.setMaximumSize(labelSize);
                                label.setMinimumSize(labelSize);
                                label.setPreferredSize(labelSize);
                                label.setBackground(panelRegistros.getBackground());
                                label.setForeground(panelRegistros.getForeground());
                                label.addMouseListener(new MouseAdapter() {
                                        public void mousePressed(MouseEvent evt) {
                                                changeBitmapMouseEvent(evt);
                                        }
                                });

                                registerRow.add(label);
                        }
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridx = 1;
                        panelRegistros.add(registerRow, gridBagConstraints);
                }

                tabRegistros.addTab("Registros", panelRegistros);

                /*
                 * MEMORIA PRINCIPAL
                 */

                mem.setMaximumSize(dimension_442_187);

                memoria.setLayout(new GridBagLayout());

                for (int i = 0; i < 8; i++) { // Etiquetas XXXX
                        JLabel label = new JLabel();
                        memLabels[i] = label;
                        Dimension size = dimension_34_19;
                        label.setBackground(memoria.getBackground());
                        label.setForeground(memoria.getForeground());
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setText(String.format("%4d", assembler.memAddress + i * 16));
                        label.setBorder(Colors.BORDER);
                        label.setMaximumSize(size);
                        label.setMinimumSize(size);
                        label.setPreferredSize(size);
                        label.setFont(cantarell_15);
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridx = 0;
                        gridBagConstraints.ipadx = 8;
                        memoria.add(label, gridBagConstraints);
                }

                for (int i = 0; i < 8; i++) { // Filas de la memoria
                        JPanel memRow = new JPanel();
                        memRows[i] = memRow;
                        Dimension size = dimension_400_19;
                        BoxLayout layout = new BoxLayout(memRow, BoxLayout.X_AXIS);
                        memRow.setBackground(memoria.getBackground());
                        memRow.setForeground(memoria.getForeground());
                        memRow.setBorder(Colors.BORDER);
                        memRow.setMaximumSize(size);
                        memRow.setMinimumSize(size);
                        memRow.setPreferredSize(size);
                        memRow.setLayout(layout);

                        for (int j = 0; j < 4; j++) { // Columnas de la memoria
                                WordLabel label = new WordLabel(assembler.mem[i][j]);
                                memWords[i][j] = label;
                                Dimension labelSize = dimension_100_19;
                                label.setHorizontalAlignment(SwingConstants.CENTER);
                                label.setText(label.word.toString());
                                label.setMaximumSize(labelSize);
                                label.setMinimumSize(labelSize);
                                label.setPreferredSize(labelSize);
                                label.setBackground(memoria.getBackground());
                                label.setForeground(memoria.getForeground());
                                label.setFont(cantarell_15);
                                label.addMouseListener(new MouseAdapter() {
                                        public void mousePressed(MouseEvent evt) {
                                                changeBitmapMouseEvent(evt);
                                        }
                                });
                                memRow.add(label);
                        }
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridx = 1;
                        memoria.add(memRow, gridBagConstraints);
                }

                mem.addTab("Memoria", memoria);

                stdoutpane.setColumns(20);
                stdoutpane.setFont(cantarell_10);
                stdoutpane.setRows(5);
                stdoutpane.setText("88110 >");
                stdoutpane.setEditable(false);
                stdout.setViewportView(stdoutpane);

                mem.addTab("Stdout", stdout);

                /*
                 * Registros ASCII
                 */
                asciireg.setMaximumSize(dimension_442_187);

                asciiRegPanel.setLayout(new BoxLayout(asciiRegPanel, BoxLayout.Y_AXIS));

                for (int i = 0; i < 8; i++) { // Filas de registros ascii
                        JPanel asciiRegRow = new JPanel();
                        asciiRegRows[i] = asciiRegRow;
                        Dimension size = dimension_179_19;
                        GridBagLayout layout = new GridBagLayout();
                        asciiRegRow.setBackground(asciiMemPanel.getBackground());
                        asciiRegRow.setForeground(asciiMemPanel.getForeground());
                        asciiRegRow.setBorder(Colors.BORDER);
                        asciiRegRow.setMaximumSize(size);
                        asciiRegRow.setMinimumSize(size);
                        asciiRegRow.setPreferredSize(size);
                        asciiRegRow.setLayout(layout);

                        for (int j = 0; j < 4; j++) { // Columnas de registros ascii
                                AsciiLabel label = new AsciiLabel(assembler.regs[i][j]);
                                asciiRegWords[i][j] = label;
                                Dimension labelSize = dimension_35_19;

                                label.setFont(monospaced_13); // NOI18N
                                label.setHorizontalAlignment(SwingConstants.CENTER);
                                label.setForeground(asciiRegRow.getForeground());
                                label.setBackground(asciiRegRow.getBackground());
                                label.setText("....");
                                label.setMaximumSize(labelSize);
                                label.setMinimumSize(labelSize);
                                label.setPreferredSize(labelSize);
                                label.addMouseListener(new MouseAdapter() {
                                        public void mousePressed(MouseEvent evt) {
                                                changeBitmapMouseEvent(evt);
                                        }
                                });
                                gridBagConstraints = new GridBagConstraints();
                                gridBagConstraints.ipadx = 8;
                                asciiRegRow.add(label, gridBagConstraints);
                        }
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridx = 1;
                        asciiRegPanel.add(asciiRegRow, gridBagConstraints);
                }

                asciireg.addTab("Ascii", asciiRegPanel);
                controltab.setMaximumSize(dimension_442_187);
                controlpanel.setLayout(new GridBagLayout());

                String[] names = { "PC", "TI", "Ciclo", "FL", "FE", "FC", "FV", "FR" };
                String[] tooltips = {
                                "Program Counter\n     Direccion de Memoria del Contador de Programa",
                                "Total Instrucciones", "Ciclo de Reloj",
                                "Flag: Little Endian\n     0 => Big Endian\n     1 => Little Endian",
                                "Flag: Exceptions\n     0 => Excepciones Activas\n     1 => Excepciones Inhibidas",
                                "Flag: Courriage (Acarreo)\n     0 => No ha habido Acarreo\n     1 => Ha habido Acarreo",
                                "Flag: Overflow (desbordamiento)\n     0 => No ha habido Overflow\n     1 => Ha habido Overflow",
                                "Flag: Round (Redondeo)\n    0 => Redondeo al mas cercano\n    1 => Redondeo hacia 0\n    2 => Redondeo hacia -Inf\n    3 => Redondeo hacia +Inf" };

                for (int i = 0; i < 8; i++) { // Etiquetas Registros de Control
                        JLabel label = new JLabel();
                        Dimension size = dimension_34_19;
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setForeground(controlpanel.getForeground());
                        label.setBackground(controlpanel.getBackground());
                        label.setText(names[i]);
                        label.setToolTipText(tooltips[i]);
                        label.setBorder(Colors.BORDER);
                        label.setMaximumSize(size);
                        label.setMinimumSize(size);
                        label.setPreferredSize(size);
                        label.setFont(cantarell_15);
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridx = 1;
                        gridBagConstraints.ipadx = 8;
                        controlpanel.add(label, gridBagConstraints);
                }

                for (int i = 0; i < 8; i++) { // Valores Registros de Control
                        ControlRegLabel label = new ControlRegLabel(assembler.controlRegs[i]);
                        controlRegs[i] = label;
                        Dimension size = dimension_60_19;
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setForeground(controlpanel.getForeground());
                        label.setBackground(controlpanel.getBackground());
                        label.setText("" + label.reg.value);
                        label.setBorder(Colors.BORDER);
                        label.setMaximumSize(size);
                        label.setMinimumSize(size);
                        label.setPreferredSize(size);
                        label.setFont(cantarell_15);
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridx = 0;
                        controlpanel.add(label, gridBagConstraints);
                }

                /*
                 * Mapa de bits
                 */
                bitTabPanel.setLayout(new GridBagLayout());

                for (int i = 31; i >= 0; i--) {
                        JLabel label = new JLabel();
                        JLabel value = new JLabel();

                        bitLabels[i] = value;

                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setText("" + i);
                        label.setBorder(Colors.BORDER);
                        label.setMaximumSize(dimension_19_19);
                        label.setMinimumSize(dimension_19_19);
                        label.setPreferredSize(dimension_19_19);
                        label.setBackground(bitTabPanel.getBackground());
                        label.setForeground(bitTabPanel.getForeground());
                        label.setFont(cantarell_14);
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridy = 0;
                        bitTabPanel.add(label, gridBagConstraints);

                        value.setHorizontalAlignment(SwingConstants.CENTER);
                        value.setText("0");
                        value.setBorder(Colors.BORDER);
                        value.setMaximumSize(dimension_19_24);
                        value.setMinimumSize(dimension_19_24);
                        value.setPreferredSize(dimension_19_24);
                        value.setBackground(bitTabPanel.getBackground());
                        value.setForeground(bitTabPanel.getForeground());
                        value.setFont(cantarell_14);
                        value.setOpaque(true);
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridy = 1;
                        bitTabPanel.add(value, gridBagConstraints);
                }

                bitTab.addTab("Bitmap", bitTabPanel);

                controltab.addTab("Control", controlpanel);

                Instrucciones.setLayout(new GridBagLayout());

                last.setHorizontalAlignment(SwingConstants.CENTER);
                last.setText("Last");
                last.setToolTipText("Ultima Instruccion");
                last.setBorder(Colors.BORDER);
                last.setMaximumSize(dimension_34_19);
                last.setMinimumSize(dimension_34_19);
                last.setPreferredSize(dimension_34_19);
                last.setForeground(Instrucciones.getForeground());
                last.setBackground(Instrucciones.getBackground());
                gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.ipadx = 8;
                Instrucciones.add(last, gridBagConstraints);

                next.setHorizontalAlignment(SwingConstants.CENTER);
                next.setText("Next");
                next.setToolTipText("Siguiente Instruccion");
                next.setBorder(Colors.BORDER);
                next.setMaximumSize(dimension_34_19);
                next.setMinimumSize(dimension_34_19);
                next.setPreferredSize(dimension_34_19);
                next.setForeground(Instrucciones.getForeground());
                next.setBackground(Instrucciones.getBackground());
                gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.ipadx = 8;
                Instrucciones.add(next, gridBagConstraints);

                lastval.setFont(cantarell_15); // NOI18N
                lastval.setHorizontalAlignment(SwingConstants.CENTER);
                lastval.setForeground(Instrucciones.getForeground());
                lastval.setBackground(Instrucciones.getBackground());
                lastval.setText("-");
                lastval.setBorder(Colors.BORDER);
                lastval.setMaximumSize(dimension_192_19);
                lastval.setMinimumSize(dimension_192_19);
                lastval.setPreferredSize(dimension_192_19);
                gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.ipadx = 8;
                Instrucciones.add(lastval, gridBagConstraints);

                nextval.setFont(cantarell_15); // NOI18N
                nextval.setHorizontalAlignment(SwingConstants.CENTER);
                nextval.setForeground(Instrucciones.getForeground());
                nextval.setBackground(Instrucciones.getBackground());
                nextval.setText("-");
                nextval.setBorder(Colors.BORDER);
                nextval.setMaximumSize(dimension_192_19);
                nextval.setMinimumSize(dimension_192_19);
                nextval.setPreferredSize(dimension_192_19);
                gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.ipadx = 8;
                Instrucciones.add(nextval, gridBagConstraints);

                conf.setLayout(new GridBagLayout());

                stepspinner.setModel(new SpinnerNumberModel(assembler.stepsInicio, 0, null, 1));
                stepspinner.setBorder(Colors.BORDER);
                stepspinner.setMinimumSize(dimension_100_38);
                stepspinner.setPreferredSize(dimension_114_38);
                stepspinner.setFont(cantarell_15);
                stepspinner.setBackground(conf.getBackground());
                stepspinner.addMouseWheelListener(new MouseWheelListener() {
                        @Override
                        public void mouseWheelMoved(MouseWheelEvent e) {
                                mouseWheelMovedEvent(e);
                        }
                });
                gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 1;
                conf.add(stepspinner, gridBagConstraints);

                memspinner.setModel(new SpinnerNumberModel(assembler.memAddress, 0, null, 16));
                memspinner.setBorder(Colors.BORDER);
                memspinner.setMinimumSize(dimension_100_38);
                memspinner.setPreferredSize(dimension_114_38);
                memspinner.setFont(cantarell_15);
                memspinner.setBackground(conf.getBackground());
                memspinner.addMouseWheelListener(new MouseWheelListener() {
                        public void mouseWheelMoved(MouseWheelEvent evt) {
                                mouseWheelMovedEvent(evt);
                        }
                });
                gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 1;
                conf.add(memspinner, gridBagConstraints);

                membutton.setText("Memoria");
                membutton.setToolTipText("Direccion de Memoria desde la cual mostrar");
                membutton.setBorder(Colors.BORDER);
                membutton.setHorizontalTextPosition(SwingConstants.CENTER);
                membutton.setMargin(new Insets(0, 0, 0, 0));
                membutton.setMaximumSize(dimension_68_38);
                membutton.setMinimumSize(dimension_70_38);
                membutton.setPreferredSize(dimension_70_38);
                membutton.setFont(cantarell_15);
                membutton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                membuttonActionPerformed(evt);
                        }
                });
                gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 1;
                conf.add(membutton, gridBagConstraints);

                stepbutton.setText("Steps");
                stepbutton.setToolTipText("Instrucciones a Ejecutar");
                stepbutton.setBorder(Colors.BORDER);
                stepbutton.setHorizontalTextPosition(SwingConstants.CENTER);
                stepbutton.setMargin(new Insets(0, 0, 0, 0));
                stepbutton.setMaximumSize(dimension_68_38);
                stepbutton.setMinimumSize(dimension_70_38);
                stepbutton.setPreferredSize(dimension_70_38);
                stepbutton.setFont(cantarell_15);
                stepbutton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                stepbuttonActionPerformed(evt);
                        }
                });
                gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                conf.add(stepbutton, gridBagConstraints);

                stdinbutton.setText("Stdin");
                stdinbutton.setBorder(Colors.BORDER);
                stdinbutton.setMaximumSize(dimension_68_25);
                stdinbutton.setMinimumSize(dimension_68_25);
                stdinbutton.setPreferredSize(dimension_68_25);
                stdinbutton.setFont(cantarell_15);
                stdinbutton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                stdinbuttonActionPerformed(evt);
                        }
                });

                stdinval.setText("t 10");
                stdinval.setBorder(Colors.BORDER);
                stdinval.setPreferredSize(dimension_73_25);
                stdinval.setFont(cantarell_15);
                stdinval.setBackground(conf.getBackground());
                stdinval.addKeyListener(new KeyAdapter() {
                        public void keyPressed(java.awt.event.KeyEvent evt) {
                                stdinvalKeyEvent(evt);
                        }
                });

                asciimem.setMaximumSize(dimension_442_187);

                asciiMemPanel.setLayout(new BoxLayout(asciiMemPanel, BoxLayout.Y_AXIS));

                for (int i = 0; i < 8; i++) { // Filas de memoria ascii
                        JPanel asciiMemRow = new JPanel();
                        asciiMemRows[i] = asciiMemRow;
                        Dimension size = dimension_179_19;
                        GridBagLayout layout = new GridBagLayout();
                        asciiMemRow.setBackground(asciiMemPanel.getBackground());
                        asciiMemRow.setForeground(asciiMemPanel.getForeground());
                        asciiMemRow.setBorder(Colors.BORDER);
                        asciiMemRow.setMaximumSize(size);
                        asciiMemRow.setMinimumSize(size);
                        asciiMemRow.setPreferredSize(size);
                        asciiMemRow.setLayout(layout);

                        for (int j = 0; j < 4; j++) { // Columnas de memoria ascii
                                AsciiLabel label = new AsciiLabel(assembler.mem[i][j]);
                                asciiMemWords[i][j] = label;
                                Dimension labelSize = dimension_35_19;

                                label.setFont(monospaced_13); // NOI18N
                                label.setHorizontalAlignment(SwingConstants.CENTER);
                                label.setForeground(asciiMemRow.getForeground());
                                label.setBackground(asciiMemRow.getBackground());
                                label.setText("....");
                                label.setMaximumSize(labelSize);
                                label.setMinimumSize(labelSize);
                                label.setPreferredSize(labelSize);
                                label.addMouseListener(new MouseAdapter() {
                                        public void mousePressed(MouseEvent evt) {
                                                changeBitmapMouseEvent(evt);
                                        }
                                });
                                gridBagConstraints = new GridBagConstraints();
                                gridBagConstraints.ipadx = 8;
                                asciiMemRow.add(label, gridBagConstraints);
                        }
                        gridBagConstraints = new GridBagConstraints();
                        gridBagConstraints.gridx = 1;
                        asciiMemPanel.add(asciiMemRow, gridBagConstraints);
                }

                asciimem.addTab("Ascii", asciiMemPanel);

                reloadButton.setText("Reload");
                reloadButton.setToolTipText("Direccion de Memoria desde la cual mostrar");
                reloadButton.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                reloadButton.setHorizontalTextPosition(SwingConstants.CENTER);
                reloadButton.setMargin(new Insets(0, 0, 0, 0));
                reloadButton.setMaximumSize(dimension_70_38);
                reloadButton.setMinimumSize(dimension_70_38);
                reloadButton.setPreferredSize(dimension_70_38);
                reloadButton.setFont(cantarell_15);
                reloadButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                reloadButtonActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(24, 24, 24)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(mem,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(asciimem,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(reloadButton,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(conf,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                false)
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addComponent(stdinbutton,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(0, 0, 0)
                                                                                                                                .addComponent(stdinval,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                174,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(Instrucciones,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addComponent(tabRegistros,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                .addComponent(asciireg,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(bitTab,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(controltab,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                                .createSequentialGroup()
                                                                .addGap(24, 24, 24)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addGroup(layout.createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                                .addComponent(asciireg,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(tabRegistros,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addComponent(controltab,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(bitTab,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                .addComponent(conf,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                .addComponent(Instrucciones,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                16,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(stdinbutton,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(stdinval,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                                                .addGap(18, 20, Short.MAX_VALUE)
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addComponent(mem,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(asciimem,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                .addComponent(reloadButton,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(24, 24, 24)));
                pack();

                this.update();
        }

        public void mouseWheelMovedEvent(MouseWheelEvent e) {
                JSpinner spinner = (JSpinner) e.getSource();

                if (e.getWheelRotation() < 0) {
                        spinner.setValue(spinner.getNextValue());
                } else if ((Integer) spinner.getPreviousValue() > 0) {
                        spinner.setValue(spinner.getPreviousValue());
                }
        }

        private void changeBitmapMouseEvent(MouseEvent evt) {
                Object source = evt.getSource();

                if (source instanceof WordLabel) {
                        assembler.bitMap = ((WordLabel) evt.getSource()).word;
                } else if (source instanceof AsciiLabel) {
                        assembler.bitMap = ((AsciiLabel) evt.getSource()).word;
                }
                updateBitmap();
        }

        private void stdinbuttonActionPerformed(ActionEvent evt) {
                String command = stdinval.getText();
                if (command.length() > 0) {
                        stdinval.setText("");
                        assembler.write(command);
                        String s = assembler.read();
                        stdoutpane.setText(s);
                        this.update();
                }
        }

        private void stdinvalKeyEvent(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                        stdinbuttonActionPerformed(null);
                }
                if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        this.requestFocus();
                }
        }

        private void stepbuttonActionPerformed(ActionEvent evt) {
                int steps = (Integer) stepspinner.getValue();
                assembler.step(steps);
                this.update();
        }

        private void membuttonActionPerformed(ActionEvent evt) {
                assembler.memAddress = (Integer) memspinner.getValue();
                this.update();
        }

        private void reloadButtonActionPerformed(ActionEvent evt) {
                try {
                        assembler.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }

                assembler = App.createAssemblerProcess();

                for (int i = 0; i < 8; i++) {
                        controlRegs[i].reg = assembler.controlRegs[i];
                        controlRegs[i].setText("0");
                        controlRegs[i].setBackground(controlpanel.getBackground());
                        for (int j = 0; j < 4; j++) {
                                registerWords[i][j].word = assembler.regs[i][j];
                                memWords[i][j].word = assembler.mem[i][j];
                                asciiRegWords[i][j].word = assembler.regs[i][j];
                                asciiMemWords[i][j].word = assembler.mem[i][j];

                                registerWords[i][j].setText(assembler.regs[i][j].toString());
                                memWords[i][j].setText(assembler.mem[i][j].toString());
                                asciiRegWords[i][j].setText(asciiRegWords[i][j].toString());
                                asciiMemWords[i][j].setText(asciiMemWords[i][j].toString());

                                registerWords[i][j].setBackground(memoria.getBackground());
                                memWords[i][j].setBackground(memoria.getBackground());
                                asciiRegWords[i][j].setBackground(asciiMemPanel.getBackground());
                                asciiMemWords[i][j].setBackground(asciiMemPanel.getBackground());
                        }
                }
                this.update();
        }

        private void updateRegs() {
                assembler.updateRegs();

                int cycle = assembler.controlRegs[2].value;

                for (int i = 0; i < 8; i++) {
                        controlRegs[i].update(cycle);

                        for (int j = 0; j < 4; j++) {
                                registerWords[i][j].update(cycle);
                                asciiRegWords[i][j].update(cycle);
                        }
                }
        }

        private void updateMem() {

                assembler.memAddress = (Integer) memspinner.getValue();

                assembler.updateMem();

                int cycle = assembler.controlRegs[2].value;

                for (int i = 0; i < 8; i++) {
                        memLabels[i].setText(String.format("%4d", assembler.memAddress + i * 16));
                        for (int j = 0; j < 4; j++) {
                                memWords[i][j].update(cycle);
                                asciiMemWords[i][j].update(cycle);
                        }
                }
        }

        private void updateBitmap() {
                Word word = assembler.bitMap;
                StringBuilder s = new StringBuilder();

                for (int i = 3; i >= 0; i--) {
                        s.append(String.format("%8s", Integer.toBinaryString(word.data[i] & 0xFF)).replace(' ', '0'));
                }

                for (int i = 0; i < 32; i++) {
                        char c = s.charAt(i);
                        bitLabels[i].setText("" + c);
                        if (c == '1') {
                                bitLabels[i].setBackground(Colors.UNUPDATED);
                        } else {
                                bitLabels[i].setBackground(Colors.BACKGROUND);
                        }
                }
        }

        private void updateInst() {
                lastval.setText(assembler.lastCmd);
                nextval.setText(assembler.nextInst);
        }

        private void update() {
                updateRegs();
                updateMem();
                updateInst();
                updateBitmap();
        }
}
