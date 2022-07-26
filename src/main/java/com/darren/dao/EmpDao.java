package com.darren.dao;


import com.darren.bean.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpDao {

    public Emp findEmpByEmpno(Integer empno);

    public List<Emp> selectAll();

    List<Emp> selectEmpByIds(List<Long> empnos);

    public Emp findEmpByEmpnoAndEname(@Param("empno") Integer empno, @Param("ename") String ename);

    public int insert(Emp emp);

    public int update(Emp emp);

    public int delete(Integer empno);
}
