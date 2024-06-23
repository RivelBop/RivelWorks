package com.rivelbop.rivelworks.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Handles two output streams simultaneously.
 *
 * @author David Jerzak (RivelBop)
 */
public class DualOutputStream extends PrintStream {
    /**
     * The second stream to output to.
     */
    private final PrintStream OTHER_PRINT_STREAM;

    /**
     * Takes in two streams and stores them to output to.
     *
     * @param primary   The first stream.
     * @param secondary The second stream.
     */
    public DualOutputStream(OutputStream primary, PrintStream secondary) {
        super(primary);
        OTHER_PRINT_STREAM = secondary;
    }

    @Override
    public void write(int b) {
        super.write(b);
        OTHER_PRINT_STREAM.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        OTHER_PRINT_STREAM.write(b);
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);
        OTHER_PRINT_STREAM.write(buf, off, len);
    }

    @Override
    public void flush() {
        super.flush();
        OTHER_PRINT_STREAM.flush();
    }

    @Override
    public void close() {
        super.close();
        OTHER_PRINT_STREAM.close();
    }
}