package com.darren;

import com.darren.bean.Emp;
import com.darren.dao.EmpDao;
import com.darren.dao1.EmpDao1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * <h3>mybatis</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2022年07月24日 18:30:32
 **/
@ContextConfiguration(locations = {"classpath:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MyBatisSpringTest {

  @Autowired
  private EmpDao dao;
  @Autowired
  private EmpDao1 dao1;

  @Test
  public void test01(){
    List<Emp> empByEmpno = dao.selectAll();
    empByEmpno.stream().forEach(System.out::println);

    List<Emp> empByEmpno1 = dao1.selectAll();
    empByEmpno1.stream().forEach(System.out::println);
  }

}

