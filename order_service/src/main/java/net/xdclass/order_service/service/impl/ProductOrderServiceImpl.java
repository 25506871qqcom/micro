package net.xdclass.order_service.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import net.xdclass.order_service.domain.ProductOrder;
import net.xdclass.order_service.service.ProductClient;
import net.xdclass.order_service.service.ProductOrderService;
import net.xdclass.order_service.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private final Logger logger  = LoggerFactory.getLogger(getClass()) ;

    //第一种方法 与 application中的 bean 配合使用
//    @Autowired
//    RestTemplate restTemplate ;

//    @Autowired
//    private LoadBalancerClient loadBalancer;

    @Autowired
    private ProductClient productClient ;

    @Override
    public ProductOrder save(int userId, int productId) {
        logger.info("order service save");
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
//        ServiceInstance instance = loadBalancer.choose("product-service");
//        String url = String.format("http://%s:%s/api/v1/product/find?id="+productId,instance.getHost(),instance.getPort());
//        RestTemplate restTemplate = new RestTemplate() ;
//        Map obj = restTemplate.getForObject(url, Map.class);
//        System.out.println(JSON.toJSONString(obj));
//        System.out.println("第二种方法");


         String response = productClient.findById(productId);

        JsonNode jsonNode = JsonUtils.str2JsonNode(response) ;



        productOrder.setProductName(jsonNode.get("name").toString());
        productOrder.setPrice(Integer.parseInt(jsonNode.get("price").toString()));
        return productOrder;
    }
}
