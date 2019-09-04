package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author dragon.cl 2019/9/4 5:28 PM
 */
public class IoUtils {

    /**
     * 4kb
     */
    private static final int DEFAULT_INITIAL_BUFFER_SIZE = 4 * 1024;
    private static final String DEFAULT_CHARSET = "UTF-8";

    private IoUtils() {}

    public static void close(OutputStream os) {
        if (os == null) {
            return;
        }

        try {
            os.close();
        } catch (Throwable t) {
            // ignore
        }
    }

    public static byte[] read(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(
            DEFAULT_INITIAL_BUFFER_SIZE);
        try {
            byte[] buffer = new byte[DEFAULT_INITIAL_BUFFER_SIZE];
            int len;
            while ((len = input.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }

            return output.toByteArray();
        } finally {
            close(output);
        }
    }

    public static String read2String(InputStream input) throws IOException {
        return read2String(input, DEFAULT_CHARSET);
    }

    public static String read2String(InputStream input, String charset)
        throws IOException {
        byte[] data = read(input);

        try {
            return new String(data, charset);
        } catch (UnsupportedEncodingException e) {
            return new String(data);
        }
    }
}
