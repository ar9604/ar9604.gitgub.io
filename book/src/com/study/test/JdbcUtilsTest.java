package com.study.test;

import com.study.utils.JdbcUtils;
import org.junit.Test;

public class JdbcUtilsTest {

    @Test
    public void testJdbcUtils(){
        System.out.println(JdbcUtils.getConnection());
    }
}
