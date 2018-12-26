package net.xdclass.order_service.service.impl;

import com.alibaba.fastjson.JSON;
import net.xdclass.order_service.domain.ProductOrder;
import net.xdclass.order_service.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    RestTemplate restTemplate ;

    @Override
    public ProductOrder save(int userId, int productId) {

        ProductOrder productOrder = new ProductOrder();
        productOrder.setCraeteTime(new Date());
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setUserId(userId);
        productOrder.setProductId(productId);

        String url = "http://product-service/api/v1/product/find?id=" ;
//        String url = "http://localhost:8771/api/v1/product/find?id=" ;
//        Object obj =restTemplate.getForObject(url+productId ,Object.class);

        Map<String,Object> obj = restTemplate.getForObject(url+productId ,Map.class);
//        {"id":1,"name":"iphone from port  8771 from port  8771","price":9999,"score":10}
        System.out.println(JSON.toJSONString(obj));

        return productOrder;
    }
}
