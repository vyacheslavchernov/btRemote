package com.vych;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

public class WaitThread implements Runnable{

    public WaitThread() {
    }


    @Override
    public void run() {
        waitForConnection();
    }

    private void waitForConnection() {
        LocalDevice local = null;

        StreamConnectionNotifier notifier = null;
        StreamConnection connection = null;

        try {
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            System.out.println(local.getBluetoothAddress());

            UUID uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
            System.out.println(uuid);

            String url = "btspp://localhost:" + uuid + ";name=btRemote";
            System.out.println(url);

            notifier = (StreamConnectionNotifier) Connector.open(url);
        } catch (BluetoothStateException e) {
            System.out.println("Bluetooth is not turned on.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // waiting for connection
        while(true) {
            try {
                System.out.println("waiting for connection...");
                connection = notifier.acceptAndOpen();

                Thread processThread = new Thread(new ProcessConnectionThread(connection));
                processThread.start();

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
