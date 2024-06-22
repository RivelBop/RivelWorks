package com.rivelbop.rivelworks.io;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Outputs to both the console and a file.
 *
 * @author David Jerzak (RivelBop)
 */
public class ConsoleFileOutputStream extends DualOutputStream {
    /**
     * Creates an OutputStream simultaneously to the console and a file.
     *
     * @param fileName The name of the file to log to.
     * @throws FileNotFoundException The file is not found or doesn't exist.
     */
    public ConsoleFileOutputStream(String fileName) throws FileNotFoundException {
        super(System.out, new PrintStream(fileName));
    }
}