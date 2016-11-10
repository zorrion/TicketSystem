/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticketing.ninja;

import java.util.ArrayList;
import java.util.List;


public class SeatHold {
  int seatHoldId;  

    public int getSeatHoldId() {
        return seatHoldId;
    }

    public void setSeatHoldId(int seatHoldId) {
        this.seatHoldId = seatHoldId;
    }
  List<Ticket> currentTickets = new ArrayList();  

    public List<Ticket> getCurrentTickets() {
        return currentTickets;
    }

    public void setCurrentTickets(List<Ticket> currentTickets) {
        this.currentTickets = currentTickets;
    }
  String reservationStatus;

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
  int totalReserved;

    public int getTotalReserved() {
        return totalReserved;
    }

    public void setTotalReserved(int totalReserved) {
        this.totalReserved = totalReserved;
    }
  
}
