package com.OptimumPool.OfferRide.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND,reason = "This offer ride in not present")
public class OfferRideNotFound extends Exception{
}
