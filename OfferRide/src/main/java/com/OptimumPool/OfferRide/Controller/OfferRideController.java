package com.OptimumPool.OfferRide.Controller;

import com.OptimumPool.OfferRide.Exception.OfferRideNotFound;
import com.OptimumPool.OfferRide.Model.CarOwner;
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

    @PostMapping
    public ResponseEntity<?> addOffer(@RequestBody Offerride offer, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        // FIXED: guard against null car_owner — create one if Jackson didn't deserialize it
        if (offer.getCar_owner() == null) {
            offer.setCar_owner(new CarOwner());
        }
        // Username always comes from JWT — not from the form
        offer.getCar_owner().setUsername(username);

        try {
            return new ResponseEntity<>(oService.addOffer(offer), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create ride: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable String id,
                                         @RequestBody Offerride updates) {
        try {
            return new ResponseEntity<>(oService.updateRide(updates, id), HttpStatus.OK);
        } catch (OfferRideNotFound e) {
            return new ResponseEntity<>("Ride not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRides() {
        try {
            oService.sendOffersToBookRide();
        } catch (Exception e) {
            // RabbitMQ might not be running — don't fail the whole request
            System.err.println("RabbitMQ sync failed (non-fatal): " + e.getMessage());
        }
        return new ResponseEntity<>(oService.getRide(), HttpStatus.OK);
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> getMyBookings() {
        return new ResponseEntity<>(oService.getAllBooking(), HttpStatus.OK);
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getMyRides(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return new ResponseEntity<>(oService.getRidesByOwner(username), HttpStatus.OK);
    }

    @GetMapping("/health")
    public String health() { return "OfferRide UP"; }
}