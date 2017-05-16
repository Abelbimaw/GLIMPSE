package id.sch.smktelkom_mlg.privateassignment.xirpl113.privateassignment3;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Fay on 15/05/2017.
 */

public class PinnedItem extends SugarRecord implements Serializable {
    public String judul;
    public String deskripsi;
    public String urlgambar;

    public PinnedItem() {
    }

    public PinnedItem(String judul, String deskripsi, String urlgambar) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.urlgambar = urlgambar;
    }


}