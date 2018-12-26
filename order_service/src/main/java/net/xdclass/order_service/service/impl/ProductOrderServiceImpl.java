package net.xdclass.order_service.service.impl;

import com.alibaba.fastjson.JSON;
import net.xdclass.order_service.domain.ProductOrder;
import net.xdclass.order_service.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    //第一种方法 与 application中的 bean 配合使用
//    @Autowired
//    RestTemplate restTemplate ;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Override
    public ProductOrder save(int userId, int productId) {

        ProductOrder productOrder = new ProductOrder();
        productOrder.setCraeteTime(new Date());
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setUserId(userId);
        productOrder.setProductId(productId);


        //第一种方法
//        String url = "http://product-service/api/v1/product/find?id=" ;
//        Map<String,Object> obj = restTemplate.getForObject(url+productId ,Map.class);
//        System.out.println("第一种方法");

        //第二种方法
        ServiceInstance instance = loadBalancer.choose("product-service");
        String url = String.format("http://%s:%s/api/v1/product/find?id="+productId,instance.getHost(),instance.getPort());
        RestTemplate restTemplate = new RestTemplate() ;
        Map obj = restTemplate.getForObject(url, Map.class);
        System.out.println(JSON.toJSONString(obj));
        System.out.println("第二种方法");
        productOrder.setProductName(obj.get("name").toString());
        productOrder.setPrice(Integer.parseInt(obj.get("price").toString()));
        return productOrder;
    }
}
