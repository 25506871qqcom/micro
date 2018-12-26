package net.xdclass.product_service.controller;


import net.xdclass.product_service.domain.Product;
import net.xdclass.product_service.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService ;


    @Value("${server.port}")
    private String port ;

    @RequestMapping("list")
    public Object list(){
        return productService.listProduct();
    }

    @RequestMapping("find")
    public Object findById(@RequestParam("id") int id ){

        Product product = productService.findById(id) ;

        Product result = new Product();

        BeanUtils.copyProperties(product,result);

        result.setName(product.getName()+" from port  "+port);

        return result;
    }

}
