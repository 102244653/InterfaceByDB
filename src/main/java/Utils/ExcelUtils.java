package Utils;

import java.io.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    HSSFSheet sheet;
    HSSFCellStyle style0;
    HSSFFont font0;
    //创建excel工作对象
    public ExcelUtils(String path, String sheetname){
        try {
            //创建workbook
            File file = new File(path);
            FileInputStream fs = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            sheet = workbook.getSheet(sheetname);
            style0 = workbook.createCellStyle();
            font0= workbook.createFont();
            logger.info("读取到"+sheetname);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExcelUtils()
    {
    }

    //单元格读取
    public String readFromExcelCell(int rowIndex, int cellIndex){
        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            logger.error("第"+rowIndex+"行不存在");
            return  null;
        }
        HSSFCell cell = row.getCell(cellIndex);
        if (cell == null) {
            logger.error("第"+cellIndex+"列不存在");
            return  null;
        }
        int cellType = cell.getCellType();
        String S;
        switch (cellType)
        {
            default:
            case 1 :
            {
                S=String.valueOf(cell.getStringCellValue());
                logger.info("读取到单元格["+rowIndex+"."+cellIndex+"]的值："+S);
                break;
            }
            case 0 :
            {
                S =String.valueOf(cell.getNumericCellValue());
                logger.info("读取到单元格["+rowIndex+"."+cellIndex+"]的值："+S);
                break;
            }
        }
        return S;
    }

    //整行读取
    public String[] readFromExcelrow(int rowIndex) {
        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            logger.error("第"+rowIndex+"行不存在");
            return  null;
        }
        String[] result=new String[row.getLastCellNum()];
        for(int i=0;i<row.getLastCellNum();i++){
            HSSFCell cell = row.getCell(i);
            int cellType = cell.getCellType();
            switch (cellType) {
                default:
                    result[i]=rowIndex+":"+i+"=error";
                    logger.error("读取到单元格错误");
                    break;
                case 1 :
                {
                    result[i] =String.valueOf(cell.getStringCellValue());
                    logger.info("读取到单元格["+rowIndex+"."+i+"]信息："+result[i]);
                    break;
                }
                case 0 :
                {
                    result[i] =String.valueOf(cell.getNumericCellValue());
                    logger.info("读取到单元格["+rowIndex+"."+i+"]信息："+result[i]);
                    break;
                }
            }
        }
        return result;
    }
    //整列读取
    public String[] readFromExcelcolumn(int columnIndex){
        //行数
        int rowid=getrows();
        String[] result=new String[rowid];
        for (int k=0;k<rowid;k++){
            HSSFRow row = sheet.getRow(k);
            if (row == null) {
                logger.error("第"+k+"行不存在");
                return  null;
            }
            HSSFCell cell = row.getCell(columnIndex);
            if (cell == null) {
                logger.error("第"+columnIndex+"列不存在");
                return  null;
            }
            int cellType = cell.getCellType();
            switch (cellType) {
                default:
                {
                    result[k] = k + ":" + columnIndex + "=error";
                    logger.error("读取到单元格错误");
                    break;
                }
                case 1 :
                {
                    result[k] =String.valueOf(cell.getStringCellValue());
                    logger.info("读取到单元格["+columnIndex+"."+k+"]信息："+result[k]);
                    break;
                }
                case 0 :
                {
                    result[k]=String.valueOf(cell.getNumericCellValue());
                    logger.info("读取到单元格["+columnIndex+"."+k+"]信息："+result[k]);
                    break;
                }
            }
        }
        return result;
    }

    //单元格写入
    public void writeToExcelCell(int rowIndex, int cellIndex, String value) {
        HSSFRow row = sheet.getRow(rowIndex);
        //判断行是否存在
        if (row == null) {
            row = sheet.createRow(rowIndex);
            logger.info("行号不存在，新建行"+rowIndex);
        }
        HSSFCell cell = row.getCell(cellIndex);
        //判断列是否存在
        if (cell == null) {
            cell = row.createCell(cellIndex);
            logger.info("单元格不存在，新建单元格["+rowIndex+"."+cellIndex+"]");
        }
        cell.setCellValue(value);
        logger.info("单元格["+rowIndex+"."+cellIndex+"]成功写入信息："+value);
    }

    //获取为空的行数序号
    public int getNullRow() {
        int nullrow;
        for (int i=1;;i++) {
            String result = readFromExcelCell(i, 1);
            if (result == null) {
                nullrow=i;
                break;
            }
        }
        return nullrow;
    }

    //获取总行数
    public int getrows(){
        int row=sheet.getLastRowNum();
        logger.info("当前页总行数为：" + (row + 1));
        return row;
    }

    //整行写入
    public void writeToExcelRows(String[] value,int h) {
        int rowid=getNullRow();
        //创建行
        HSSFRow row = sheet.createRow(rowid);
        row.setHeightInPoints(h);
        for(int i=0;i<value.length;i++){
            //创建列
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(value[i]);
            cell.setCellStyle(this.mystyle());
            logger.info("单元格["+rowid+"."+i+"]写入值："+value[i]);
        }
    }

    //修改后保存Excel
    public void save (String path){
        try {
            // 首先要创建一个原始Excel文件的输出流对象！
            FileOutputStream excelFileOutPutStream = new FileOutputStream(path);
            // 将最新的 Excel 文件写入到文件输出流中，更新文件信息！
            sheet.getWorkbook().write(excelFileOutPutStream);
            // 执行 flush 操作， 将缓存区内的信息更新到文件上
            excelFileOutPutStream.flush();
            // 使用后，及时关闭这个输出流对象， 好习惯，再强调一遍！
            excelFileOutPutStream.close();
            logger.info(path+"保存成功\n");
        } catch (Exception e){
            logger.info(path + "保存失败\n");
        }
    }

    //创建一个Excel
    public void CreatExcel(String path,String title,String[] value){
        FileOutputStream os=null;
        try {
            // 创建 一个excel文档对象
            HSSFWorkbook workBook = new HSSFWorkbook();
            // 创建一个工作薄对象
            HSSFSheet sheet = workBook.createSheet("Sheet1");
            // 设置列的宽度为
            sheet.setColumnWidth(0,8000);
            sheet.setColumnWidth(1,6000);
            sheet.setColumnWidth(2,8000);
            sheet.setColumnWidth(3,6000);
            sheet.setColumnWidth(4,12000);
            sheet.setColumnWidth(5,8000);
            sheet.setColumnWidth(6,10000);
            sheet.setColumnWidth(7,4000);
            HSSFRow rowtitle=null;
            HSSFRow rowdata;
            //表头信息--整行合并
            if(title.isEmpty()){
                rowdata= sheet.createRow(0);// 创建一个行对象

            }else {
                rowtitle= sheet.createRow(0);//创建表头信息行
                rowdata = sheet.createRow(1);// 创建一个行对象
                rowtitle.setHeightInPoints(30);// 设置行高23像素
            }
            rowdata.setHeightInPoints(30);// 设置行高23像素
            HSSFCellStyle style = workBook.createCellStyle();// 创建样式对象
            style.setWrapText(true);
            // 设置字体
            HSSFFont font = workBook.createFont();// 创建字体对象
            font.setFontHeightInPoints((short) 15);// 设置字体大小
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体
            font.setFontName("宋体");// 设置为黑体字
            style.setFont(font);// 将字体加入到样式对象

            // 设置对齐方式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中

            // 设置边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 顶部边框
            //style.setTopBorderColor(HSSFColor.WHITE.index);// 设置为红色
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 底部边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边边框
            if(!title.isEmpty()){
                CellRangeAddress cra=new CellRangeAddress(0, 0, 0,value.length-1);
                sheet.addMergedRegion(cra);
                HSSFCell cell0 = rowtitle.createCell(0);
                cell0.setCellValue(title);
                cell0.setCellStyle(style);
            }
            // 创建单元格,写入表头内容
            for(int i=0;i<value.length;i++) {
                HSSFCell cell = rowdata.createCell(i);
                cell.setCellValue(value[i]);
                cell.setCellStyle(style);
            }
            os= new FileOutputStream(path);
            workBook.write(os);// 将文档对象写入文件输出流
        }catch (Exception e){
            logger.error("生成EXCEL文件失败");
        }finally {
            try {
                os.close();// 关闭文件输出流
            }catch (Exception e){}
        }
    }

    public HSSFCellStyle  mystyle(){
        font0.setFontHeightInPoints((short) 11);
        style0.setWrapText(true);
        style0.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中
        style0.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        style0.setFont(font0);
        HSSFCellStyle mystyle=style0;
        return  mystyle;
    }
}
