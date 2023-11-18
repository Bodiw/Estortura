package oe.process;

import java.io.IOException;

public class CompilerProcess {

    public String compiler, code, start, output;

    public CompilerProcess(String compiler, String code, String start, String output) {
        this.compiler = compiler;
        this.code = code;
        this.start = start;
        this.output = output;
    }

    public void compile() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(compiler, "-e", start, "-o", output, code);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        p.waitFor();

        String output = new String(p.getInputStream().readAllBytes());

        if (output.contains("88110.ens-ERROR")) {
            throw new RuntimeException(output);
        } else if (output.contains("No pude abrir")) {
            throw new RuntimeException(output + code);
        }
    }
}
