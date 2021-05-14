package com.wsl.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * All Rights Reserved, Designed By www.jet-china.com.cn
 *
 * @Title: .java
 * @Package: com.jet.cloud.deepmind
 * @Description:
 * @author: 济中节能 zhuyicheng
 * @date: 2021/4/6 16:58
 * @version: V1.0
 * @Copyright: 2020 济中节能 All Rights Reserved.
 */
public class DingReadExcelUtil {
    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(DingReadExcelUtil.class);

    /**
     * xls后缀的表格文件
     */
    private static final String EXCEL_XLS = ".xls";

    /**
     * xlsx后缀的表格文件
     */
    private static final String EXCEL_XLSX = ".xlsx";

    /**
     * excel文件内容对象
     */
    private Workbook wb = null;

    /**
     * 单个表格内容
     */
    private Sheet sheet = null;

    /**
     * 行信息对象
     */
    private Row row = null;

    /**
     * @param filePath excel文件路径
     * @return true:初始化成功, false:初始化失败
     */
    public boolean initReadExcel(String filePath) {
        if (filePath == null) {
            return false;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (EXCEL_XLS.equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (EXCEL_XLSX.equals(extString)) {
                wb = new XSSFWorkbook(is);
            } else {
                return false;
            }
            sheet = wb.getSheetAt(0);
//            row = sheet.getRow(1);
            // 总行数
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < rows; i++) {
                row = sheet.getRow(i);
                String username = row.getCell(0).toString().trim();
                String password = row.getCell(1).toString().trim();
                System.out.println(username+"----------"+password);
            }
            return true;
        } catch (IOException e) {
            log.info(e.toString());
            return false;
        } finally {
            try {
                if (null != wb) {
                    wb.close();
                }
            } catch (IOException e) {
                log.info(e.toString());
                return false;
            }
        }
    }


    public static void main(String[] args) {
        DingReadExcelUtil dingReadExcelUtil = new DingReadExcelUtil();
        dingReadExcelUtil.initReadExcel("D:\\wyWorkSpace\\wuyu.xlsx");

    }

}
