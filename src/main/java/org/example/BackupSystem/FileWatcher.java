package org.example.BackupSystem;

import java.io.File;
import org.apache.commons.io.monitor.*;

public class FileWatcher
{
    public static void main(String[] args) throws Exception
    {
        File directory = new File("src/main/java/org/example/Files");

        FileAlterationObserver observer = getFileAlterationObserver(directory);

        FileAlterationMonitor monitor = new FileAlterationMonitor(500);
        monitor.addObserver(observer);

        monitor.start();
    }

    private static FileAlterationObserver getFileAlterationObserver(File directory)
    {
        FileAlterationObserver observer = new FileAlterationObserver(directory);

        observer.addListener(new FileAlterationListenerAdaptor()
        {
            @Override
            public void onFileCreate(File file)
            {
                BackUp.Work();
            }

            @Override
            public void onFileDelete(File file)
            {
                BackUp.Work();
            }

            @Override
            public void onFileChange(File file)
            {
                BackUp.Work();
            }
        });
        return observer;
    }
}

