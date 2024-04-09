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

public class LogoutDataProvider {
	@DataProvider(name = "logoutData")
    public Object[][] getLogoutData() throws IOException {
        FileInputStream file = new FileInputStream(new File("C:\\Users\\acer\\Downloads\\dataLogout.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getLastRowNum();
        Object[][] data = new Object[rowCount][3];

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1);
            if (row != null) {
                String email = "";
                String password = "";
                String expectedResult = "";

                Cell cellEmail = row.getCell(0);
                if (cellEmail != null && cellEmail.getCellType() == CellType.STRING) {
                    email = cellEmail.getStringCellValue().trim();
                }

                Cell cellPassword = row.getCell(1);
                if (cellPassword != null && cellPassword.getCellType() == CellType.STRING) {
                    password = cellPassword.getStringCellValue().trim();
                }

                Cell cellExpectedResult = row.getCell(2);
                if (cellExpectedResult != null && cellExpectedResult.getCellType() == CellType.STRING) {
                    expectedResult = cellExpectedResult.getStringCellValue().trim();
                }

                if (!email.isEmpty() || !password.isEmpty() || !expectedResult.isEmpty()) {
                    data[i][0] = email;
                    data[i][1] = password;
                    data[i][2] = expectedResult;
                }
            }
        }

        workbook.close();
        file.close();

        return data;
    }
}
