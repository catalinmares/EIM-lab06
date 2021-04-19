package ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.net.Socket;

import ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.general.Constants;
import ro.pub.cs.systems.eim.lab06.ftpserverwelcomemessage.general.Utilities;

public class FTPServerCommunicationAsyncTask extends AsyncTask<String, String, Void> {

    private TextView welcomeMessageTextView;

    public FTPServerCommunicationAsyncTask(TextView welcomeMessageTextView) {
        this.welcomeMessageTextView = welcomeMessageTextView;
    }

    @Override
    protected Void doInBackground(String... params) {
        Socket socket;
        try {
            socket = new Socket(params[0], Constants.FTP_PORT);
            Log.v(Constants.TAG, "Connected to: " + socket.getInetAddress() + ":" + socket.getPort());

            BufferedReader bufferedReader = Utilities.getReader(socket);
            String line = bufferedReader.readLine();

            if (line != null && line.startsWith(Constants.FTP_MULTILINE_START_CODE)) {
                line = bufferedReader.readLine();

                while (line != null) {
                    if (!line.equals(Constants.FTP_MULTILINE_END_CODE1) && !line.startsWith(Constants.FTP_MULTILINE_END_CODE2)) {
                        Log.v(Constants.TAG, "A line has been received from the FTP server: " + line);
                        publishProgress(line);
                    } else {
                        break;
                    }

                    line = bufferedReader.readLine();
                }
            }

            socket.close();
        } catch (Exception exception) {
            Log.d(Constants.TAG, exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        welcomeMessageTextView.setText("");
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        welcomeMessageTextView.append("\n" + progress[0]);
    }

    @Override
    protected void onPostExecute(Void result) {}

}
