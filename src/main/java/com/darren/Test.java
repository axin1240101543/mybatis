package com.darren;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>mybatis</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2022年07月22日 21:21:42
 **/
public class Test {

    public static void main(String[] args) {
      String str = "java{0}替换测试，{1}行不行，{2}可以的";
      /*String [] valueList = {"占位符","试试","肯定"};*/
//      Object[] arrT = new String[2];
//      arrT[0] = "wowo";
      List<String> values = new ArrayList<>();
      values.add("aaaa");
      values.add("bbbb");
      values.add("cccc");
      String[] array = values.toArray(new String[0]);
      String result = MessageFormat.format(str, array);
      System.out.println(result);
    }

}

