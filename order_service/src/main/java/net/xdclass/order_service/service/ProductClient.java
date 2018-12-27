package net.xdclass.order_service.service;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="product-service")
public interface ProductClient {

    @GetMapping("/api/v1/product/find")
    String findById(@RequestParam("id") int id );

}
