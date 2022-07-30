package com.darren.dao1;


import com.darren.bean.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface EmpDao1 {

    public Emp findEmpByEmpno(Integer empno);

    public List<Emp> selectAll();

    List<Emp> selectEmpByIds(List<Long> empnos);

    public Emp findEmpByEmpnoAndEname(@Param("empno") Integer empno, @Param("ename") String ename);

    public int insert(Emp emp);

    public int update(Emp emp);

    public int delete(Integer empno);
}
