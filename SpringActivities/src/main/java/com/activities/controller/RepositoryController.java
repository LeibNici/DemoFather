package com.activities.controller;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class RepositoryController {

    /**
     * RepositoryService，是Activiti的资源管理接口，提供了管理和控制流程发布包和流程定义的操作。使用工作流建模工具设计的业务流程图需要使用此Service将流程定义文件的内容部署到计算机中。
     * 除了流程部署定义以外还可以做如下的操作：
     * 查询引擎中的发布包和流程定义。
     * 暂停或激活发布包以及对应全部和特定流程定义。暂停意味着它们不能再在执行任务操作了，激活是对应的反向操作。
     * 获取多种资源，像包含在发布包中的文件获引擎自动生成的流程图。
     * 获取流程定义的POJO，可以用解析流程，而不必通过XML。
     */
    @Autowired
    private RepositoryService repositoryService;

    /**
     * RuntimeService是Activiti的流程运行管理接口，可以从这个接口中获取很多关于流程执行相关的信息。
     */
    @Autowired
    private RuntimeService runtimeService;

    /**
     * TaskService是Activiti的任务管理接口，可以从这个接口中获取任务的信息。
     */
    @Autowired
    private TaskService taskService;

    /**
     * HistoryService是Activiti的历史管理类，可以查询历史信息，执行流程时，引擎会包含很多数据（根据配置），比如流程实例启动时间，任务的参与者，完成任务的时间，每个流程实例的执行路径，等等。
     */
    @Autowired
    private HistoryService historyService;

    /**
     * ManagementService是Activiti的引擎管理接口，提供了对Activiti流程引擎的管理和维护功能，这些功能不在工作流驱动的应用程序中使用，主要用于Activiti系统的日常维护。
     */
    @Autowired
    private ManagementService managementService;

    /**
     * 部署流程
     */
    @GetMapping("/getRepository")
    public Map<String, Object> getRepository() {
        Map<String, Object> result = new HashMap<>();
        Deployment leave = repositoryService.createDeployment()
                .addClasspathResource("bpmn/leave.bpmn")
                .name("请假申请流程")
                .deploy();
        log.info(leave.getId());
        log.info(leave.getName());

        result.put("code", 200);
        result.put("data", leave.getName());

        return result;
    }

    @GetMapping("/startProcess")
    public void startProcess() {
        ProcessInstance myLeave = runtimeService.startProcessInstanceByKey("leave");
        log.info("流程定义id: {}", myLeave.getProcessDefinitionId());
        log.info("流程实例id: {}", myLeave.getId());
        log.info("当前活动id: {}", myLeave.getActivityId());
    }

    @GetMapping("/findTask")
    public void findTask() {
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("leave")
                .taskCandidateGroup("deptLeader")
                .list();

        list.forEach(task -> {
            log.info("流程实例id: {}",task.getProcessInstanceId());
            log.info("任务id: {}",task.getId());
            log.info("任务负责任: {}",task.getAssignee());
            log.info("任务名称: {}",task.getName());
        });
    }

    @GetMapping("/completTask")
    public void completTask() {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("leave")
                .taskCandidateGroup("deptLeader")
                .singleResult();
        taskService.complete(task.getId());
    }

    @GetMapping("/activeTask")
    public void activeTask() {
        ProcessDefinition leave = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("leave")
                .singleResult();
        log.info(String.valueOf(leave.isSuspended()));
    }
}
