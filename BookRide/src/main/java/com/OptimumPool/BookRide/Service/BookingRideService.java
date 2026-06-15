package com.OptimumPool.BookRide.Service;

import com.OptimumPool.BookRide.Configuration.BookingDTO;
import com.OptimumPool.BookRide.Model.*;
import com.OptimumPool.BookRide.Repository.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingRideService {

    private final BookRideRepository rideRepo;
    private final BookingRepo bookingRepo;
    private final InvoiceRepository invoiceRepo;
    private final RabbitTemplate rabbitTemplate;

    public BookingRideService(BookRideRepository rideRepo,
                              BookingRepo bookingRepo,
                              InvoiceRepository invoiceRepo,
                              RabbitTemplate rabbitTemplate) {
        this.rideRepo     = rideRepo;
        this.bookingRepo  = bookingRepo;
        this.invoiceRepo  = invoiceRepo;
        this.rabbitTemplate = rabbitTemplate;
    }

    // ─── Ride Listing ────────────────────────────────────────────

    public List<Offerride> getAllRides() {
        return rideRepo.findAll();
    }

    public Offerride getRideById(String id) {
        return rideRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found: " + id));
    }

    public List<Offerride> filterRides(String from, String to) {
        List<Offerride> all    = rideRepo.findAll();
        List<Offerride> result = new ArrayList<>();
        for (Offerride ride : all) {
            List<String> points = ride.getWayPoint();
            if (points != null
                    && points.contains(from)
                    && points.contains(to)
                    && points.indexOf(from) < points.indexOf(to)) {
                result.add(ride);
            }
        }
        return result;
    }

    public List<ImportantDetails> getCarDetails(String from, String to) {
        List<ImportantDetails> details = new ArrayList<>();
        for (Offerride ride : filterRides(from, to)) {
            int i1     = ride.getWayPoint().indexOf(from);
            int i2     = ride.getWayPoint().indexOf(to);
            int dist   = ride.getDistance().get(i2) - ride.getDistance().get(i1);
            int charge = dist * ride.getCharge_per_km();
            details.add(new ImportantDetails(
                    ride.getCar_info().getCarNum(),
                    ride.getCar_info().getAvl_seats(),
                    dist, charge
            ));
        }
        return details;
    }

    // ─── Booking ──────────────────────────────────────────────────

    public Bookings bookRide(String rideId, String customerName, int seats, String from, String to) {
        Offerride ride = getRideById(rideId);

        if (ride.getCar_info().getAvl_seats() < seats) {
            throw new RuntimeException("Not enough seats available");
        }

        int fromIndex = ride.getWayPoint().indexOf(from);
        int toIndex   = ride.getWayPoint().indexOf(to);

        if (fromIndex == -1 || toIndex == -1 || fromIndex >= toIndex) {
            throw new RuntimeException("Invalid route: " + from + " → " + to);
        }

        int dist = ride.getDistance().get(toIndex) - ride.getDistance().get(fromIndex);

        Bookings booking = new Bookings(ride, customerName, seats, dist, from, to);
        bookingRepo.save(booking);

        pushBookingToOfferRide();

        return booking;
    }

    public Bookings getBookingByCustomer(String customerName) {
        return bookingRepo.findByCustomerName(customerName);
    }

    public List<Bookings> getAllBookingsByCustomer(String customerName) {
        return bookingRepo.findAllByCustomerName(customerName);
    }

    // ─── Owner Accept / Reject ───────────────────────────────────

    public Bookings acceptBooking(String bookingId) {
        Bookings booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!"PENDING".equals(booking.getBookingStatus())) {
            throw new RuntimeException("Booking is already " + booking.getBookingStatus());
        }

        Offerride ride = booking.getOfferObject();
        if (ride.getCar_info().getAvl_seats() < booking.getNo_seat_want()) {
            throw new RuntimeException("Not enough seats available anymore");
        }
        ride.getCar_info().setAvl_seats(
                ride.getCar_info().getAvl_seats() - booking.getNo_seat_want()
        );
        rideRepo.save(ride);

        booking.setBookingStatus("ACCEPTED");
        bookingRepo.save(booking);

        pushBookingToOfferRide();

        return booking;
    }

    public Bookings rejectBooking(String bookingId) {
        Bookings booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!"PENDING".equals(booking.getBookingStatus())) {
            throw new RuntimeException("Booking is already " + booking.getBookingStatus());
        }

        booking.setBookingStatus("REJECTED");
        bookingRepo.save(booking);
        pushBookingToOfferRide();

        return booking;
    }

    public List<Bookings> getAllBookings() {
        return bookingRepo.findAll();
    }

    public List<Bookings> getBookingsByStatus(String status) {
        return bookingRepo.findByBookingStatus(status);
    }

    // ─── Invoice ─────────────────────────────────────────────────

    public Invoice generateInvoice(String bookingId) {
        Bookings booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        if (!"ACCEPTED".equals(booking.getBookingStatus())) {
            throw new RuntimeException(
                    "Cannot generate invoice — booking status is: " + booking.getBookingStatus()
                            + ". Owner must accept first."
            );
        }

        int bill = booking.getDistance()
                * booking.getNo_seat_want()
                * booking.getOfferObject().getCharge_per_km();

        Invoice invoice = new Invoice(booking, bill);
        return invoiceRepo.save(invoice);
    }

    public String settleInvoice(String invoiceId, int paidAmount) {
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.getBill_generated() != paidAmount) {
            return "Payment failed. Expected: ₹" + invoice.getBill_generated();
        }

        invoice.setStatus("PAID");
        invoiceRepo.save(invoice);

        bookingRepo.deleteById(invoice.getBooking_obj().getBooking_id());
        invoiceRepo.deleteById(invoiceId);

        return "Payment successful. Ride completed.";
    }

    // ─── RabbitMQ ─────────────────────────────────────────────────

    @RabbitListener(queues = "offer_queue")
    public void receiveOffersFromOfferRide(com.OptimumPool.BookRide.Configuration.OfferDTO dto) {
        if (dto.getOfferList() != null) {
            rideRepo.saveAll(dto.getOfferList());
        }
    }

    public void pushBookingToOfferRide() {
        BookingDTO dto = new BookingDTO(bookingRepo.findAll());
        rabbitTemplate.convertAndSend("direct-exchange", "booking_key", dto);
    }
}