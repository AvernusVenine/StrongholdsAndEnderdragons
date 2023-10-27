package avernusvenine.sne;

public class Globals {

    public static String convertWidthToMinecraftCode(int width){

        int code = 0xD0000 + width;
        int highSurrogate = 0xD800 + ((code - 0x10000) >> 10);
        int lowSurrogate = 0xDC00 + ((code - 0x10000) & 0x3FF);

        return new String(new int[]{highSurrogate, lowSurrogate}, 0, 2);
    }

}
