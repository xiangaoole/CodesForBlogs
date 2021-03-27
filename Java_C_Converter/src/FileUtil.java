import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 文件读写工具类
 *
 * Created by Harold Gao on 2018/3/27.
 */
public class FileUtil {

    // 读取C语言写的float
    public static float readFloatFromC(DataInputStream dataInputStream) {
        byte[] bytes = new byte[Float.SIZE / 8];
        try {
            dataInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static int readIntFromC(InputStream in) throws IOException {
        byte[] bytes = new byte[Integer.SIZE / 8];
        in.read(bytes);
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static void writeIntWithNewlineToC(DataOutputStream out, int n) throws IOException {
        writeIntToC(out, n);
        out.writeByte('\n');
        out.flush();
    }

    public static void writeIntToC(DataOutputStream out, int n) throws IOException {
        n = Integer.reverseBytes(n);
        out.writeInt(n);
    }

    /**
     * 读取 C 语言写的 float 到 <code>Float[]</code> 数组
     *
     * @param dataInputStream
     * @param a
     * @throws IOException
     */
    public static void readFloatFromC(DataInputStream dataInputStream, float[] a) throws IOException {
        for (int i = 0; i < a.length; i++) {
            a[i] = readFloatFromC(dataInputStream);
        }
    }

    public static void writeFloatToC(DataOutputStream out, float f) throws IOException {
        writeIntToC(out, Float.floatToIntBits(f));
    }

    public static void writeFloatWithNewlineToC(DataOutputStream out, float f) throws IOException {
        writeIntWithNewlineToC(out, Float.floatToIntBits(f));
    }

    // 读取C语言写的short
    public static short readShortFromC(DataInputStream dataInputStream) throws IOException {
        byte[] bytes = new byte[Short.SIZE / 8];
        dataInputStream.read(bytes);
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public static void writeShortToC(DataOutputStream out, short s) throws IOException {
        s = Short.reverseBytes(s);
        out.writeShort(s);
    }

    /**
     * 读取C语言写的short到<code>Short[]</code>数组
     *
     * @param dataInputStream
     * @param a
     * @throws IOException
     */
    public static void readShortFromC(DataInputStream dataInputStream, short[] a) throws IOException {
        for (int i = 0; i < a.length; i++) {
            a[i] = readShortFromC(dataInputStream);
        }
    }

    /**
     * c写的字符数组转化为String
     *
     * @param bytes
     * @return
     */
    public static String byteArrayToString(byte[] bytes) {
        String s = null;
        try {
            s = new String(bytes, "GBK");
            s = s.substring(0, s.indexOf(0));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * String转化为C的字符数组 ！！！没有考虑数组越界
     *
     * @param s
     * @param bytes
     */
    public static void stringToByteArray(String s, byte[] bytes) {
        try {
            byte[] a = s.getBytes("GBK");
            System.arraycopy(a, 0, bytes, 0, a.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
