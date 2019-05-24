package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
            return;
        }

        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (hour,time) information type!");
            String city = bufferedReader.readLine();
            if (city == null || city.isEmpty()) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error receiving parameters from client (city / information type!");
                return;
            }
            HashMap<String, AlarmInfo> data = serverThread.getData();
            AlarmInfo weatherForecastInformation = null;

            if (data.containsKey("127.0.0.1")) {
                Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the cache...");
                weatherForecastInformation = data.get(city);
            } else {

                Log.i(Constants.TAG, "[COMMUNICATION THREAD] dsfsfs");
                weatherForecastInformation = new AlarmInfo("127.0.0.1", 2, 30);
                serverThread.setData(city, weatherForecastInformation);

            }

            String result = "" + weatherForecastInformation.ip_client + " " + weatherForecastInformation.hour + " "
                                + weatherForecastInformation.minute;
            printWriter.println(result);
            printWriter.flush();


        } catch (IOException ioException) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
        } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException ioException) {
                        Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                        if (Constants.DEBUG) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
    }
}
