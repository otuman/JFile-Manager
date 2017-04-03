package com.jerotoma.jfilemanager.Model;

/**
 * Created by Otoman on 02/04/2017.
 */

public class Drive {
    private String dariveName;
    private String icon;
    private String content;
    private int progressStatus;

    public Drive(String name, String icon, String content, int progressStatus) {
        this.dariveName = name;
        this.icon    = icon;
        this.content = content;
        this.progressStatus = progressStatus;
    }
    public int getProgressStatus() {
        return progressStatus;
    }
    public String getDariveName() {
        return dariveName;
    }

    public String getIcon() {
        return icon;
    }

    public String getContent() {
        return content;
    }
}
