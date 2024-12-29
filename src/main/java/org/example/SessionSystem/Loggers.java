package org.example.SessionSystem;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Loggers
{
    public static final Logger session_logger = Logger.getLogger("Session");
    public static final Logger password_request_logger = Logger.getLogger("Password Request");
    public static final Logger team_logger = Logger.getLogger("Team");
    public static final Logger backup_logger = Logger.getLogger("Backup");
    public static final Logger anomaly_session_logger = Logger.getLogger("AnomalySession");
    public static final Logger anomaly_password_request_logger = Logger.getLogger("AnomalyPasswordRequest");
    public static final Logger anomaly_backup_logger = Logger.getLogger("anomaly_backup_logger");

    public static void InitializeSessionLogger()
    {
        FileHandler fileHandler;
        try
        {
            fileHandler = new FileHandler("src/main/java/org/example/Logs/Session.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            session_logger.addHandler(fileHandler);
            session_logger.setLevel(Level.INFO);
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void InitializeAnomalySessionLogger()
    {
        FileHandler fileHandler;
        try
        {
            fileHandler = new FileHandler("src/main/java/org/example/Anomalies/SessionAnomaly.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            anomaly_session_logger.addHandler(fileHandler);
            anomaly_session_logger.setLevel(Level.INFO);
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void InitializePasswordRequestLogger()
    {
        FileHandler fileHandler;
        try
        {
            fileHandler = new FileHandler("src/main/java/org/example/Logs/PasswordRequest.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            password_request_logger.addHandler(fileHandler);
            password_request_logger.setLevel(Level.INFO);
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void InitializeAnomalyPasswordRequestLogger()
    {
        FileHandler fileHandler;
        try
        {
            fileHandler = new FileHandler("src/main/java/org/example/Anomalies/RequestAnomaly.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            anomaly_password_request_logger.addHandler(fileHandler);
            anomaly_password_request_logger.setLevel(Level.INFO);
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void InitializeTeamLogger()
    {
        FileHandler fileHandler;
        try
        {
            fileHandler = new FileHandler("src/main/java/org/example/Logs/Team.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            team_logger.addHandler(fileHandler);
            team_logger.setLevel(Level.INFO);

        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void InitializeBackupLogger()
    {
        FileHandler fileHandler;
        try
        {
            fileHandler = new FileHandler("src/main/java/org/example/Logs/Backup.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            backup_logger.addHandler(fileHandler);
            backup_logger.setLevel(Level.INFO);
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void InitializeAnomalyBackupLogger()
    {
        FileHandler fileHandler;
        try
        {
            fileHandler = new FileHandler("src/main/java/org/example/Anomalies/BackupAnomaly.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            anomaly_backup_logger.addHandler(fileHandler);
            anomaly_backup_logger.setLevel(Level.INFO);
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

}
