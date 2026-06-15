package com.OptimumPool.OfferRide.Services;

import com.OptimumPool.OfferRide.Configuration.BookingDTO;
import com.OptimumPool.OfferRide.Configuration.OfferDTO;
import com.OptimumPool.OfferRide.Configuration.RabbitConfig;
import com.OptimumPool.OfferRide.Exception.OfferRideNotFound;
import com.OptimumPool.OfferRide.Model.Bookings;
import com.OptimumPool.OfferRide.Model.CustDetails;
import com.OptimumPool.OfferRide.Model.Offerride;
import com.OptimumPool.OfferRide.Repository.IBookingRepo;
import com.OptimumPool.OfferRide.Repository.OfferRideRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfferService implements IOfferService {

    @Autowired
    private OfferRideRepo orepo;

    @Autowired
    private IBookingRepo brepo;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Offerride addOffer(Offerride offer) {
        // UUID is assigned in Offerride() constructor — no Random.nextInt()
        return orepo.save(offer);
    }

    @Override
    public Offerride updateRide(Offerride updates, String id) throws OfferRideNotFound {
        Offerride existing = orepo.findById(id)
                .orElseThrow(OfferRideNotFound::new);
        existing.setCar_info(updates.getCar_info());
        existing.setDate(updates.getDate());
        existing.setDistance(updates.getDistance());
        existing.setTime(updates.getTime());
        existing.setWayPoint(updates.getWayPoint());
        existing.setCharge_per_km(updates.getCharge_per_km());
        return orepo.save(existing);
    }

    @Override
    public List<Offerride> getRide() {
        return orepo.findAll();
    }

    @Override
    public List<CustDetails> getAllBooking() {
        List<Bookings> bookings = brepo.findAll();
        List<CustDetails> result = new ArrayList<>();
        for (Bookings b : bookings) {
            result.add(new CustDetails(
                    b.getBooking_id(),
                    b.getCustomerName(),
                    0L,                                    // phone not stored in booking — see note below
                    b.getNo_seat_want(),
                    b.getSource(),
                    b.getDestination(),
                    b.getOfferObject() != null ? b.getOfferObject().getDate() : "N/A",
                    b.getBookingStatus()
            ));
        }
        return result;
    }

    // FIXED: BookingDTO now carries a typed List<Bookings>, not a JSONObject
    // Add the @Override annotation here
    @Override
    @RabbitListener(queues = RabbitConfig.BOOKING_QUEUE)
    public void receiveBookingFromRabbit(BookingDTO dto) {
        if (dto.getBookList() != null) {
            for (Bookings b : dto.getBookList()) {
                brepo.save(b);
            }
        }
    }

    // Called when car owner GETs /offerride — pushes current offers to BookRide
    public void sendOffersToBookRide() {
        OfferDTO dto = new OfferDTO(orepo.findAll());
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.OFFER_KEY, dto);
    }

    public List<Offerride> getRidesByOwner(String username) {
        return orepo.findByCarOwnerUsername(username);
    }}