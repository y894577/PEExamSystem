package com.scnu.peexamsystem.service.institute;

import com.scnu.peexamsystem.dao.institute.InstituteDao;
import com.scnu.peexamsystem.entity.Institute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InstituteServiceImpl implements InstituteService {
    @Autowired
    InstituteDao instituteDao;

    @Override
    public Map<String, Object> queryInstituteList() {
        Map<String, Object> map = new HashMap<>();
        List<Institute> list = instituteDao.findAll();
        boolean isQuery = list.size() > 0;

        map.put("msg", "查询institute" + (isQuery ? "成功" : "失败"));
        map.put("result", list.toArray());
        map.put("code", isQuery ? 1 : 0);
        return map;
    }
}
