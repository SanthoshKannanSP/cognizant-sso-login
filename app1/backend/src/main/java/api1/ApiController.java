package api1;

import java.util.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import api1.dto.request.OrderRequest;
import api1.dto.response.OrderResponse;
import api1.entity.Order;
import api1.service.OrderService;
import api1.entity.OrderInput;

@RestController
public class ApiController {
	
	private final OrderService orderService;

	public ApiController(OrderService orderService){
		this.orderService = orderService;
	}

	@GetMapping(path="api/orders")
    public List<Order> findAllOrder() {
        return orderService.findAllOrder();
    }

	@PostMapping(path="api/add/order")
	public OrderResponse addOrder(@RequestBody OrderInput orderInput){
			OrderRequest or = new OrderRequest();
			or.setName(orderInput.getName());
			or.setCategory(orderInput.getCategory());
			Date today = new Date();
			or.setDateOfOrder(today);
			or.setPrice(orderInput.getPrice());
			return orderService.saveOrder(or);
	}

	@PreAuthorize("hasRole('admin-access')")
	@GetMapping(path="/check/admin")
	public int isAdmin(){
		return 1;
	}
	
	@GetMapping(path = "/anon")
	public String anon() {
		return "Hello! This page is accessible to everyone";
	}
}