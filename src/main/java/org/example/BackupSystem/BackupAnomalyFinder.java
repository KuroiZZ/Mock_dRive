package org.example.BackupSystem;

import org.example.SessionSystem.Loggers;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BackupAnomalyFinder
{
    private static final String ANOMALY_LOG_FILE = "src/main/java/org/example/Anomalies/RequestAnomaly.txt";
    private static long lastReadPosition = 0;

    public static void main(String[] args)
    {
        Loggers.InitializeAnomalyBackupLogger();
        ReadAndCombineBackupLog();
    }

    public static ArrayList<String> ReadAndCombineBackupLog()
    {
        readLastPosition();

        ArrayList<String> loglist = new ArrayList<String>();
        String filePath = "src/main/java/org/example/Logs/Backup.txt";
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r"))
        {
            raf.seek(lastReadPosition);
            String line1, line2;
            while ((line1 = raf.readLine()) != null)
            {
                line2 = raf.readLine();
                if (line2 != null) {
                    String logEntry = line1.trim() + " " + line2.trim();
                    if (logEntry.contains("failed"))
                    {
                        loglist.add(logEntry);
                    }
                }
            }

            lastReadPosition = raf.getFilePointer();
            writeLastPosition();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            FindAnomalies(loglist);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
        return loglist;
    }

    public static void FindAnomalies(ArrayList<String> logs) throws ParseException
    {
        for (String log : logs)
        {
            String[] parts = log.split(" ");
            String dateStr = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];
            Loggers.anomaly_backup_logger.info("There was a problem with the backup dated " + dateStr );
        }
    }

    private static void writeLastPosition()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/org/example/Anomalies/lastBackup.txt")))
        {
            writer.write(String.valueOf(lastReadPosition));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void readLastPosition()
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/example/Anomalies/lastBackup.txt")))
        {
            String position = reader.readLine();
            if (position != null) {
                lastReadPosition = Long.parseLong(position);
            }
        }
        catch (IOException e)
        {
            lastReadPosition = 0;
        }
    }
}
