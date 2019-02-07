import Model.Firm;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Тупо записываем данные из Словаря firmMap
 * в файл "outmaga.xls")
 */
public class controller {

    public static void action(Map<Long, Firm> firmMap) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        Sheet sheet = hssfWorkbook.createSheet();
        int countRow = 0;
        Cell cell;
        for (Map.Entry<Long, Firm> entry : firmMap.entrySet()) {
            Row row = sheet.createRow(countRow++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue().getName());
            row.createCell(2).setCellValue(entry.getValue().getPhoneNumber());
            row.createCell(3).setCellValue(entry.getValue().getAdress());
        }

        try {
            File file = new File("outmaga.xls");
            hssfWorkbook.write(file);
            hssfWorkbook.close();
        } catch (IOException ex) {

        }

    }
}
