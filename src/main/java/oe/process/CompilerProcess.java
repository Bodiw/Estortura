package oe.process;

public class CompilerProcess {

    public String compiler, code, start, output;

    public CompilerProcess(String compiler, String code, String start, String output) {
        this.compiler = compiler;
        this.code = code;
        this.start = start;
        this.output = output;
    }

    public void compile() throws Exception {
        ProcessBuilder pb = new ProcessBuilder(compiler, "-e", start, "-o", output, code);
        pb.inheritIO();
        Process p = pb.start();
        p.waitFor();
    }
}
