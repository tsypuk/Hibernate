package model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by rtsy on 12.12.2015.
 */
@Entity
@Table (name = "stockprice", schema = "study_innodb", catalog = "")
public class CE_StockpriceEntity {
    private Integer _close;
    private int _stockId;

    @Basic
    @Column (name = "close")
    public Integer getClose() {
        return _close;
    }

    public void setClose(Integer close) {
        _close = close;
    }

    @Id
    @Column (name = "stock_id")
    public int getStockId() {
        return _stockId;
    }

    public void setStockId(int stockId) {
        _stockId = stockId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CE_StockpriceEntity that = (CE_StockpriceEntity) o;

        if (_stockId != that._stockId) {
            return false;
        }
        if (_close != null ? !_close.equals(that._close) : that._close != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = _close != null ? _close.hashCode() : 0;
        result = 31 * result + _stockId;
        return result;
    }

    @Override
    public String toString() {
        return "CE_StockpriceEntity{" +
                "_close=" + _close +
                ", _stockId=" + _stockId +
                '}';
    }
}
