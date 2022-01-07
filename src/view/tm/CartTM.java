package view.tm;

public class CartTM {
    private  String MId;
    private Double charge;
    private  Integer qty;
    private Double total;

    public CartTM() {
    }

    public CartTM(String MId, Double charge, Integer qty, Double total) {
        this.setMId(MId);
        this.setCharge(charge);
        this.setQty(qty);
        this.setTotal(total);
    }


    public String getMId() {
        return MId;
    }

    public void setMId(String MId) {
        this.MId = MId;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                ", MId='" + MId + '\'' +
                ", charge=" + charge +
                ", qty=" + qty +
                ", total=" + total +
                '}';
    }
}
