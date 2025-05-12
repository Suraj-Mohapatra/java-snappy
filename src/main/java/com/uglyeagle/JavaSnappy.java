package com.uglyeagle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.xerial.snappy.Snappy;

public class JavaSnappy {

    public static void main(String[] args) {

        StringBuilder builder = new StringBuilder();
        // InputStream stream = JavaSnappy.class.getResourceAsStream("/input.txt"); // also works
        try (InputStream inputStream = JavaSnappy.class.
                getClassLoader().
                getResourceAsStream("input.txt");) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            char[] buffer = new char[512]; //8192 for best performance
            int numberOfCharsRead;

            while ((numberOfCharsRead = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, numberOfCharsRead);
            }

            System.out.println("end of file reached");
            System.out.println("size of input after reading is " + builder.length());

        } catch (IOException | NullPointerException ie) {
            System.err.println("error occured");
        }

        byte[] uncompressed = null;
        byte[] compressed = null;

        try {
            compressed = Snappy.compress(builder.toString().getBytes("UTF-8"));
            System.out.println("size of compressed text is " + compressed.length);
            uncompressed = Snappy.uncompress(compressed);
            System.out.println("size of uncompressed text is " + uncompressed.length);
        } catch (UnsupportedEncodingException ex) {
            System.err.println("error occured");
        } catch (IOException ie) {
            System.err.println("error occured");
        }

    }
}
