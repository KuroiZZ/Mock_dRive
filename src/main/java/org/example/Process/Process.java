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

    public static ScheduledExecutorService scheduler_Anomaly_Session;
    public static java.lang.Process process_Anomaly_Session;

    public static java.lang.Process StartSessionAnomaly()
    {
        scheduler_Anomaly_Session = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder processBuilder = new ProcessBuilder
                        (
                                "java", "-cp",
                                System.getProperty("java.class.path"), // classpath buraya eklendi
                                "org.example.BackupSystem.SessionAnomalyFinder"
                        );

                processBuilder.directory(new File("C:\\Users\\habil\\OneDrive\\Desktop\\Proje\\3.Sınıf\\1. Dönem\\Yazılım Geliştirme\\Odev2\\Qod"));
                processBuilder.inheritIO();
                process_Anomaly_Session = processBuilder.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler_Anomaly_Session.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        return process_Backup;
    }

    public static ScheduledExecutorService scheduler_Anomaly_Password_Request;
    public static java.lang.Process process_Anomaly_Password_Request;

    public static java.lang.Process StartPasswordRequestAnomaly()
    {
        scheduler_Anomaly_Password_Request = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder processBuilder = new ProcessBuilder
                        (
                                "java", "-cp",
                                System.getProperty("java.class.path"), // classpath buraya eklendi
                                "org.example.BackupSystem.RequestAnomalyFinder"
                        );

                processBuilder.directory(new File("C:\\Users\\habil\\OneDrive\\Desktop\\Proje\\3.Sınıf\\1. Dönem\\Yazılım Geliştirme\\Odev2\\Qod"));
                processBuilder.inheritIO();
                process_Anomaly_Password_Request = processBuilder.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler_Anomaly_Password_Request.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        return process_Backup;
    }


    public static ScheduledExecutorService scheduler_Backup_Request;
    public static java.lang.Process process_Backup_Anomaly;

    public static java.lang.Process StartBackupAnomaly()
    {
        scheduler_Backup_Request = Executors.newScheduledThreadPool(1);

        Runnable task = () ->
        {
            try
            {
                ProcessBuilder processBuilder = new ProcessBuilder
                        (
                                "java", "-cp",
                                System.getProperty("java.class.path"), // classpath buraya eklendi
                                "org.example.BackupSystem.BackupAnomalyFinder"
                        );

                processBuilder.directory(new File("C:\\Users\\habil\\OneDrive\\Desktop\\Proje\\3.Sınıf\\1. Dönem\\Yazılım Geliştirme\\Odev2\\Qod"));
                processBuilder.inheritIO();
                process_Backup_Anomaly = processBuilder.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        };

        scheduler_Backup_Request.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        return process_Backup;
    }
}
