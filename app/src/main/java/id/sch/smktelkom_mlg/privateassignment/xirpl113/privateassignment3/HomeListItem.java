package id.sch.smktelkom_mlg.privateassignment.xirpl113.privateassignment3;

/**
 * Created by Fay on 12/05/2017.
 */

class HomeListItem {
    private String imageUrl;
    private String head;
    private String desc;


    public HomeListItem(String imageUrl, String head, String desc) {

        this.imageUrl = imageUrl;
        this.head = head;
        this.desc = desc;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }
}