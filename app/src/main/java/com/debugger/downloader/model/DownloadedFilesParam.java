package com.debugger.downloader.model;

public class DownloadedFilesParam {

    private String path;
    private String name;
    private Integer isFavourite;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer isFavourite() {
        return isFavourite;
    }

    public void setFavourite(Integer favourite) {
        isFavourite = favourite;
    }
}
