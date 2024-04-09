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

public class RegistrationDataProvider {

    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() throws IOException {
        FileInputStream file = new FileInputStream(
                new File("C:\\Users\\acer\\Downloads\\dataResgister.xlsx")); //doi path theo may
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getLastRowNum();
        Object[][] data = new Object[rowCount][4]; // 4 là số cột trong tệp Excel

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1);
            if (row != null) {
                String name = "";
                String email = "";
                String password = "";
                String expectedResult = "";

                // Kiểm tra các ô dữ liệu trước khi gán giá trị
                Cell cellName = row.getCell(0);
                if (cellName != null && cellName.getCellType() == CellType.STRING) {
                    name = cellName.getStringCellValue().trim(); // Thêm phương thức trim() để loại bỏ các khoảng trắng
                    // ở đầu và cuối chuỗi
                }

                Cell cellEmail = row.getCell(1);
                if (cellEmail != null && cellEmail.getCellType() == CellType.STRING) {
                    email = cellEmail.getStringCellValue().trim(); // Thêm phương thức trim() để loại bỏ các khoảng
                    // trắng ở đầu và cuối chuỗi
                }

                Cell cellPassword = row.getCell(2);
                if (cellPassword != null && cellPassword.getCellType() == CellType.STRING) {
                    password = cellPassword.getStringCellValue().trim(); // Thêm phương thức trim() để loại bỏ các
                    // khoảng trắng ở đầu và cuối chuỗi
                }

                Cell cellExpectedResult = row.getCell(3);
                if (cellExpectedResult != null && cellExpectedResult.getCellType() == CellType.STRING) {
                    expectedResult = cellExpectedResult.getStringCellValue().trim(); // Thêm phương thức trim() để loại
                    // bỏ các khoảng trắng ở đầu và
                    // cuối chuỗi
                }

                // Kiểm tra nếu tất cả các giá trị không rỗng
                if (!name.isEmpty() || !email.isEmpty() || !password.isEmpty() || !expectedResult.isEmpty()) {
                    data[i][0] = name;
                    data[i][1] = email;
                    data[i][2] = password;
                    data[i][3] = expectedResult;
                }
            }
        }

        workbook.close();
        file.close();

        return data;
    }
}
