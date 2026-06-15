package com.OptimumPool.OfferRide.Controller;

import com.OptimumPool.OfferRide.Exception.OfferRideNotFound;
import com.OptimumPool.OfferRide.Model.Offerride;
import com.OptimumPool.OfferRide.Services.OfferService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/offerride")
public class OfferRideController {

    @Autowired
    private OfferService oService;

    // Car owner creates a new ride offer
    @PostMapping
    public ResponseEntity<?> addOffer(@RequestBody Offerride offer, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        offer.getCar_owner().setUsername(username);
        return new ResponseEntity<>(oService.addOffer(offer), HttpStatus.CREATED);
    }

    // Car owner updates their ride
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable String id,
                                         @RequestBody Offerride updates) throws OfferRideNotFound {
        try {
            return new ResponseEntity<>(oService.updateRide(updates, id), HttpStatus.OK);
        } catch (OfferRideNotFound e) {
            return new ResponseEntity<>("Ride not found", HttpStatus.NOT_FOUND);
        }
    }

    // Get all active rides (used by BookRide to sync via REST fallback)
    @GetMapping
    public ResponseEntity<?> getAllRides() {
        oService.sendOffersToBookRide();
        return new ResponseEntity<>(oService.getRide(), HttpStatus.OK);
    }

    // Car owner sees who booked their rides
    @GetMapping("/bookings")
    public ResponseEntity<?> getMyBookings() {
        return new ResponseEntity<>(oService.getAllBooking(), HttpStatus.OK);
    }

    // Car owner's own rides only
    @GetMapping("/mine")
    public ResponseEntity<?> getMyRides(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return new ResponseEntity<>(oService.getRidesByOwner(username), HttpStatus.OK);
    }

    @GetMapping("/health")
    public String health() { return "OfferRide UP"; }
}