package com.cec6.yhgl.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cec6.yhgl.domain.Operation;
import com.cec6.yhgl.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping("/operationList")
    public List<Operation> operationList() {
        List<Operation> ret = operationService.findAll();
        ret.sort(Comparator.comparingInt(Operation::getIndex));
        return ret;
    }

    @PostMapping
    public String createElement(@RequestBody Operation operation) {
        Operation ret = operationService.addOne(operation);
        if (ret.getId() != null)
            return "success";
        return "failed";
    }

    @DeleteMapping("/{ids}")
    public String deleteElement(@PathVariable List<String> ids) {
        for (String id : new HashSet<>(ids)) {
            operationService.deleteOperation(id);
        }
        return "success";
    }

    @GetMapping("/operationTree")
    public String operationTree() {
        List<Operation> opsList = operationService.findAll();
        // sort
        opsList.sort(Comparator.comparingInt(Operation::getIndex));
        JSONArray ret = new JSONArray();
        opsList.stream().forEach((opt) -> {
            JSONObject object = new JSONObject();
            object.put("id", opt.getId());
            object.put("index", opt.getIndex());
            object.put("name", opt.getName() + ":" + opt.getUrl());
            ret.add(object);
        });
        return ret.toJSONString();
    }


}