package foodlist.muckitlist;

/**
 * Created by daeyo on 2017-12-08.
 *
 * Memo 데이터베이스
 */


public class Memo {

    private String key;
    private String txt, title, address;
    private int food_category;
    private long createDate, updateDate;
    private float rating;
    private String usid;
    private boolean pin;

    public void setPin(boolean pin) {
        this.pin = pin;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setFood_category(int food_category) {
        this.food_category = food_category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getTxt() {
        return txt;
    }

    public String getKey() {
        return key;
    }

    public long getCreateDate() {
        return createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public float getRating() {

        return rating;
    }

    public String getUsid() {
        return usid;
    }

    public boolean isPin() {
        return pin;
    }

    public int getFood_category() {
        return food_category;
    }
//캡슐화



    public String getTitle(){
        if (txt != null){
            if( txt.indexOf("\n") > -1){
                return txt.substring(0, txt.indexOf("\n"));
            } else {
                return txt;
            }
        }
        return title;
    }
}

