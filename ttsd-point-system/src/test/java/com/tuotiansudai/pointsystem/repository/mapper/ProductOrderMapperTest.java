package com.tuotiansudai.pointsystem.repository.mapper;

import com.tuotiansudai.pointsystem.repository.model.GoodsType;
import com.tuotiansudai.pointsystem.repository.model.ProductModel;
import com.tuotiansudai.pointsystem.repository.model.ProductOrderModel;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.repository.model.UserStatus;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
public class ProductOrderMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductOrderMapper productOrderMapper;


    @Test
    public void shouldCreateProductOrderModel() throws Exception {
        UserModel fakeUserModel = this.createFakeUserModel();

        ProductModel productModel = this.createFakeProductModel(fakeUserModel.getLoginName());

        ProductOrderModel productOrderModel = new ProductOrderModel();
        productOrderModel.setId(10001);
        productOrderModel.setConsignment(true);
        productOrderModel.setCreatedTime(new Date());
        productOrderModel.setCreatedUser(fakeUserModel.getLoginName());
        productOrderModel.setNum(2);
        productOrderModel.setProductId(productModel.getId());

        productOrderMapper.create(productOrderModel);



       // assertThat(productModelList.size(), is(1));
    }

    private UserModel createFakeUserModel() {
        UserModel fakeUserModel = new UserModel();
        fakeUserModel.setLoginName("productorderCreateUser");
        fakeUserModel.setPassword("123abc");
        fakeUserModel.setEmail("12345@abc.com");
        fakeUserModel.setMobile("13900000000");
        fakeUserModel.setRegisterTime(new Date());
        fakeUserModel.setStatus(UserStatus.ACTIVE);
        fakeUserModel.setSalt(UUID.randomUUID().toString().replaceAll("-", ""));
        userMapper.create(fakeUserModel);
        return fakeUserModel;
    }

    private ProductModel createFakeProductModel(String loginName){
        ProductModel fakeProductModel = new ProductModel();
        fakeProductModel.setId(100001);
        fakeProductModel.setGoodsType(GoodsType.VIRTUAL);
        fakeProductModel.setProductName("虚拟商品");
        fakeProductModel.setDescription("充值卡");
        fakeProductModel.setActive(true);
        fakeProductModel.setCreatedUser(loginName);
        fakeProductModel.setCreatedTime(new Date());
        fakeProductModel.setStartTime(new Date());
        fakeProductModel.setEndTime(new DateTime().plusDays(7).toDate());
        fakeProductModel.setTotalCount(100);
        fakeProductModel.setUsedCount(0);
        fakeProductModel.setImageUrl("upload/images/a.png");
        fakeProductModel.setProductPrice(2000);
        fakeProductModel.setSeq(1);
        return fakeProductModel;
    }



}
