package com.vych;

import javax.microedition.io.StreamConnection;
import java.io.IOException;
import java.io.InputStream;

public class ProcessConnectionThread implements Runnable{
    private static final int EXIT_CMD = -1;
    private static final int KEY_RIGHT = 1;
    private static final int KEY_LEFT = 2;

    private final StreamConnection mConnection;

    public ProcessConnectionThread(StreamConnection connection) {
        mConnection = connection;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = mConnection.openInputStream();

            while(true) {
                int command = inputStream.read();

                if (command == EXIT_CMD) {
                    System.out.println("finish process");
                    break;
                }

                processCommand(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("waiting for input");
    }

    private void processCommand(int command) {
        switch (command) {
            case KEY_LEFT:
                System.out.println("left");
                break;
            case KEY_RIGHT:
                System.out.println("right");
                break;
        }
    }
}
