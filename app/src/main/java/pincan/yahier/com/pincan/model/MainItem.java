package pincan.yahier.com.pincan.model;

/**
 * Created by yahier on 2018/1/25.
 */

public class MainItem {
    String title;
    String des;

    public MainItem(String title, Class mact) {
        this.title = title;
    }

    public MainItem(String title, String des) {
        this.title = title;
        this.des = des;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

}

