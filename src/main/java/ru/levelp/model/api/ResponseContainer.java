package ru.levelp.model.api;

import ru.levelp.model.entities.BackupInfo;

import java.util.ArrayList;

public class ResponseContainer {
    private int id;
    private String method;
    private long ts;
    private int backupId;
    private ArrayList<BackupInfo> history;
}
