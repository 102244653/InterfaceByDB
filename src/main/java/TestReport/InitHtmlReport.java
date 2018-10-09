package TestReport;

import Utils.DateUtils;

import java.io.*;

public class InitHtmlReport {
    private static final String OUTPUT_FOLDER = "./TestReport/";
    public static String dirc= DateUtils.format(DateUtils.CHECK_LOG_FORMAT);
    private static final String FILE_NAME ="API"+InitExcelReport.ExcelName+".html";
    //报告模板存放地址
    private String templatePath = System.getProperty("user.dir")+"\\src\\main\\resources\\reportdemo";
    //报告存放地址
    private String Reportpath =OUTPUT_FOLDER+"\\"+dirc+"\\"+FILE_NAME;

    public  void CreatHtmlReport(){
        try {
            String template = this.read(templatePath);
            File dir = new File(OUTPUT_FOLDER+"\\"+dirc);
            if (!dir.exists())
            {dir.mkdirs();}
            BufferedWriter output = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(new File(Reportpath)),"UTF-8"));
            template = template.replaceFirst("\\$\\{resultData\\}", ResultData.GetResultData());
            output.write(template);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String read(String path) {
        File file = new File(path);
        InputStream is = null;
        StringBuffer sb = new StringBuffer();
        try {
            is = new FileInputStream(file);
            int index = 0;
            byte[] b = new byte[1024];
            while ((index = is.read(b)) != -1) {
                sb.append(new String(b, 0, index));
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
