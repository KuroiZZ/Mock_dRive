package org.example.BackupSystem;

import org.example.SessionSystem.Loggers;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class BackUp
{
    private static final Path SOURCE_DIR = Paths.get("src/main/java/org/example/Files");
    private static final Path TARGET_DIR = Paths.get("src/main/java/org/example/FilesBackup");

    public static void main(String[] args)
    {

        Work();
    }

    public static void Work()
    {
        Loggers.InitializeBackupLogger();
        
        try
        {
            if (Files.exists(TARGET_DIR))
            {
                deleteDirectoryRecursively(TARGET_DIR);
            }
            Files.createDirectories(TARGET_DIR);
            backupDirectory(SOURCE_DIR, TARGET_DIR);
            Loggers.backup_logger.info("Backup completed.");
        }
        catch (IOException e)
        {
            Loggers.backup_logger.warning("Backup failed.");
            e.printStackTrace();
        }
    }

    public static void backupDirectory(Path source, Path target) throws IOException
    {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
            {
                Path targetDir = target.resolve(source.relativize(dir));
                if (!Files.exists(targetDir))
                {
                    Files.createDirectories(targetDir);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                Path targetFile = target.resolve(source.relativize(file));
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });

    }

    public static void deleteDirectoryRecursively(Path dir) throws IOException
    {
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
            {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
