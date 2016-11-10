/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticketing.ninja;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Jason Slaughter
 * @email zorrion2000@yahoo.com
 */
public class Ticket {
    String confirmationCode = UUID.randomUUID().toString();

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
    String email;
    String venue;
    double price;
    boolean reserved;
    boolean hold;
    boolean paid;
    Date holdDateTime;

    public Date getHoldDateTime() {
        return holdDateTime;
    }

    public void setHoldDateTime(Date holdDateTime) {
        this.holdDateTime = holdDateTime;
    }
    /**
     * 10 seats left stage, 10 seats right stage, 330 seats front stage
     * Seats 1 through 10 = stage left
     * Seats 11 through 21 = stage right
     * Seats 22 through 352 = front stage
     */
    int seatLocation;

    public int getSeatLocation() {
        return seatLocation;
    }

    public void setSeatLocation(int seatLocation) {
        this.seatLocation = seatLocation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
   
    
}
