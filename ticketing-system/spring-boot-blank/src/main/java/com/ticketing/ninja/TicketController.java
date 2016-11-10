package com.ticketing.ninja;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController implements TicketService {
 
    @RequestMapping("/")
    List<String> applicationRunningCheck() {
        return Stadium.END_POINTS;
    }
    
     
    @Override
    @RequestMapping(value = "/findOpenSeatCount", produces = "application/json")
    public @ResponseBody int numSeatsAvailable() {
        return Stadium.currentOpenSeatNumber();
    }
    
    
    @RequestMapping(value = "/showSeatStatus/{id}", produces = "application/json")
    public @ResponseBody Ticket showSeatStatus(@PathVariable("id") Integer id) {
        return Stadium.SEAT_MAP.get(id);
    }
    
    @RequestMapping(value = "/holdBestSeats/{numberOfSeats}/{email}", produces = "application/json")
    public @ResponseBody SeatHold holdBestSeats(@PathVariable("numberOfSeats") Integer numberOfSeats, @PathVariable("email") String email) {
        return findAndHoldSeats(numberOfSeats,email);
    }
    
    @RequestMapping(value = "/reserveHeldSeats/{holdId}/{email}", produces = "application/json")
    public @ResponseBody String reserveHeldSeats(@PathVariable("holdId") Integer holdId, @PathVariable("email") String email) {
        return reserveSeats(holdId, email);
    }
    /**
     * Hold the best seats based on seat location
     * Assumes front row in the first 5 rows are the best
     * @param numSeats
     * @param customerEmail
     * @return 
     */
    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
       return Stadium.holdBestOpenSeats(numSeats, customerEmail);
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        return Stadium.reserveHeldSeats(seatHoldId, customerEmail);
    }
    
    

   
}
