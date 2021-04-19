package ro.pub.cs.systems.eim.lab06.singlethreadedserver.network;

import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.lab06.singlethreadedserver.general.Constants;
import ro.pub.cs.systems.eim.lab06.singlethreadedserver.general.Utilities;

public class CommunicationThread extends Thread {
    private Socket socket;
    private EditText serverTextEditText;

    public CommunicationThread(Socket socket, EditText serverTextEditText) {
        this.socket = socket;
        this.serverTextEditText = serverTextEditText;
    }

    @Override
    public void run() {
        try {
            Log.v(Constants.TAG, "Connection opened with " + socket.getInetAddress() + ":" + socket.getLocalPort());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(Constants.TAG, e.getMessage());
                if (Constants.DEBUG) {
                    e.printStackTrace();
                }
            }

            PrintWriter printWriter = Utilities.getWriter(socket);
            printWriter.println(serverTextEditText.getText().toString());
            socket.close();
            Log.v(Constants.TAG, "Connection closed");
        } catch (IOException e) {
            Log.e(Constants.TAG, "An exception has occurred: " + e.getMessage());
            if (Constants.DEBUG) {
                e.printStackTrace();
            }
        }


    }
}
