package com.kineticcat.in14n.block.multiblock;

import com.google.gson.Gson;
import com.kineticcat.in14n.Util;
import net.minecraft.resources.ResourceLocation;

public class MBPattern {

    public static MBPattern fromFile(String fn) {

        ResourceLocation file = new ResourceLocation(fn);
        String path = file.getPath();

        Util UTIL = new Util();
        String text = UTIL.getFile(path);

        Gson gson = new Gson();
        return gson.fromJson(text, MBPattern.class);

    }



    //data uses YXZ
    //offset uses XYZ
    //for ease of writing mb files lol
    public String[][][] data;
    public int[] offset;
    public MBPattern rotate() {
        int X = data.length;
        int Y = data[0].length;
        int Z = data[0][0].length;
        MBPattern out = new MBPattern();

        for (int x = 0; x < X; x++) {
            // transpose
            for (int y = 0; y < Y; y++) {
                for (int z = 0; z < Z; z++) {
                    out.data[x][z][y] = data[x][y][z];
                }
            }

            //mirror
            for (int y = 0; y < Y; y++) {
                out.data[x][y] = reverse(out.data[x][y]);
            }
        }
        return out;
    }
    // why is this such a pain
    private static String[] reverse(String[] row) {
        String[] out = new String[row.length];
        int start = 0;
        int end = row.length - 1;
        while (start < end) {
            out[end] = row[start];
            out[start] = row[end];
            start++;
            end--;
        }
        return out;
    }

}
