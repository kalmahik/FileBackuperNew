package ru.levelp.model.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ApiManager {
    private static final String IP = "localhost";
    private static final int PORT = 8686;
    private static final ApiManager instance = new ApiManager();

    private ApiManager() {

    }

    public static ApiManager getInstance() {
        return instance;
    }

    public boolean uploadBackup(String pathToZip, String backupName) {
        Socket socket = initConnection();
        if (socket != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                RequestContainer request = new RequestContainer(Methods.UPLOAD);
                request.setBackupName(backupName);
                //отпаравить jsonRequest через writer, закрыть writer
                //отправить файл
                //  inputStream - это pathToZip
                //  outputStream - socket.getOutputStream()
                //Ждем с помощью reader.readLine() ответа от сервера
                //получили jsonResponse -> ResponseContainer
                //отключаем все потоки
                disconnect(socket);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    private Socket initConnection() {
        try {
            return new Socket(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void disconnect(Socket socket) {
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
