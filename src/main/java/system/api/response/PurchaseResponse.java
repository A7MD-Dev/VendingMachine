package system.api.response;

import system.model.Purchase;
import java.util.Map;

public class PurchaseResponse {
    private Purchase purchase;
    private Map<Integer, Integer> change;

    public PurchaseResponse(Purchase purchase, Map<Integer, Integer> change) {
        this.purchase = purchase;
        this.change = change;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Map<Integer, Integer> getChange() {
        return change;
    }

    public void setChange(Map<Integer, Integer> change) {
        this.change = change;
    }
}
