/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ticketing.ninja;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Track the current state of the seats in memory.
 * An additional iteration could add some form of persistence, disk, DB, etc.
 * Various maps track the seats by unique ID.
 * The ticket holds the state of each seat based on the ticketed information.
 * @author Jason
 */
public class Stadium {
    public static final int MAX_SEATS = 350;
    /**
     * Expire holds every 30 seconds
     * 
     */
    public static final long SEAT_EXPIRE_SECONDS = 30;
    /**
     * TODO move venue to DB value
     */
    private static final String VENUE = "Amazing Band";
    /**
     * TODO move price to DB value
     */
    private static final double PRICE = 55.00;
    private static int HOLD_COUNTER = 0;
    public static Map<Integer,Ticket> SEAT_MAP = new LinkedHashMap();
    public static Map<Integer,SeatHold> HELD_MAP = new LinkedHashMap();
    public static List<String> END_POINTS = new ArrayList();
     
    /**
     * Check to determine if a seat is open.
     * @param ticket
     * @return 
     */
    public static boolean seatIsOpen(Ticket ticket){
        if(null==ticket){
            //TODO add additional logging etc here as needed.
            return false;
        }else return !ticket.isHold()&&!ticket.isReserved();
    }
    
    /**
     * Check the current number of open seats.
     * @return 
     */
    public static int currentOpenSeatNumber(){
        expireHolds();
         Map<Integer, Ticket> openSeats = SEAT_MAP.entrySet().stream()
	.filter(map -> seatIsOpen(map.getValue())==true)
	.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));  
        return openSeats.size();
    }
    /**
     * 
     * @param count
     * @param email
     * @return 
     */
    public static SeatHold holdBestOpenSeats(int count, String email) {
    //Expire SeatHolds prior to holding best seats
    expireHolds();
    SeatHold hold = new SeatHold();
    Map<Integer, Ticket> openSeats = SEAT_MAP.entrySet().stream()
	.filter(map -> seatIsOpen(map.getValue())==true)
	.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));  
    if(null==openSeats||openSeats.isEmpty()){
        hold.setReservationStatus("The seat reservation failed. Please try again at a later time.");
        return hold;
    } else if(count > Stadium.currentOpenSeatNumber()){
         hold.setReservationStatus("The requested number of seats could not be reserved. Currently there are " 
                 + Stadium.currentOpenSeatNumber() + " open seats.");
        return hold;
    }
  
    Date holdDate = Calendar.getInstance().getTime();
       for(int i=0; i<count;i++){
           Ticket currTicket = openSeats.get(i);
           currTicket.setEmail(email);
           currTicket.setHold(true);
           currTicket.setVenue(VENUE);
           currTicket.setPrice(PRICE);
           currTicket.setHoldDateTime(holdDate);
          
          hold.getCurrentTickets().add(currTicket);
       }
       hold.setReservationStatus("The requested seats were reserved successfully.");
       hold.setTotalReserved(count);
       HELD_MAP.put(HOLD_COUNTER++, hold);
     return hold;
    }
    
    
    /**
     * Use the email since no PK is set for the seatHoldId due to 
     * no configured DB yet.
     * @param email
     * @return 
     */
    public static String reserveHeldSeats(int seatHoldId, String email) {
    Map<Integer, SeatHold> seatsToReserve = HELD_MAP.entrySet().stream()
	.filter(map -> map.getValue().getCurrentTickets().get(0).getEmail().equalsIgnoreCase(email))
	.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));  
    if(null==seatsToReserve||seatsToReserve.isEmpty()){
        return "The seat reserve failed. Please try again at a later time.";
    }
        String confirmationCode = UUID.randomUUID().toString();
       for(Integer id : seatsToReserve.keySet()){
           SeatHold seatHold = seatsToReserve.get(id);
           for(Ticket t : seatHold.getCurrentTickets()){
           t.setHold(false);
           t.setReserved(true);
           t.setHoldDateTime(null);
           t.setConfirmationCode(confirmationCode);
           SEAT_MAP.put(t.getSeatLocation(), t);
           }
            HELD_MAP.remove(seatHold.getSeatHoldId());
       }
      
     return confirmationCode;
    }
    
    private static void expireHolds(){
        Date current = Calendar.getInstance().getTime();
        List<Integer> seatHoldIdsToRemove = new ArrayList();
        for(Integer id : HELD_MAP.keySet()){
            SeatHold seatHold = HELD_MAP.get(id);
            for(Ticket t : seatHold.getCurrentTickets()){
                Date holdDate = t.getHoldDateTime();
                if(holdDate.before(current)){
                    long elapsedSeconds = (current.getTime() - holdDate.getTime())/1000;
                     //TODO change to debug
                    System.out.println("Elapsed time " + elapsedSeconds);
                    if(elapsedSeconds > SEAT_EXPIRE_SECONDS){
                        //Add the ticket back to the main pool
                        t.setHold(false);
                        t.setHoldDateTime(null);
                        SEAT_MAP.put(t.getSeatLocation(), t);
                        if(!seatHoldIdsToRemove.contains(seatHold.getSeatHoldId())){
                            seatHoldIdsToRemove.add(seatHold.getSeatHoldId());
                        }
                    }
                }
            }
        }
        //Remove the SeatHold from the hold pool.
        for(Integer id : seatHoldIdsToRemove){
            HELD_MAP.remove(id);
            //TODO change to debug
             System.out.println("Removed SeatHold Id " + id);
        }
    }
}
