package zhang.zhixuan.mobileapp_airline;

/**
 * Created by ruicai on 6/11/15.
 */
public class TicketCheckInEntity {
    private Long id;
    private String originCity;
    private String destinationCity;
    private String originAN;
    private String destinationAN;
    private String flightNo;
    private Long flightId;
    private String depD;
    private String ariD;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getOriginAN() {
        return originAN;
    }

    public void setOriginAN(String originAN) {
        this.originAN = originAN;
    }

    public String getDestinationAN() {
        return destinationAN;
    }

    public void setDestinationAN(String destinationAN) {
        this.destinationAN = destinationAN;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getDepD() {
        return depD;
    }

    public void setDepD(String depD) {
        this.depD = depD;
    }

    public String getAriD() {
        return ariD;
    }

    public void setAriD(String ariD) {
        this.ariD = ariD;
    }
}
