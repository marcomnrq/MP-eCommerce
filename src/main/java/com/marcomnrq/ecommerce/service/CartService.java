package com.marcomnrq.ecommerce.service;

import com.marcomnrq.ecommerce.domain.model.Cart;
import com.marcomnrq.ecommerce.domain.model.CartItem;
import com.marcomnrq.ecommerce.domain.model.User;
import com.marcomnrq.ecommerce.domain.repository.CartRepository;
import com.marcomnrq.ecommerce.domain.repository.UserRepository;
import com.marcomnrq.ecommerce.exception.CustomException;
import com.marcomnrq.ecommerce.resource.MercadopagoResource;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    public Cart createCart(User user){
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Cart emptyCart(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("user not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CustomException("user not found"));
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

    public Cart addItem(Long userId, CartItem cartItem){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("user not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CustomException("user not found"));
        if(cart.getItems().contains(cartItem) == true){
            // Item is already on cart
            int index = cart.getItems().indexOf(cartItem);
            int quantity = cart.getItems().get(index).getQuantity();
            cart.getItems().get(index).setQuantity(quantity + 1);
        }else{
            // Add item to the cart

        }
        return cartRepository.save(cart);
    }

    public MercadopagoResource checkout(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("user not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CustomException("user not found"));
        List<CartItem> itemList = cart.getItems();
        if (itemList.size() <= 0){
            throw new CustomException("Item list empty");
        }
        try{
            MercadoPago.SDK.setAccessToken("APP_USR-2655234219308178-021317-3a8274d0c9d2bd477b1366aaea089466-582311310");
            Preference preference = new Preference();
            // Appending the items
            for(int i = 0; i < itemList.size(); ++i){
                Item item = new Item();
                item.setId(itemList.get(i).getId().toString());
                item.setQuantity(itemList.get(i).getQuantity());
                item.setDescription(itemList.get(i).getProduct().getDescription());
                item.setCurrencyId("PEN");
                item.setUnitPrice(itemList.get(i).getUnitPrice());
                preference.appendItem(item);
            }
            // Payer information
            Payer payer = new Payer();
            payer.setName("Lalo Landa")
                    .setIdentification(new Identification().setType("DNI").setNumber("70760405"))
                    .setEmail("manriqueacham@gmail.com")
                    .setAddress(new Address().setZipCode("52"));

            // Back urls and notification url
            preference.setBackUrls(
                    new BackUrls()
                            .setFailure("http://localhost:8080/feedback")
                            .setPending("http://localhost:8080/feedback")
                            .setSuccess("http://localhost:8080/feedback")
            );
            preference.setNotificationUrl("http://localhost:8080/api/carts/notifications");
            // Saving and returning
            //preference.setPayer(payer);
            preference = preference.save();

            // Resource (DTO)
            MercadopagoResource resource = new MercadopagoResource();

            resource.setId(preference.getId());
            resource.setInitPoint(preference.getInitPoint());
            resource.setSandboxInitPoint(preference.getSandboxInitPoint());
            return resource;

        } catch (MPException exception){
            throw new CustomException("Something went wrong", exception);
        }
    }
}
