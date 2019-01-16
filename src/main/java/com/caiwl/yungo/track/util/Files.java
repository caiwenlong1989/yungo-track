package com.caiwl.yungo.track.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author caiwl
 * @date 2019/1/16 11:43
 */
public class Files {

    public static final byte[] PIXEL = Files.load("pixel.png");
    public static final byte[] FAVICON = Files.load("favicon.ico");
    public static final byte[] EMPTY_BODY = new byte[0];

    public static byte[] load(String file) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (final InputStream input = loader.getResourceAsStream(file);
             final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buff = new byte[1024 * 4];
            int n;
            while ((n = input.read(buff)) != -1) {
                output.write(buff, 0, n);
            }
            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
