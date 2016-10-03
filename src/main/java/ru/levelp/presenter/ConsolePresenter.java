package ru.levelp.presenter;

import ru.levelp.model.api.ApiManager;
import ru.levelp.model.utils.FileCompressor;
import ru.levelp.view.ConsoleView;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ConsolePresenter {
    private ConsoleView view;
    private FileCompressor compressor;

    public ConsolePresenter() {
        compressor = new FileCompressor();
    }

    public void setView(ConsoleView view) {
        this.view = view;
    }

    public void start() {
        view.start();
    }

    public void onUserAnswered(String answer) {
        try {
            int parsedAnswer = Integer.parseInt(answer);
            switch (parsedAnswer) {
                case 1:
                    uploadFileCase();
                    break;
                case 4:
                    view.quit();
                    break;
                default:
                    view.showWrongCommand(answer);
            }
        } catch (NumberFormatException e) {
            view.showWrongCommand(answer);
        }
    }

    private void uploadFileCase() {
        String path = view.requestFilePath();
        if (path != null && !path.isEmpty()) {
            Path parsedPath = FileSystems.getDefault().getPath(path);
            File file = parsedPath.toFile();
            if (file.exists()) {
                String backupName = view.requestBackupName();
                if (backupName != null && !backupName.isEmpty()) {
                    File backupTemp = new File("temp_files", backupName);
                    try {
                        compressor.zip(file.getPath(), backupTemp.getPath());
                        boolean result = ApiManager.getInstance()
                                .uploadBackup(backupTemp.getPath(), backupName);
                        if (result) {
                            view.showDone();
                        } else {
                            view.showUploadError();
                        }
                    } catch (IOException e) {
                        view.showZipError();
                    }
                } else{
                    view.showNameError();
                }
            } else{
                view.showInputError();
            }
        } else {
            view.showInputError();
        }
    }
}
