package dataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class SearchDataProvider {
	@DataProvider(name = "searchData")
    public Object[][] getLoginData() throws IOException {
        FileInputStream file = new FileInputStream(new File("C:\\Users\\acer\\Downloads\\dataSearch.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getLastRowNum();
        Object[][] data = new Object[rowCount][2];

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1);
            if (row != null) {
                String keyword = "";
                String expectedResult = "";

                Cell cellkeyword = row.getCell(0);
                if (cellkeyword != null && cellkeyword.getCellType() == CellType.STRING) {
                	keyword = cellkeyword.getStringCellValue().trim();
                }


                Cell cellExpectedResult = row.getCell(1);
                if (cellExpectedResult != null && cellExpectedResult.getCellType() == CellType.STRING) {
                    expectedResult = cellExpectedResult.getStringCellValue().trim();
                }

                if (!keyword.isEmpty() || !expectedResult.isEmpty()) {
                    data[i][0] = keyword;
                    data[i][1] = expectedResult;
                }
            }
        }

        workbook.close();
        file.close();

        return data;
    }
}
