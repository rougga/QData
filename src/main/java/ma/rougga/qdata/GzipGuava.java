package ma.rougga.qdata;
import com.google.common.io.ByteStreams;
import java.io.*;
import java.util.zip.GZIPOutputStream;
import java.util.zip.GZIPInputStream;

public class GzipGuava {

    // Method to compress a string
    public static byte[] compress(String text) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOS = new GZIPOutputStream(byteStream)) {
            gzipOS.write(text.getBytes());
        }
        return byteStream.toByteArray();
    }

    // Method to decompress a byte array
    public static String decompress(byte[] compressedData) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(compressedData);
        try (GZIPInputStream gzipIS = new GZIPInputStream(byteStream)) {
            return new String(ByteStreams.toByteArray(gzipIS));
        }
    }
}
