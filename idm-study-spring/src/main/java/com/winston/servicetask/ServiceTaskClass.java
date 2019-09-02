package com.winston.servicetask;

import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @ClassName ServiceTaskClass
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/9/2 9:52
 * @Version：
 */
public class ServiceTaskClass implements JavaDelegate {

    Expression age;

    public void execute(DelegateExecution execution) {
        System.out.println(age);
        System.out.println(age.getExpressionText());
        System.out.println(age.getValue(execution));
        execution.setVariable("Winston", "Winston");
    }
}
