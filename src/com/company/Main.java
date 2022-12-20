package com.company;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class Main {

    public static void main(String[] args) {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get("D:\\watcher_demo");
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            System.out.println("Watch Service Registerd for Dir:" + dir.getFileName());
            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException e) {
                    return;
                }
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = watchEvent.kind();
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) watchEvent;
                    Path fileName=ev.context();
                    System.out.println(kind.name()+":"+fileName);
                    if(kind==ENTRY_MODIFY){
                        System.out.println("My Source File has Changed");
                    }
                }
                boolean valid= key.reset();
                if(!valid)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
