package foodlist.muckitlist;

import java.util.Date;

/**
 * Created by daeyo on 2017-12-08.
 *
 * Memo 데이터베이스
 */

public class Memo {
    private String txt;
    private Date createDate, updateDate;

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getTxt() {

        return txt;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }
}
