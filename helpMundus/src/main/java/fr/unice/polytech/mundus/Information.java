package fr.unice.polytech.mundus;

import java.io.Serializable;

/**
 * Created by spong on 2016/8/18.
 */
public class Information implements Serializable {

    private String title = null;
    private String content = null;

    public Information(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return title + "\n" + content;
    }
}