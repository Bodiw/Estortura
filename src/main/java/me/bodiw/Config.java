package me.bodiw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Config {

    String configPath;
    public String emulator, compiler, config, code, start, bin, bitmapType, breakpoint;
    public int iniMem, iniStep = 1, iniSkip, iniBitmap;
    public float scale;

    public Config(String configPath) {
        this.configPath = configPath;
    }

    public void load() throws IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(configPath));

        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        HashMap<String, String> json = gson.fromJson(bufferedReader, type);
        bufferedReader.close();

        emulator = json.get("EMULADOR");
        compiler = json.get("COMPILADOR");
        config = json.get("CONF");
        code = json.get("CODIGO");
        breakpoint = json.get("TAG_BREAKPOINT");
        start = json.get("TAG_INICIO");

        iniMem = Integer.parseInt(json.get("MEM_ADDR_INICIO"));
        iniStep = Integer.parseInt(json.get("STEP_INICIO"));
        iniSkip = Integer.parseInt(json.get("SKIP_INICIO"));
        iniBitmap = Integer.parseInt(json.get("BITMAP_ADDR"));
        bitmapType = json.get("BITMAP_TYPE");

        scale = Float.parseFloat(json.get("SCALE"));
    }
}
