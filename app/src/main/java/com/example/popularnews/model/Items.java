package com.example.popularnews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {

    @Expose
    @SerializedName("dislike_count")
    private String dislike_count;
    @Expose
    @SerializedName("like_count")
    private String like_count;
    @Expose
    @SerializedName("created_time")
    private String created_time;
    @Expose
    @SerializedName("taklif")
    private String taklif;
    @Expose
    @SerializedName("region")
    private Region region;
    @Expose
    @SerializedName("fio")
    private String fio;
    @Expose
    @SerializedName("category")
    private Category category;
    @Expose
    @SerializedName("id")
    private String id;

    public String getDislike_count() {
        return dislike_count;
    }

    public void setDislike_count(String dislike_count) {
        this.dislike_count = dislike_count;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getTaklif() {
        return taklif;
    }

    public void setTaklif(String taklif) {
        this.taklif = taklif;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
    }

    public void setDislike_count(int dislike_count) {
    }

    public void setLike_count(int like_count) {
    }

    public void setRegion(Object region) {
    }

    public static class Region {
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


}
