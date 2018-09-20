package TestReport;

import Utils.DateUtils;
import Utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitExcelReport {
    private static Logger logger = LoggerFactory.getLogger(InitExcelReport.class);

    public static String TitleName="接口测试报告";
    public static long ExcelName= DateUtils.time();
    public static String ExcelPath="./TestExcelReport/"+ExcelName+".xls";
    public static String[] meanu={"ID","CaseName","ApiName","TestURL","RequestData","ExpectResult","ResponseData","Result"};

    public void InitExcel(){
        ExcelUtils excel=new ExcelUtils();
        excel.CreatExcel(ExcelPath,TitleName,meanu);
        logger.info("测试报告路径："+ExcelPath);
    }

    public void InsertData(String[] data){
        ExcelUtils work=new ExcelUtils(ExcelPath,"Sheet1");
        work.writeToExcelRows(data,45);
        work.save(ExcelPath);
    }

    public void InsertData(String excelpath,String sheetname,String[] data){
        ExcelUtils work=new ExcelUtils(excelpath,sheetname);
        work.writeToExcelRows(data,45);
        work.save(excelpath);
    }
}
