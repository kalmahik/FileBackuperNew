package ru.levelp.view;

import ru.levelp.presenter.ConsolePresenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleView {
    private BufferedReader reader;
    private ConsolePresenter presenter;
    private boolean running = true;

    public ConsoleView(ConsolePresenter presenter) {
        this.presenter = presenter;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        while (running) {
            showMenu();
            try {
                String choice = reader.readLine();
                presenter.onUserAnswered(choice);
            } catch (IOException ignored) {
            }
        }
    }

    private void showMenu() {
        System.out.println("1 - upload");
        System.out.println("4 - quit");
    }

    public void quit() {
        running = false;
    }

    public void showWrongCommand(String command) {
        System.err.println("Wrong command: " + command);
    }

    public String requestFilePath() {
        return requestSomeInput("Input path to file or directory:");
    }

    public String requestBackupName() {
        return requestSomeInput("Input backup name:");
    }

    private String requestSomeInput(String printMessage) {
        System.out.println(printMessage);
        try {
            return reader.readLine();
        } catch (IOException ignored) {
        }
        return null;
    }

    public void showZipError() {
        System.err.println("Data packing error");
    }

    public void showDone() {
        System.out.println("Complete");
    }

    public void showUploadError() {
        System.out.println("Uploading error");
    }

    public void showInputError() {
        System.out.println("File or dir not found or path is incorrect");
    }

    public void showNameError() {
        System.out.println("File name is incorrect");
    }
}
