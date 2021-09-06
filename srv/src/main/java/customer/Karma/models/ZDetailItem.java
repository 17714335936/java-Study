package customer.Karma.models;

/**
 * @author zhangbin
 * @date 2021-5-27 21:26
 */
public class ZDetailItem {

    private String BUKRS;
    private String DetailItem_ID;
    private String DetailItem;
    private String AdminCode;
    private String AccountCode;

    public String getBUKRS() {
        return BUKRS;
    }

    public void setBUKRS(String BUKRS) {
        this.BUKRS = BUKRS;
    }

    public String getDetailItem_ID() {
        return DetailItem_ID;
    }

    public void setDetailItem_ID(String detailItem_ID) {
        DetailItem_ID = detailItem_ID;
    }

    public String getDetailItem() {
        return DetailItem;
    }

    public void setDetailItem(String detailItem) {
        DetailItem = detailItem;
    }

    public String getAdminCode() {
        return AdminCode;
    }

    public void setAdminCode(String adminCode) {
        AdminCode = adminCode;
    }

    public String getAccountCode() {
        return AccountCode;
    }

    public void setAccountCode(String accountCode) {
        AccountCode = accountCode;
    }
}
