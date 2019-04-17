package com.cec6.yhgl.service;

import com.cec6.yhgl.dao.ElementMapper;
import com.cec6.yhgl.domain.HtmlElement;
import com.cec6.yhgl.table.TB_ELEMENT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HtmlElementService {

    @Autowired
    private ElementMapper elementMapper;

    public List<HtmlElement> findAll() {
        List<HtmlElement> ret = new ArrayList<>();
        elementMapper.selectList(null).stream().forEach((element) -> ret.add(new HtmlElement(element)));
        return ret;
    }

    public HtmlElement addOne(HtmlElement htmlElement) {
        if (htmlElement.getId() == null)
            htmlElement.setId(UUID.randomUUID().toString());
        elementMapper.insert(new TB_ELEMENT(htmlElement));
        return htmlElement;
    }

    public void deleteElement(String id) {
        elementMapper.deleteById(id);
    }

}
