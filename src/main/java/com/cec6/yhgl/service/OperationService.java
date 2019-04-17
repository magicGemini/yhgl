package com.cec6.yhgl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cec6.yhgl.dao.OperationMapper;
import com.cec6.yhgl.domain.Operation;
import com.cec6.yhgl.table.TB_OPERATION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OperationService {

    @Autowired
    private OperationMapper operationMapper;

    public List<Operation> findAll() {
        List<Operation> ret = new ArrayList<>();
        operationMapper.selectList(null).stream().forEach((oper) -> ret.add(new Operation(oper)));
        return ret;
    }

    public Operation addOne(Operation operation) {
        if (operation.getIsValid() == null || operation.getIsValid() == false)
            operation.setIsValid(true);
        if (operation.getId() == null)
            operation.setId(UUID.randomUUID().toString());
        operationMapper.insert(new TB_OPERATION(operation));
        return operation;
    }

    public void deleteOperation(String id) {
        operationMapper.deleteById(id);
    }

    public Operation findByUrlAndMethod(String url, String method) {
        TB_OPERATION operation = operationMapper.selectOne(new QueryWrapper<TB_OPERATION>().eq("url", url).eq("method", method));
        if (operation != null)
            return new Operation(operation);
        return null;
    }
}
