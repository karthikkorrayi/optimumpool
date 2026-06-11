package com.OptimumPool.OfferRide.Controller;

import com.OptimumPool.OfferRide.Exception.OfferRideNotFound;
import com.OptimumPool.OfferRide.Model.Offerride;
import com.OptimumPool.OfferRide.Services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class OfferRideController {

    @Autowired
    private OfferService oService;




    @PostMapping("offerride")
    public ResponseEntity<?> addOfferRideDb(@RequestBody Offerride f){
        return new ResponseEntity<>(oService.addOffer(f), HttpStatus.CREATED);
    }

    @GetMapping("offerride")
    public  ResponseEntity<?> getUser(){
        oService.sendDataToConsumer();
        return  new ResponseEntity<>(oService.getRide(),HttpStatus.OK);
    }



    @PutMapping("updateRide/{id}")
    public ResponseEntity<?>  updateOfferRide(@RequestBody Offerride of , @PathVariable int id ) throws OfferRideNotFound {
        try {
            oService.updateRide(of,id);
            return new ResponseEntity<>("Your offer has successfully updated !",HttpStatus.OK);
        } catch(OfferRideNotFound e) {
            throw new OfferRideNotFound();
        }
        catch(Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getbookings")
    public ResponseEntity<?> getBookings(){
        return  new ResponseEntity<>(oService.getALlBooking() , HttpStatus.OK);

    }






}
