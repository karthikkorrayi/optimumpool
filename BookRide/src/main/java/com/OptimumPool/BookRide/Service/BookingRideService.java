package com.OptimumPool.BookRide.Service;

import com.OptimumPool.BookRide.Configuration.BookingDTO;
import com.OptimumPool.BookRide.Configuration.RabbitConfig;
import com.OptimumPool.BookRide.Model.*;
import com.OptimumPool.BookRide.Repository.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingRideService {

    private final BookRideRepository rideRepo;
    private final BookingRepo bookingRepo;
    private final InvoiceRepository invoiceRepo;
    private final RabbitTemplate rabbitTemplate;

    // RECOMMENDED: Constructor Injection over Field Injection
    public BookingRideService(BookRideRepository rideRepo,
                              BookingRepo bookingRepo,
                              InvoiceRepository invoiceRepo,
                              RabbitTemplate rabbitTemplate) {
        this.rideRepo = rideRepo;
        this.bookingRepo = bookingRepo;
        this.invoiceRepo = invoiceRepo;
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
        List<Offerride> all = rideRepo.findAll();
        List<Offerride> result = new ArrayList<>();
        for (Offerride ride : all) {
            List<String> points = ride.getWayPoint();
            if (points.contains(from) && points.contains(to) &&
                    points.indexOf(from) < points.indexOf(to)) {
                result.add(ride);
            }
        }
        return result;
    }

    public List<ImportantDetails> getCarDetails(String from, String to) {
        List<ImportantDetails> details = new ArrayList<>();
        for (Offerride ride : filterRides(from, to)) {
            int i1 = ride.getWayPoint().indexOf(from);
            int i2 = ride.getWayPoint().indexOf(to);
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

        int i1   = ride.getWayPoint().indexOf(from);
        int i2   = ride.getWayPoint().indexOf(to);
        int dist = ride.getDistance().get(i2) - ride.getDistance().get(i1);

        int uniqueId = Math.abs(UUID.randomUUID().hashCode());

        Bookings booking = new Bookings(
                uniqueId,
                ride, customerName, seats, dist, from, to
        );
        bookingRepo.save(booking);

        ride.getCar_info().setAvl_seats(ride.getCar_info().getAvl_seats() - seats);
        rideRepo.save(ride);

        pushBookingToOfferRide();

        return booking;
    }

    public Bookings getBookingByCustomer(String customerName) {
        return bookingRepo.findByCustomerName(customerName);
    }

    // ─── Invoice ─────────────────────────────────────────────────

    public com.OptimumPool.BookRide.Model.Invoice generateInvoice(String bookingId) {
        Bookings booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        int bill = booking.getDistance()
                * booking.getNo_seat_want()
                * booking.getOfferObject().getCharge_per_km();

        com.OptimumPool.BookRide.Model.Invoice invoice = new com.OptimumPool.BookRide.Model.Invoice(booking, bill);
        return invoiceRepo.save(invoice);
    }

    public String settleInvoice(String invoiceId, int paidAmount) {
        com.OptimumPool.BookRide.Model.Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.getBill_generated() != paidAmount) {
            return "Payment failed. Expected: ₹" + invoice.getBill_generated();
        }

        invoice.setStatus("PAID");
        invoiceRepo.save(invoice);

        // Clear entry by ID value safely matching repository definitions
        bookingRepo.deleteById(String.valueOf(invoice.getBooking_obj().getId()));
        invoiceRepo.deleteById(invoiceId);

        return "Payment successful. Ride completed.";
    }

    // ─── RabbitMQ ─────────────────────────────────────────────────

    @RabbitListener(queues = RabbitConfig.OFFER_QUEUE)
    public void receiveOffersFromOfferRide(
            com.OptimumPool.BookRide.Configuration.OfferDTO dto) {
        if (dto.getOfferList() != null) {
            rideRepo.saveAll(dto.getOfferList());
        }
    }

    public void pushBookingToOfferRide() {
        BookingDTO dto = new BookingDTO(bookingRepo.findAll());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.BOOKING_KEY, dto);
    }
}