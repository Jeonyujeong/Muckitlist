package foodlist.muckitlist;

/**
 * Created by yujeong on 16/12/17.
 */

class FoodItem {

    private String name;
    private String address;
    private Integer mapx;
    private Integer mapy;

    FoodItem(String name, String address, Integer mapx, Integer mapy) {
        this.name = name;
        this.address = address;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    String getName() { return name; }

    void setName(String name) { this.name = name; }

    String getAddress() { return address; }

    void setAddress(String address) {this.address = address; }

    Integer getMapx() { return mapx; }

    Integer getMapy() { return mapy; }
}
