package hr.foi.air.database.entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.List;

import hr.foi.air.database.MainDatabase;

/**
 * Created by Ivan on 6.10.2016..
 */

@Table(database = MainDatabase.class)
public class Discount extends BaseModel{

    @PrimaryKey
    @Column int id;
    @Column String name;
    @Column String description;
    @Column int storeId;
    @Column Date startDate; // java.util.date
    @Column Date endDate;
    @Column int discount;

    @Column
    @ForeignKey(tableClass = Store.class)
    Store store;

    public Discount(){}

    public Discount(int id, String name, String description, int storeId, Date startDate, Date endDate, int discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.storeId = storeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
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

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setStore(Store store){
        this.store = store;
    }

    public static List<Discount> getAll(){
        return SQLite.select().from(Discount.class).queryList();
    }

    public static Discount getDiscountById(int id){
        return SQLite.select().from(Discount.class).where(Discount_Table.id.eq(id)).querySingle();
    }
}
