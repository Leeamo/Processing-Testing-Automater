import java.io.File;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.filechooser.FileSystemView;

import java.util.Set;

//Call xlsxOut method to generate xlsx spreadsheet
//Requires apache poi package
class xlsxTest {
    public void xlsxOut(ArrayList<StudentDetails> sutdents) {
        // Building workbook/spreadsheet objects
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Test Results");
        XSSFRow row;

        // Dummy data to be put into the spreadsheet, this will be replaced with the
        // testing output data
        Integer i = 1;
        Map<String, Object[]> studentData = new TreeMap<String, Object[]>();
        studentData.put("1", new Object[] { "SID", "Name", "AssignmentName", "Mark" });
        for (StudentDetails student : sutdents) {
            i++;
            studentData.put(i.toString(),
                    new Object[] { student.sID, student.name, student.assignmentName, student.mark.toString() });

        }

        Set<String> keyid = studentData.keySet();

        int rowid = 0;

        // Spreadsheet cell style example
        final XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);

        // Loop through spreadsheet cells to form a table
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = studentData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
                cell.setCellStyle(cellStyle);
            }
        }

        // Create an xlsx file and write the workbook to it
        try {
            FileOutputStream out = new FileOutputStream(
                    new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/"
                            + "studentsResults.xlsx"));
            workbook.write(out);
            out.close();
            workbook.close();
            System.out.println("System: Spreadsheet of students results finished, check documents directory!");
        } catch (Exception e) {
            System.out.println("System: Error creating spreadsheet!");
        }
    }
}