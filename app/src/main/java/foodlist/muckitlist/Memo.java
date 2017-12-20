package foodlist.muckitlist;

/**
 * Created by daeyo on 2017-12-08.
 *
 * Memo 데이터베이스
 */


public class Memo {

    private String key;
    private String txt, title, address;
    private long createDate, updateDate;
    private int star;

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

    public void setStar(int star) {
        this.star = star;
    }

    public String getAddress() {
        return address;
    }

    public int getStar() {
        return star;
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

