package com.example.delayedmessageexchange.service;

import com.example.delayedmessageexchange.entity.Department;
import com.example.delayedmessageexchange.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired

    private AmqpTemplate amqpTemplate;

    @Value("${spring.rabbitmq.sms.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.sms.routingkey}")
    private String routingKey;
    @Value("${spring.rabbitmq.sms.queue}")
    private String queue;
    @Value("${spring.rabbitmq.sms.delay}")
    private Integer delay;

    public DepartmentService(DepartmentRepository departmentRepository, AmqpTemplate amqpTemplate) {
        this.departmentRepository = departmentRepository;

        this.amqpTemplate = amqpTemplate;

    }

    public String saveDept(Department department) {
        departmentRepository.save(department);
        return "Department is saved";
    }

    public List<Department> findAll() {
        List<Department> departments = departmentRepository.findAll();

        return departments;
    }

    public String findDeptById(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return "Id is present";
        } else {
            return "No record found";
        }


    }

    public String pushMessageToQueue() {
        Department department = new Department();
        department.setId(1);
        department.setDepName("Ozair");
        department.setHasProject(true);
        sendMessage(department);//,50000);
        return "Message push Queue" + department;
    }

    public void sendMessage(Department dept)// final long delayTime)
    {
        amqpTemplate.convertAndSend(exchange, routingKey, dept

                , message -> {

                    message.getMessageProperties().setDelay(delay);

                    return message;
                });
    }

//    public void recieveMessageFromQueue(Department dept) {
//
//        amqpTemplate.receive("delay_test_queue",50000);
//
//
//        log.info("Message Revcieved: {}", dept.getId() + dept.getDepName() + dept.isHasProject());
//    }
}
