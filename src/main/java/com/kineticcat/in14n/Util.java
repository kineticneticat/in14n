package com.kineticcat.in14n;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Util {

    public String getFile(String path) {
        BufferedReader readIn = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
                .getResourceAsStream(path)), StandardCharsets.UTF_8));
        String file = "";
        String line = "";
        while (true) {
            try {
                line = readIn.readLine();
            } catch (IOException e) {
                // cant really fix it
                e.printStackTrace();
            }
            if (line != null) {
                file = file + line + "\n";
            } else {
                break;
            }
        }
        return file;
    }

}
