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

    private final Gson gson = new Gson();


}
