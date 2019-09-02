package com.winston.task;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @ClassName WinstonSingleTaskListener
 * @Author: Winston
 * @Description: 任务监听器
 * @Date:Create：in 2019/8/30 11:30
 * @Version：
 */
public class WinstonSingleTaskListener implements TaskListener {


    public void notify(DelegateTask delegateTask) {
        String assignee = "Winston";
        // 设置任务处理人
        delegateTask.setAssignee(assignee);
    }
}
