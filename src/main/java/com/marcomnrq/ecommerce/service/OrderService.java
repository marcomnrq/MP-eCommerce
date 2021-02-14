package com.marcomnrq.ecommerce.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marcomnrq.ecommerce.exception.CustomException;
import com.marcomnrq.ecommerce.resource.MercadopagoResource;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public MercadopagoResource createOrder(){
        try{
            MercadoPago.SDK.setAccessToken("APP_USR-2655234219308178-021317-3a8274d0c9d2bd477b1366aaea089466-582311310");

            Preference preference = new Preference();
            // Creating the item
            Item item = new Item();
            item.setId("1234")
                    .setTitle("Example product")
                    .setDescription("Dispositivo móvil de Tienda e-commerce")
                    .setPictureUrl("https://source.unsplash.com/random/300x300")
                    .setQuantity(1)
                    .setCurrencyId("PEN")
                    .setUnitPrice((float) 15.00);
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
            preference.setNotificationUrl("http://localhost:8080/api/orders/notifications");
            // Saving and returning
            //preference.setPayer(payer);
            preference.appendItem(item);
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
