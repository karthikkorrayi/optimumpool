package com.OptimumPool.BookRide.Controller;

import com.OptimumPool.BookRide.Model.*;
import com.OptimumPool.BookRide.Service.BookingRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class Controller {

    @Autowired
    private BookingRideService service;

    @GetMapping("bookrides")
    public ResponseEntity<?> getRides(){
        List<Offerride> demoList = service.getAllRides();
        return new ResponseEntity<>(demoList, HttpStatus.OK);
    }

    @GetMapping("bookrides/{id}")
    public ResponseEntity<?> getRide(@PathVariable int id) {
        Offerride ride = service.getRide(id);
        return new ResponseEntity<>(ride,HttpStatus.OK);
    }

    @GetMapping("bookrides/{from}/{to}")
    public ResponseEntity<?> getFilterRides(@PathVariable String from, @PathVariable String to){
        List<Offerride>list1 = service.getRide(from,to);
        return new ResponseEntity<>(list1,HttpStatus.OK);
    }

    @GetMapping("rides/{from}/{to}")
    public ResponseEntity<?> getCarInfo(@PathVariable String from, @PathVariable String to){

        List<ImportantDetails>list1 = service.getCarInformation(from,to);
        return new ResponseEntity<>(list1,HttpStatus.OK);
    }

    @PostMapping("bookrides/{id}/{customerName}/{no_seat_want}/{from}/{to}")
    public ResponseEntity<?> bookRide(@PathVariable int id , @PathVariable String customerName ,@PathVariable int no_seat_want,@PathVariable String from,@PathVariable String to){
        Bookings booking = service.bookRide(id,customerName,no_seat_want,from,to);
        return new ResponseEntity<>("You have booked your ride successfully",HttpStatus.CREATED);
    }


    @GetMapping("booking/{customerName}")
    public ResponseEntity<?> getBookingDetailsById(@PathVariable String customerName){
        Bookings booking = service.getBooking(customerName);
        return new ResponseEntity<>(booking,HttpStatus.OK);
    }

    @GetMapping("invoice/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable int id){
        Invoice invoice = service.getInvoice(id);
        return new ResponseEntity<>(invoice,HttpStatus.OK);
    }
    @PostMapping("invoice/{id}/{bill_amount}")
    public String billPaid(@PathVariable int id,@PathVariable int bill_amount){
        String message = service.settleRide(id, bill_amount);
        return message;
    }

    @GetMapping("booking")
    public ResponseEntity<?> getuser(){
       return new  ResponseEntity<>(service.getList(), HttpStatus.OK);
    }

    @GetMapping("data")
    public ResponseEntity<?> get(){
        service.sendDataToConsumer();
        return new  ResponseEntity<>("Booking information is send successfully to RabbitMQ", HttpStatus.OK);
    }




}
