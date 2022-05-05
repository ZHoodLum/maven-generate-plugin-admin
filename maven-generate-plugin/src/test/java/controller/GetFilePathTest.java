package controller;

import org.junit.jupiter.api.Test;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/5 20:52
 * @Description:
 */
public class GetFilePathTest {

    @Test
    public void getFilePathTest() {
        this.getClass().getClassLoader().getResourceAsStream("excel_service_template.xlsx");
        System.out.println();
    }
}
