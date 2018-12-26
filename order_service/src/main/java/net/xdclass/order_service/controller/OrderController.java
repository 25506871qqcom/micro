package net.xdclass.order_service.controller;


import net.xdclass.order_service.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private ProductOrderService productOrderService ;


    @Value("${server.port}")
    private String port ;


    @RequestMapping("save")
    public Object save(@RequestParam("userId") int userId,@RequestParam("productId") int productId ){


        return  productOrderService.save(userId,productId) ;
    }

}
