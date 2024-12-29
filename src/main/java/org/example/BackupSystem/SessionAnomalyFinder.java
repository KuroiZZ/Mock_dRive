package org.example.BackupSystem;

import org.example.SessionSystem.Loggers;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SessionAnomalyFinder
{
    private static final String ANOMALY_LOG_FILE = "src/main/java/org/example/Anomalies/SessionAnomaly.txt";
    private static long lastReadPosition = 0;

    public static void main(String[] args)
    {
        Loggers.InitializeAnomalySessionLogger();
        ReadAndCombineSessionLog();
    }

    public static ArrayList<String> ReadAndCombineSessionLog()
    {
        readLastPosition();

        ArrayList<String> loglist = new ArrayList<String>();
        String filePath = "src/main/java/org/example/Logs/Session.txt";
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r"))
        {
            raf.seek(lastReadPosition);
            String line1, line2;
            while ((line1 = raf.readLine()) != null)
            {
                line2 = raf.readLine();
                if (line2 != null) {
                    String logEntry = line1.trim() + " " + line2.trim();
                    if (logEntry.contains("login failed"))
                    {
                        loglist.add(TakeTimeAndUsername(logEntry));
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

    public static String TakeTimeAndUsername(String logline)
    {
        return logline.substring(0, 22) + logline.split("User ")[1].split(" ")[0];
    }

    public static void FindAnomalies(ArrayList<String> logs) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");

        Map<String, List<Date>> userLogs = new HashMap<>();


        for (String log : logs)
        {
            String[] parts = log.split(" ");
            String dateStr = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3]; // "Ara 29, 2024 12:43:07"
            String userName = parts[4]; // Kullanıcı adı

            Date date = sdf.parse(dateStr);

            userLogs.computeIfAbsent(userName, k -> new ArrayList<>()).add(date);
        }

        for (Map.Entry<String, List<Date>> entry : userLogs.entrySet())
        {
            String userName = entry.getKey();
            List<Date> timestamps = entry.getValue();
            Collections.sort(timestamps);

            for (int i = 0; i < timestamps.size() - 2; i++)
            {
                Date first = timestamps.get(i);
                Date second = timestamps.get(i + 1);
                Date third = timestamps.get(i + 2);

                long diff1 = (second.getTime() - first.getTime()) / 1000;
                long diff2 = (third.getTime() - second.getTime()) / 1000;

                if (diff1 <= 30 && diff2 <= 30)
                {
                    Loggers.anomaly_session_logger.info("User " + userName + " try to log 3 times in 30 seconds.");
                    break;
                }
            }
        }
    }

    private static void writeLastPosition()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/org/example/Anomalies/lastSession.txt")))
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
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/example/Anomalies/lastSession.txt")))
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

