package model;

/**
 * Created by PERSONAL on 5/29/2015.
 */
public class Feeling {
    private String title;
    private String content;
    private String image;
    private String TAG="FEELING_MODEL";

    public Feeling() {
    }

    public Feeling(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
