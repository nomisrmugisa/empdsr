package org.pdsr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class CONSTANTS {

	public static final String FACILITY_ID="PDRS";
    public static final String ROOT_LOCATION ="attachments";
    
    public static final void writeToDisk(final String USER, final String MEMO, String filename, byte bytes[])
            throws IOException {
        Path path = Paths.get(ROOT_LOCATION, new String[0]).resolve(USER).resolve(MEMO).resolve(filename);
        Files.createDirectories(path.getParent(), new FileAttribute[0]);
        Files.write(path, bytes, new OpenOption[0]);
    }

    public static final byte[] readFromDisk(final String USER, final String MEMO, String filename) throws IOException {
        Path path = Paths.get(ROOT_LOCATION, new String[0]).resolve(USER).resolve(MEMO).resolve(filename);
        return Files.readAllBytes(path);
    }

    public static final byte[] readICD10(String filename) throws IOException {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public static final boolean removeFromDisk(final String USER, final String MEMO, String filename) throws IOException {
        Path path = Paths.get(ROOT_LOCATION, new String[0]).resolve(USER).resolve(MEMO).resolve(filename);
        return Files.deleteIfExists(path);
    }

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isUnix() {
        return OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0;
    }

    public static boolean isSolaris() {
        return OS.contains("sunos");
    }

    private static final String OS = System.getProperty("os.name").toLowerCase();
    
}