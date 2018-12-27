package net.xdclass.order_service.fallback;

import net.xdclass.order_service.service.ProductClient;
import org.springframework.stereotype.Component;


@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public String findById(int id) {

        System.out.println("ProductClientFallback");
        return null;
    }
}
