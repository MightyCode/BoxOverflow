package box_overflow.util;

import java.io.*;


public abstract class FileMethods {
    public static boolean copy(File source, File dest) {
        try (InputStream sourceFile = new java.io.FileInputStream(source);
             OutputStream destinationFile = new FileOutputStream(dest)) {
            // Lecture par segment de 0.5Mo
            byte buffer[] = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1){
                destinationFile.write(buffer, 0, nbLecture);
            }
        } catch (IOException e){
            e.printStackTrace();
            return false; // Erreur
        }
        return true; // Résultat OK
    }

    public static boolean copy(String source, String dest) {
        return copy(new File(source), new File(dest));

    }
}
