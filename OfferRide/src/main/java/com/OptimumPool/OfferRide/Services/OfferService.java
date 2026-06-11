package com.OptimumPool.OfferRide.Services;

import com.OptimumPool.OfferRide.Configuration.BookingDTO;
import com.OptimumPool.OfferRide.Configuration.OfferDTO;
import com.OptimumPool.OfferRide.Exception.OfferRideNotFound;
import com.OptimumPool.OfferRide.Model.Bookings;
import com.OptimumPool.OfferRide.Model.CustDetails;
import com.OptimumPool.OfferRide.Model.Offerride;
import com.OptimumPool.OfferRide.Repository.IBookingRepo;
import com.OptimumPool.OfferRide.Repository.OfferRideRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service

public class OfferService implements IOfferService {

    @Autowired
    OfferRideRepo orepo;

    @Autowired
    IBookingRepo brepo;


    List<CustDetails> custDetailsList = new ArrayList<>();


    List<Bookings> bookingList;
    List<Offerride> offerrideList ;


    private String name ;
    private int id;


    //method for adding offerride to database
    @Override
    public Offerride addOffer(Offerride of) {
        Random r1 = new Random();
        int id1 = r1.nextInt(1000);
        int own_id = r1.nextInt(2000);
        of.getCar_owner().setOwnerId(own_id);
        of.setOffer_id(id1);
        return orepo.save(of);
    }



    //method for updating offerride

    @Override
    public Offerride updateRide(Offerride or , int id) throws OfferRideNotFound {
        Offerride newRide;
        if (orepo.findById(id).isEmpty()) {
            throw new OfferRideNotFound();
        }
        else{
            newRide = orepo.findById(id).get();
            newRide.setCar_info(or.getCar_info());
            newRide.setDate(or.getDate());
            newRide.setDistance(or.getDistance());
            newRide.setTime(or.getTime());
            newRide.setWayPoint(or.getWayPoint());
            newRide.setCharge_per_km(or.getCharge_per_km());

            orepo.save(newRide);

        }
        return newRide;


    }



    //method for showing all the details of custmer who have booked ride
    public List<Offerride> getRide(){
        offerrideList = orepo.findAll();

        return offerrideList;

    }

    public List<CustDetails> getALlBooking(){

        List<Bookings> bookings =brepo.findAll();
        for(Bookings info : bookings){
            CustDetails custmer = new CustDetails();
            custmer.setCust_name(info.getCustomerName());
            custmer.setNo_of_seat_want(info.getNo_seat_want());
            custmer.setSource(info.getSource());
            custmer.setDestination(info.getDestination());
            custmer.setDate_of_journey(info.getOfferObject().getDate());
            custDetailsList.add(custmer);
        }

        return custDetailsList;
    }


    /*how to listen from rabit */

    @RabbitListener(queues = "booking_queue")
    public void recieveDataFromRabbit(BookingDTO bto) throws IOException {
        Object jsonobj = bto.getJsonObject().get("booklist");
        ObjectMapper mapper = new ObjectMapper();
        String jsonstr = mapper.writeValueAsString(jsonobj);

        System.out.println(jsonstr);
        bookingList=mapper.readValue(jsonstr, new TypeReference<List<Bookings>>(){});
        for (Bookings obj : bookingList)
            brepo.save(obj);

        System.out.println(bookingList.get(0).getCustomerName());
    }



    //offer ride data is sending to rabbit mq

     @Autowired(required = false)
    private RabbitTemplate rt;

    @Autowired(required = false)
    private DirectExchange exchange;

    public void sendDataToConsumer(){
        JSONObject data =new JSONObject();

        data.put("list" , offerrideList);
        OfferDTO odto = new OfferDTO();
        odto.setOfferList(getRide());
//        odto.setJsonObject(data);
        System.out.println("data sended to rabbit mq");
        rt.convertAndSend(exchange.getName(),"mq_route",odto);
    }


}
