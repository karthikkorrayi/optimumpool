package com.OptimumPool.BookRide.Controller;

import com.OptimumPool.BookRide.Model.*;
import com.OptimumPool.BookRide.Service.BookingRideService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class Controller {

    @Autowired
    private BookingRideService service;

    @GetMapping("/bookrides")
    public ResponseEntity<?> getAllRides() {
        return new ResponseEntity<>(service.getAllRides(), HttpStatus.OK);
    }

    @GetMapping("/bookrides/{id}")
    public ResponseEntity<?> getRide(@PathVariable String id) {
        return new ResponseEntity<>(service.getRideById(id), HttpStatus.OK);
    }

    @GetMapping("/bookrides/filter/{from}/{to}")
    public ResponseEntity<?> filterRides(@PathVariable String from, @PathVariable String to) {
        return new ResponseEntity<>(service.filterRides(from, to), HttpStatus.OK);
    }

    @GetMapping("/rides/details/{from}/{to}")
    public ResponseEntity<?> getCarDetails(@PathVariable String from, @PathVariable String to) {
        return new ResponseEntity<>(service.getCarDetails(from, to), HttpStatus.OK);
    }

    // Book a ride — all params in request body, not path variables
    @PostMapping("/bookrides/{id}")
    public ResponseEntity<?> bookRide(@PathVariable String id,
                                      @RequestBody Map<String, Object> body,
                                      HttpServletRequest request) {
        String customerName = (String) request.getAttribute("username");
        int    seats        = (int)    body.get("seats");
        String from         = (String) body.get("from");
        String to           = (String) body.get("to");
        try {
            Bookings booking = service.bookRide(id, customerName, seats, from, to);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/booking/me")
    public ResponseEntity<?> getMyBookings(HttpServletRequest request) {
        String customerName = (String) request.getAttribute("username");
        return new ResponseEntity<>(service.getAllBookingsByCustomer(customerName), HttpStatus.OK);
    }

    // ─── Owner Accept / Reject ───────────────────────────────────

    @GetMapping("/booking/owner/all")
    public ResponseEntity<?> getAllBookingsForOwner() {
        return new ResponseEntity<>(service.getAllBookings(), HttpStatus.OK);
    }

    @GetMapping("/booking/owner/pending")
    public ResponseEntity<?> getPendingBookings() {
        return new ResponseEntity<>(service.getBookingsByStatus("PENDING"), HttpStatus.OK);
    }

    @PutMapping("/booking/accept/{bookingId}")
    public ResponseEntity<?> acceptBooking(@PathVariable String bookingId) {
        try {
            return new ResponseEntity<>(service.acceptBooking(bookingId), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/booking/reject/{bookingId}")
    public ResponseEntity<?> rejectBooking(@PathVariable String bookingId) {
        try {
            return new ResponseEntity<>(service.rejectBooking(bookingId), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ─── Invoice ─────────────────────────────────────────────────

    @PostMapping("/invoice/{bookingId}")
    public ResponseEntity<?> generateInvoice(@PathVariable String bookingId) {
        try {
            return new ResponseEntity<>(service.generateInvoice(bookingId), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/invoice/pay/{invoiceId}")
    public ResponseEntity<?> payInvoice(@PathVariable String invoiceId,
                                        @RequestBody Map<String, Integer> body) {
        String result = service.settleInvoice(invoiceId, body.get("amount"));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/health")
    public String health() { return "BookRide UP"; }
}