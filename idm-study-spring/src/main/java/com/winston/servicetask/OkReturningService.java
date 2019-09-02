package com.winston.servicetask;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName OkReturningService
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/9/2 10:46
 * @Version：
 */
//@Component("okReturningService")
public class OkReturningService implements Serializable {

    public String invoke(){
        return "老高";
    }

}
