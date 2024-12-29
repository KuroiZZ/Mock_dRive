package org.example.BackupSystem;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileWatcher
{
    private static final Path WATCH_DIR = Paths.get("src/main/java/org/example/Files");

    public static void main(String[] args)
    {
        try
        {
            Watch();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void Watch() throws IOException
    {

        WatchService watchService = FileSystems.getDefault().newWatchService();

        registerAllDirectories(WATCH_DIR, watchService);

        while (true)
        {
            WatchKey key;
            try
            {
                key = watchService.take();
            }
            catch (InterruptedException e)
            {
                return;
            }

            for (WatchEvent<?> event : key.pollEvents())
            {
                WatchEvent.Kind<?> kind = event.kind();
                Path fileName = (Path) event.context();

                System.out.println("Değişiklik türü: " + kind + ", Dosya: " + fileName);

                if (kind == StandardWatchEventKinds.ENTRY_CREATE)
                {
                    System.out.println("C");
                }
                else if (kind == StandardWatchEventKinds.ENTRY_MODIFY)
                {
                    System.out.println("M");
                }
                else if (kind == StandardWatchEventKinds.ENTRY_DELETE)
                {
                    System.out.println("D");
                }
            }

            boolean valid = key.reset();
            if (!valid)
            {
                break;
            }
        }

    }

    private static void registerAllDirectories(Path startDir, WatchService watchService) throws IOException
    {
        Files.walkFileTree(startDir, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
            {
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_MODIFY,
                        StandardWatchEventKinds.ENTRY_DELETE);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}

