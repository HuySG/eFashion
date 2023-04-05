package com.cybersoft.eFashion.controller;

import com.cybersoft.eFashion.dto.OrderItemsDTO;
import com.cybersoft.eFashion.payload.ResponseData;
import com.cybersoft.eFashion.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping()
    public ResponseEntity<?> getAllOrderItems(){
        ResponseData responseData = new ResponseData();
        responseData.setData(cartService.getAllOrderItems());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addCart(@RequestBody OrderItemsDTO orderItemsDTO, @RequestParam("id") int id){

        ResponseData responseData = new ResponseData();
        boolean isExists = cartService.findProductById(id);

        if(isExists){
            cartService.plusQuantity(id);
        }else {
            responseData.setData(cartService.addCart(orderItemsDTO));
        }
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    @PutMapping("plusQuantity")
    public ResponseEntity<?> plusQuantity(@RequestParam("id") int id){
        cartService.plusQuantity(id);
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @PutMapping("subtractQuantity")
    public ResponseEntity<?> subtractQuantity(@RequestParam("id") int id){
        cartService.subtractQuantity(id);
        return new ResponseEntity<>("",HttpStatus.OK);
    }
}