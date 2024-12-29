package org.example.Process;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Process
{
    public static ScheduledExecutorService scheduler_Backup;
    public static java.lang.Process process_Backup;

    public static java.lang.Process StartBackup()
    {
        scheduler_Backup = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder processBuilder = new ProcessBuilder
                        (
                        "java", "-cp",
                                System.getProperty("java.class.path"), // classpath buraya eklendi
                                "org.example.BackupSystem.BackUp"
                        );

                processBuilder.environment().put("LOG_PATH", "src/main/java/org/example/Logs/Backup.txt");
                processBuilder.directory(new File("C:\\Users\\habil\\OneDrive\\Desktop\\Proje\\3.Sınıf\\1. Dönem\\Yazılım Geliştirme\\Odev2\\Qod"));
                processBuilder.inheritIO();
                process_Backup = processBuilder.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler_Backup.scheduleAtFixedRate(task, 0, 30, TimeUnit.SECONDS);
        return process_Backup;
    }
}
