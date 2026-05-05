package utils;

import java.io.File;

public class FileStorageConfig {

    public static final String DIR = System.getProperty("user.home") + "/IdeaProjects/module_2_4/uploads/";


    public static void checkDirectory() {
        File dir = new File(DIR);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) System.out.println("Created upload directory: " + DIR);
        }
    }
}