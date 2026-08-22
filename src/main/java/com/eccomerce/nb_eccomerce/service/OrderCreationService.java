package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.dto.request.OrderRequest;
import com.eccomerce.nb_eccomerce.entity.Cart;
import com.eccomerce.nb_eccomerce.entity.Order;
import com.eccomerce.nb_eccomerce.entity.User;

public interface OrderCreationService {
    Order create(Cart cart, User user, OrderRequest request);
}
