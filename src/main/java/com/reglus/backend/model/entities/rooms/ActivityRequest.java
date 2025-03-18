package com.reglus.backend.model.entities.rooms;

import java.time.LocalDateTime;

public class ActivityRequest {
    private Long roomId;
    private Long educatorId;
    private String title;
    private String description;
    private byte[] fileData;
    private Integer maxPoints;
    private LocalDateTime dataLimit;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getEducatorId() {
        return educatorId;
    }

    public void setEducatorId(Long educatorId) {
        this.educatorId = educatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFileData() { return fileData; }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public LocalDateTime getDataLimit() {
        return dataLimit;
    }

    public void setDataLimit(LocalDateTime dataLimit) {
        this.dataLimit = dataLimit;
    }
}