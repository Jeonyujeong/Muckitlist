package foodlist.muckitlist;

/**
 * Created by yujeong on 16/12/17.
 */

class FoodItem {

    private String name;
    private String address;
    private Double mapx;
    private Double mapy;

    FoodItem(String name, String address, Double mapx, Double mapy) {
        this.name = name;
        this.address = address;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    String getName() { return name; }

    void setName(String name) { this.name = name; }

    String getAddress() { return address; }

    void setAddress(String address) {this.address = address; }

    Double getMapx() { return mapx; }

    Double getMapy() { return mapy; }
}
