package customer.Karma.models;

import java.util.Objects;

/**
 * @author zhangbin
 * @date 2021-5-27 17:10
 */

public class ZCostManagementMethod {

    private String BUKRS;
    private String Segment_ID;
    private String Segment;

    public String getBUKRS() {
        return BUKRS;
    }

    public void setBUKRS(String BUKRS) {
        this.BUKRS = BUKRS;
    }

    public String getSegment_ID() {
        return Segment_ID;
    }

    public void setSegment_ID(String segment_ID) {
        Segment_ID = segment_ID;
    }

    public String getSegment() {
        return Segment;
    }

    public void setSegment(String segment) {
        Segment = segment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZCostManagementMethod)) return false;
        ZCostManagementMethod that = (ZCostManagementMethod) o;
        return Objects.equals(getBUKRS(), that.getBUKRS()) &&
                Objects.equals(getSegment_ID(), that.getSegment_ID()) &&
                Objects.equals(getSegment(), that.getSegment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBUKRS(), getSegment_ID(), getSegment());
    }
}
