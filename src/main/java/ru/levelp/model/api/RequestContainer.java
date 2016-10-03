package ru.levelp.model.api;

import ru.levelp.model.utils.DateUtil;

//JsonRPC
public class RequestContainer {
    private int id;
    private String method;
    private long ts;
    private int backupId;
    private String backupName;

    public RequestContainer(String method) {
        this.method = method;
        id = (int) (Math.random() * Integer.MAX_VALUE);
        ts = DateUtil.now();
    }

    public void setBackupId(int backupId) {
        this.backupId = backupId;
    }

    public void setBackupName(String backupName) {
        this.backupName = backupName;
    }
}
