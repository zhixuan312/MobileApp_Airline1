package zhang.zhixuan.mobileapp_airline;

import java.io.Serializable;

/**
 * Created by ruicai on 29/10/15.
 */
public class FlightEntity implements Serializable {
    private long id;
    private String flightNo;
    private String departureDate;
    private String arrivalDate;
    private String price;
    private String bookingClassName;
    private String origin;
    private String destination;
    private String oriAirportName;
    private String desAirportName;
    private String oriAirportCode;
    private String desAirportCode;
    private String aircraftTailN;
    private String depDayWE;
    private String depTimeE;
    private String ariDayWE;
    private String ariTimeE;
    private String timeDuration;
    private String timeDminutes;
    private double priceD;

    public Boolean expand = false;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
    public String getDepartureDate() {
        return departureDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }
    public String getBookingClassName() {
        return bookingClassName;
    }

    public void setBookingClassName(String bookingClassName) {
        this.bookingClassName = bookingClassName;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriAirportName() {
        return oriAirportName;
    }

    public void setOriAirportName(String oriAirportName) {
        this.oriAirportName = oriAirportName;
    }



    public String getOriAirportCode() {
        return oriAirportCode;
    }

    public void setOriAirportCode(String oriAirportCode) {
        this.oriAirportCode = oriAirportCode;
    }


    public String getAircraftTailN() {
        return aircraftTailN;
    }

    public void setAircraftTailN(String aircraftTailN) {
        this.aircraftTailN = aircraftTailN;
    }

    public String getDesAirportName() {
        return desAirportName;
    }

    public void setDesAirportName(String desAirportName) {
        this.desAirportName = desAirportName;
    }

    public String getDesAirportCode() {
        return desAirportCode;
    }

    public void setDesAirportCode(String desAirportCode) {
        this.desAirportCode = desAirportCode;
    }

    public String getDepDayWE() {
        return depDayWE;
    }

    public void setDepDayWE(String depDayWE) {
        this.depDayWE = depDayWE;
    }

    public String getDepTimeE() {
        return depTimeE;
    }

    public void setDepTimeE(String depTimeE) {
        this.depTimeE = depTimeE;
    }

    public String getAriDayWE() {
        return ariDayWE;
    }

    public void setAriDayWE(String ariDayE) {
        this.ariDayWE = ariDayE;
    }

    public String getAriTimeE() {
        return ariTimeE;
    }

    public void setAriTimeE(String ariTimeE) {
        this.ariTimeE = ariTimeE;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPriceD() {
        return priceD;
    }

    public void setPriceD(double priceD) {
        this.priceD = priceD;
    }

    public String getTimeDminutes() {
        return timeDminutes;
    }

    public void setTimeDminutes(String timeDminutes) {
        this.timeDminutes = timeDminutes;
    }
}
