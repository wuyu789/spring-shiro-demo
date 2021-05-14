package com.wsl.utils;

import com.datastax.driver.core.*;
import com.wsl.bean.DataPoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FirstDemo {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder()
                .addContactPoint("127.0.0.1")
                .build();

        Session session = cluster.connect();
        System.out.println(session);
        long startTime = System.currentTimeMillis();

//        String insertSql = "INSERT INTO mycasdb.data_points (point,timestamp,value) VALUES ('LYGSHCYJD.DGWSCL.E2-2-18.Ep_imp',1572488391000,11) ;";
//        session.execute(insertSql);



//        ResultSet rs = session.execute(
////                QueryBuilder.select("point", "timestamp", "value")
//                QueryBuilder.select().all()
//                        .from("mycasdb", "data_points")
//                        .where(QueryBuilder.eq("point", "LYGSHCYJD.DGWSCL.E2-54622cd0-ea69-4c19-a085-36cc009ee602.Ep_imp"))
////                        .and(QueryBuilder.eq("timestamp1", 1620461154638l))
//                        .and(QueryBuilder.eq("value", 3296)));
//        Iterator<Row> rsIterator = rs.iterator();
//        while (rsIterator.hasNext()) {
//            Row row = rsIterator.next();
//            dataPoint = new DataPoint();
//            dataPoint.setPoint(row.getString("point"));
//            dataPoint.setTimestamp(row.getLong("timestamp1"));
//            dataPoint.setValue(row.getDouble("value"));
//            System.out.println(dataPoint);
//        }
        List<DataPoint> list = new ArrayList<>();

        try {
            String GET_STUDENT = "SELECT * FROM  mycasdb.data_points WHERE point=? and timestamp1 >= ? and timestamp1 <= ?;";
            PreparedStatement prepareStatement = session.prepare(GET_STUDENT);
            BoundStatement bindStatement = new BoundStatement(prepareStatement).bind("LYGSHCYJD.DGWSCL.E2-d7d79272-28d2-4f6d-a80d-3f91bca9dae1.Ep_imp",1577808000000l, 1609430400000l);
            ResultSet rs = session.execute(bindStatement);
            Iterator<Row> rsIterator = rs.iterator();
            while (rsIterator.hasNext()) {
                Row row = rsIterator.next();
                DataPoint dataPoint = null;
                dataPoint = new DataPoint();
                dataPoint.setPoint(row.getString("point"));
                dataPoint.setTimestamp(row.getLong("timestamp1"));
                dataPoint.setValue(row.getDouble("value"));
                list.add(dataPoint);
                System.out.println(dataPoint);
            }

            System.err.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cluster.close();
        }


        long endTime = System.currentTimeMillis();
        System.out.println("共运行:" + (endTime - startTime) + "ms");


    }
}
