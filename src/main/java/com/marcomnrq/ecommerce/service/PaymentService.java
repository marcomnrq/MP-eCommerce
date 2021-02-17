package com.marcomnrq.ecommerce.service;

import com.google.gson.Gson;
import com.marcomnrq.ecommerce.domain.model.Payment;
import com.marcomnrq.ecommerce.domain.repository.PaymentRepository;
import com.marcomnrq.ecommerce.exception.CustomException;
import com.marcomnrq.ecommerce.resource.MercadopagoResource;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final Gson gson;

    public MercadopagoResource generatePreference(){
        try{
            MercadoPago.SDK.setAccessToken("APP_USR-8208253118659647-112521-dd670f3fd6aa9147df51117701a2082e-677408439");
            MercadoPago.SDK.setIntegratorId("dev_2e4ad5dd362f11eb809d0242ac130004");
            Preference preference = new Preference();
            // Appending the items
            Item item = new Item();
            item.setId("1234");
            item.setTitle("Producto ejemplo");
            item.setQuantity(1);
            item.setDescription("Dispositivo movil de tienda eCommerce");
            item.setCurrencyId("PEN");
            item.setUnitPrice((float) 14.99);
            preference.appendItem(item);
            // Payer information
            Payer payer = new Payer();
            payer.setName("Lalo Landa")
                    .setEmail("test_user_46542185@testuser.com")
                    .setIdentification(new Identification()
                            .setType("DNI")
                            .setNumber("223344445"))
                    .setPhone(new Phone()
                            .setAreaCode("52")
                            .setNumber("5549737300"))
                    .setAddress(new Address()
                            .setZipCode("03940")
                            .setStreetNumber(1602)
                            .setStreetName("Insurgentes Sur"));
            preference.setPayer(payer);
            // Back urls and notification url
            preference.setBackUrls(
                    new BackUrls()
                            .setFailure("https://mp-marco.herokuapp.com/payment")
                            .setPending("https://mp-marco.herokuapp.com/payment")
                            .setSuccess("https://mp-marco.herokuapp.com/payment")
            );
            preference.setNotificationUrl("https://mp-marco.herokuapp.com/api/payments/notifications");
            preference.setExternalReference("manriqueacham@gmail.com");
            // Payment Methods
            PaymentMethods paymentMethods = new PaymentMethods();
            paymentMethods.setExcludedPaymentMethods("diners");
            paymentMethods.setExcludedPaymentTypes("atm");
            paymentMethods.setInstallments(6);
            preference.setPaymentMethods(paymentMethods);
            preference.setAutoReturn(Preference.AutoReturn.approved);
            // Saving and returning
            preference = preference.save();

            // Resource (DTO)
            MercadopagoResource resource = new MercadopagoResource();
            resource.setId(preference.getId());
            resource.setInitPoint(preference.getInitPoint());
            resource.setSandboxInitPoint(preference.getSandboxInitPoint());
            resource.setItems(preference.getItems());
            resource.setExternalReference(preference.getExternalReference());
            return resource;

        } catch (MPException exception){
            throw new CustomException("Something went wrong", exception);
        }
    }
    public void paymentNotification(String parameters, String body){
        Payment payment = new Payment();
        payment.setParameters(parameters);
        payment.setBody(body);
        paymentRepository.save(payment);
    }
    public Page<Payment> getAllPayments(Pageable pageable){
        return paymentRepository.findAll(pageable);
    }

    public ResponseEntity deleteAllPayments(){
        paymentRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
