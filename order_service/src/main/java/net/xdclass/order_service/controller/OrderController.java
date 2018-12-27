package net.xdclass.order_service.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import net.xdclass.order_service.service.ProductOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private ProductOrderService productOrderService ;


    @Value("${server.port}")
    private String port ;


    @Autowired
    private StringRedisTemplate redisTemplate ;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail" ,commandProperties = {
            @HystrixProperty(name="",value = "")
    })
    public Object save(@RequestParam("userId") int userId, @RequestParam("productId") int productId , HttpServletRequest request){

        Map<String,Object> msg = new HashMap<>();
        msg.put("code",0);
        msg.put("data",productOrderService.save(userId,productId));
        return  msg ;
    }


    private Object saveOrderFail(@RequestParam("userId") int userId,@RequestParam("productId") int productId , HttpServletRequest request){


        new Thread(new Runnable() {
            @Override
            public void run() {
                String key="save_order" ;
                String sendValue = redisTemplate.opsForValue().get(key);
                System.out.println("get value:"+sendValue);

                if(StringUtils.isEmpty(sendValue)){
                    System.out.println(" save fail ! why? find it ");
                    redisTemplate.opsForValue().set(key,"save fail at "+new Date()+ " from "+request.getRemoteAddr(),20, TimeUnit.SECONDS) ;
                }else{
                    System.out.println(" save fail ! handling ... after 20 second ,send again 1 ");
                }
            }
        }).start();


        Map<String,Object> msg = new HashMap<>();
        msg.put("code",-1);
        msg.put("msg","try again ! ");
        return  msg;
    }



}
