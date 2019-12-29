package com.itheima;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/21
 * @description:
 */
public class JDBCTest {

    @Test
    public void testJDBC() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql:///sz82", "root", "root");
        PreparedStatement pstmt = conn.prepareStatement("select DATE_FORMAT(orderDate,'%e') date, number, reservations  from t_ordersetting where orderDate between ? and ?");
        pstmt.setString(1, "2019-12-01");
        pstmt.setString(2, "2019-12-31");
        ResultSet rs = pstmt.executeQuery();
        // 结果信息
        ResultSetMetaData metaData = rs.getMetaData();
        // 返回的结果集列的个数
        int cnt = metaData.getColumnCount();
        // 获取列的名称
        String[] keys = new String[cnt];
        for(int i = 1; i <= cnt; i++) {
            //System.out.print(metaData.getColumnLabel(i) + ":");
            keys[i-1] = metaData.getColumnLabel(i);
        }

        // List<Map<String,Integer>>
        //System.out.println();
        List<Map<String,Integer>> result = new ArrayList<Map<String,Integer>>();
        Map<String,Integer> data = null;
        while(rs.next()){
            data = new HashMap<String,Integer>();
            for (String key : keys) {
                data.put(key, rs.getInt(key.toUpperCase()));
            }
            result.add(data);
            //System.out.println(rs.getInt(1) + ":" + rs.getInt(2) + ":" + rs.getInt(3));
        }
        System.out.println(JSON.toJSONString(result));
        rs.close();
        pstmt.close();
        conn.close();
    }
}
