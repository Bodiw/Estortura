package me.bodiw.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {
    private static final Pattern PC_PATTERN = Pattern.compile("PC=(\\d+)");
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("PC=\\d+\\s+(.*?)\\s+Tot\\. Inst");
    private static final Pattern TOTAL_INSTRUCTIONS_PATTERN = Pattern.compile("Inst: (\\d+)");
    private static final Pattern CYCLE_PATTERN = Pattern.compile("Ciclo : (\\d+)");
    private static final Pattern FLAG_PATTERN = Pattern.compile("FL=(\\d) FE=(\\d) FC=(\\d) FV=(\\d) FR=(\\d)");
    private static final Pattern REGISTER_PATTERN = Pattern.compile("R(\\d{2}) = ([0-9A-F]{8}) h");
    private static final Pattern MEMORY_PATTERN = Pattern
            .compile("(\\d+)\\s+([0-9A-F]{8})\\s+([0-9A-F]{8})\\s+([0-9A-F]{8})\\s+([0-9A-F]{8})");

    public static Map<String, Object> readRegs(String input) {
        Map<String, Object> values = new HashMap<>();
        Matcher m;

        m = PC_PATTERN.matcher(input);
        if (m.find()) {
            values.put("PC", Integer.parseInt(m.group(1)));
        }

        m = INSTRUCTION_PATTERN.matcher(input);
        if (m.find()) {
            values.put("Instruction", m.group(1).trim().replaceAll("\\s+", " "));
        }

        m = TOTAL_INSTRUCTIONS_PATTERN.matcher(input);
        if (m.find()) {
            values.put("TotalInstructions", Integer.parseInt(m.group(1)));
        }

        m = CYCLE_PATTERN.matcher(input);
        if (m.find()) {
            values.put("Cycle", Integer.parseInt(m.group(1)));
        }

        m = FLAG_PATTERN.matcher(input);
        if (m.find()) {
            values.put("FL", Integer.parseInt(m.group(1)));
            values.put("FE", Integer.parseInt(m.group(2)));
            values.put("FC", Integer.parseInt(m.group(3)));
            values.put("FV", Integer.parseInt(m.group(4)));
            values.put("FR", Integer.parseInt(m.group(5)));
        }

        m = REGISTER_PATTERN.matcher(input);

        values.put("R00", new byte[] { 0, 0, 0, 0 });
        while (m.find()) {
            String reg = "R" + m.group(1);
            byte[] bytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                int value = Integer.parseInt(m.group(2).substring(i * 2, (i + 1) * 2), 16);
                bytes[i] = (byte) value;
            }
            values.put(reg, bytes);
        }

        return values;
    }

    public static Map<Integer, byte[][]> readMem(String input) {
        Map<Integer, byte[][]> memoryValues = new HashMap<>();
        Matcher m = MEMORY_PATTERN.matcher(input);

        while (m.find()) {
            int identifier = Integer.parseInt(m.group(1));
            byte[][] bytesArray = new byte[4][4];

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int value = Integer.parseInt(m.group(i + 2).substring(j * 2, (j + 1) * 2), 16);
                    bytesArray[i][j] = (byte) value;
                }
            }
            memoryValues.put(identifier, bytesArray);
        }
        return memoryValues;
    }
}
