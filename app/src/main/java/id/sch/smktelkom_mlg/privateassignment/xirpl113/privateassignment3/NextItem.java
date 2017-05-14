package id.sch.smktelkom_mlg.privateassignment.xirpl113.privateassignment3;

import java.io.Serializable;

/**
 * Created by Fay on 14/05/2017.
 */

public class NextItem implements Serializable {
    private String imageUrl;
    private String title;

    public NextItem(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}
