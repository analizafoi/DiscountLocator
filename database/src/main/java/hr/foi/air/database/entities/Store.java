package hr.foi.air.database.entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import hr.foi.air.database.MainDatabase;

/**
 * Created by Ivan on 6.10.2016..
 */

@Table(database = MainDatabase.class)
public class Store extends BaseModel{

    @PrimaryKey
    @Column int id;
    @Column String name;
    @Column String description;
    @Column String imgUrl;
    @Column long longitude;
    @Column long latitude;

    public Store() {
    }

    public Store(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.description = store.getDescription();
        this.imgUrl = store.getImgUrl();
        this.longitude = store.getLongitude();
        this.latitude = store.getLatitude();
        this.discountList = store.getDiscountList();
    }

    public Store(int id, String name, String description, String imgUrl, long longitude, long latitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    List<Discount> discountList;

    public List<Discount> getDiscountList(){
        if(discountList == null || discountList.isEmpty()){
            discountList = new Select().from(Discount.class)
                    .where(Discount_Table.storeId.eq(id))
                    .queryList();
        }
        return discountList;
    }

    public static List<Store> getAll(){
        return SQLite.select().from(Store.class).queryList();
    }

    public static Store getStoreById(int id){
        Store store = new Select().from(Store.class).where(Store_Table.id.eq(id)).querySingle();
        return store;
    }

}
