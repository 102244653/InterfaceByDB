package TestReport;

import Utils.DateUtils;
import Utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class InitExcelReport {
    private static Logger logger = LoggerFactory.getLogger(InitExcelReport.class);

    public static String TitleName="API接口测试报告";
    public static String OUTPUT_FOLDER = "./TestExcelReport/";
    public static String dirc= DateUtils.format(DateUtils.CHECK_LOG_FORMAT);
    public static String ExcelName= DateUtils.format(DateUtils.time8);
    public static String ExcelPath=OUTPUT_FOLDER+"\\"+dirc+"\\"+"API"+ExcelName+".xls";
    public static String[] meanu={"TestURL","APIName","CaseName","RequestMethod","RequestBody","ExpectResult","ResponseData","Result",};

    public static void InitExcel(){
        ExcelUtils excel=new ExcelUtils();
        File dir = new File(OUTPUT_FOLDER+"\\"+dirc);
        if (!dir.exists())
        {dir.mkdirs();}
        excel.CreatExcel(ExcelPath,TitleName,meanu);
        logger.info("测试报告路径："+ExcelPath);
    }

    public static void InsertData(String[] data){
        try {
            ExcelUtils work=new ExcelUtils(ExcelPath,"Sheet1");
            work.writeToExcelRows(data,45);
            work.save(ExcelPath);
            Thread.sleep(1000);
        }catch (Exception e){
            logger.info(ExcelPath+"写入数据失败："+data);
        }

    }

    public static void InsertData(String excelpath,String sheetname,String[] data){
        try{
            ExcelUtils work=new ExcelUtils(excelpath,sheetname);
            work.writeToExcelRows(data,45);
            work.save(excelpath);
            Thread.sleep(1000);
        }catch (Exception e){
            logger.info(excelpath+"写入数据失败："+data);
        }

    }
}
