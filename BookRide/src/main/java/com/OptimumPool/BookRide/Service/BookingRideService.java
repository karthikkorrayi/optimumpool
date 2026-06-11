package com.OptimumPool.BookRide.Service;

import com.OptimumPool.BookRide.Configuration.BookingDTO;
import com.OptimumPool.BookRide.Model.*;
import com.OptimumPool.BookRide.Repository.BookRideRepository;
import com.OptimumPool.BookRide.Repository.BookingRepo;
import com.OptimumPool.BookRide.Repository.InvoiceRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BookingRideService implements IBookRideService{

    @Autowired
    private BookRideRepository repo;

    @Autowired
    private BookingRepo repo2;

    @Autowired
    private InvoiceRepository repo3;


    public List<Offerride> getAllRides(){
        List<Offerride> l1 = repo.findAll();
        return l1;
    }

    public Offerride getRide(int id){
        Offerride ride = repo.findById(id).get();
        return ride;
    }

    public List<Offerride> getRide(String from ,String to) {
        boolean b1 = false;
        boolean b2 = false;
        List<Offerride> output = new ArrayList<>();
        List<Offerride> l1 = repo.findAll();
        for (Offerride ride : l1) {

            List<String> getpoints = ride.getWayPoint();
            for (String city : getpoints) {
                if (city.contains(from)) b1 = true;
                if(city.contains(to)) b2 = true;

                }
            if(b1 && b2) output.add(ride);
            b1=false;
            b2=false;
            }

        return output;
    }

    public Bookings bookRide(int id, String customerName, int no_seat_want,String from, String to){

        Random r1 = new Random();
        int booking_id = r1.nextInt(1000);
        Offerride ride = repo.findById(id).get();
        int index1 = ride.getWayPoint().indexOf(from);
        int index2 = ride.getWayPoint().indexOf(to);
        if(ride.getCar_info().getAvl_seats()-no_seat_want>=0) {
            Bookings booking = new Bookings(booking_id, ride, customerName, no_seat_want, ride.getDistance().get(index2) - ride.getDistance().get(index1),from,to);
            repo2.save(booking);
            ride.getCar_info().setAvl_seats(ride.getCar_info().getAvl_seats() - no_seat_want);
            repo.save(ride);
            return booking;
        }
        else return null;
    }

    public List<ImportantDetails> getCarInformation(String from, String to){
        List<Offerride>list1 = getRide(from,to);
        List<ImportantDetails> list2 = new ArrayList<ImportantDetails>();
        for(Offerride ride2:list1){
            String carNum1 = ride2.getCar_info().getCarNum();
            int avl_seats1 = ride2.getCar_info().getAvl_seats();
            int index1 = ride2.getWayPoint().indexOf(from);
            int index2 = ride2.getWayPoint().indexOf(to);
            int distance1 = ride2.getDistance().get(index2)-ride2.getDistance().get(index1);
            int charge1 = distance1*ride2.getCharge_per_km();
            ImportantDetails temp = new ImportantDetails(carNum1,avl_seats1,distance1,charge1);
            list2.add(temp);
        }
        return list2;

    }

    public Bookings getBooking(String customerName){
        Bookings bookingDetail = repo2.findBycustomerName(customerName);
        return bookingDetail;
    }

    public Invoice getInvoice(int id){
        Bookings booking = repo2.findById(id).get();
        Random r1 = new Random();
        int Invoice_Id = r1.nextInt(1000);
//        int bill_generated=200;
        int bill_generated = booking.getDistance()*booking.getNo_seat_want()*booking.getOfferObject().getCharge_per_km();
        Invoice invoice = new Invoice(Invoice_Id,booking,bill_generated);
        repo3.save(invoice);
        return invoice;
    }
    public String settleRide(int Invoice_Id,int bill_amount){
        Invoice invoice = repo3.findById(Invoice_Id).get();
        int booking_id = invoice.getBooking_obj().getId();
        if(invoice.getBill_generated()==bill_amount) {
            repo3.deleteById(Invoice_Id);
            repo2.deleteById(booking_id);
            return "Bill amount is paid successfully, your ride has been completed";
        }
        else
        {
            return "Payment failed, Please give the bill amount as mentioned in invoice";
        }
    }

    //sending data to rabbitmq

    List<Bookings> bookList;
    @Override
    public List<Bookings> getList() {
        bookList = repo2.findAll();
        return bookList;
    }



    /*Rabbitmq is sending data*/
    @Autowired(required = false)
    private RabbitTemplate rt;

    @Autowired(required = false)
    private DirectExchange exchange;

    public  void sendDataToConsumer(){
        JSONObject data = new JSONObject();
        data.put("booklist" , bookList);
        BookingDTO bdto = new BookingDTO();
        bdto.setJsonObject(data);

        System.out.println("data sended to rabbit mq");
        rt.convertAndSend(exchange.getName(),"route_key",bdto);

    }


}
