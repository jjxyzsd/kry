package com.test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MysqlEntityUtil {
  private static final MysqlEntityUtil INSTANCE = new MysqlEntityUtil();
 
  private String tableName;// 表名
  private String comment;// 表注释
  private String[] colNames; // 列名数组
  private String[] colTypes; // 列名类型数组
  private String[] colComment; // 列名注释数组
  private int[] colSizes; // 列名大小数组
  
  private boolean needUtil = false; // 是否需要导入包java.util.*
  private boolean needSql = false; // 是否需要导入包java.sql.*
  
  private static final String SQL = "select * from ";// 数据表操作
  private static final String COL = "show full columns from ";// 数据表的字段信息
  private static final String SCH = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='kry'";// 数据中的表注释
  
  //需要修改的数据
  private static final String URL = "jdbc:mysql://127.0.0.1:3306/kry";// 需要连接的数据库
  private static final String NAME = "root";// 连接用户名
  private static final String PASS = "123";// 连接密码
  private static final String DRIVER = "com.mysql.jdbc.Driver";
  private String packageOutPath = "com.demo.pojo";// 指定实体生成所在包的路径

  /**
   * 类的构造方法
   */
  private MysqlEntityUtil() {
    
  }
 
  /**
   * 生成class的所有内容
   */
  private String parse() {
    StringBuffer sb = new StringBuffer();
    sb.append("package " + packageOutPath + ";\r\n");
    sb.append("\r\n");
    // 判断是否导入工具包
    if (needUtil) {
      sb.append("import java.util.Date;\r\n");
    }
    if (needSql) {
      sb.append("import java.sql.*;\r\n");
    }
    // 注释部分
    sb.append("/**\r\n");
    sb.append(" * " + comment + "(" + tableName + ")\r\n");
    sb.append(" */ \r\n");
    // 实体部分
    sb.append("public class " + getTransStr(tableName, true) + "{\r\n\r\n");
    processAllAttrs(sb);//属性
    sb.append("\r\n");
    processAllMethod(sb);// get()、set()方法
    processToString(sb);//toString()方法
    sb.append("}\r\n");
    return sb.toString();
  }
 
  /**
   * 生成所有成员变量
   */
  private void processAllAttrs(StringBuffer sb) {
    for (int i = 0; i < colNames.length; i++) {
      sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + getTransStr(colNames[i], false) + ";//"+colComment[i]+"\r\n");
    }
  }
 
  /**
   * 重写toString()方法
   */
  private void processToString(StringBuffer sb) {
    sb.append("\tpublic String toString() {\r\n");
    sb.append("\t\treturn \"" +tableName + "{\" + \r\n");
    for (int i = 0; i < colNames.length; i++) {
      if (i != 0)
        sb.append("\t\t\t\", ");
      if (i == 0)
        sb.append("\t\t\t\"");
      sb.append(colNames[i] + "=\" + "
          + colNames[i]).append(" + \r\n");
      if (i == colNames.length - 1) {
        sb.append("\t\t\t\"}\";\r\n");
      }
    }
    sb.append("\t}\r\n");
  }

  /**
   * 生成所有get/set方法
   */
  private void processAllMethod(StringBuffer sb) {
    for (int i = 0; i < colNames.length; i++) {
      sb.append("\tpublic void set" + getTransStr(colNames[i], true) + "(" + sqlType2JavaType(colTypes[i]) + " "
          + getTransStr(colNames[i], false) + "){\r\n");
      sb.append("\t\tthis." + getTransStr(colNames[i], false) + "=" + getTransStr(colNames[i], false) + ";\r\n");
      sb.append("\t}\r\n");
      sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + getTransStr(colNames[i], true) + "(){\r\n");
      sb.append("\t\treturn " + getTransStr(colNames[i], false) + ";\r\n");
      sb.append("\t}\r\n");
    }
  }
 
  /**
   * 将传入字符串的首字母转成大写
   */
  private String initCap(String str) {
    char[] ch = str.toCharArray();
    if (ch[0] >= 'a' && ch[0] <= 'z')
      ch[0] = (char) (ch[0] - 32);
    return new String(ch);
  }
 
  /**
   * 将mysql中表名和字段名转换成驼峰形式
   */
  private String getTransStr(String before, boolean firstChar2Upper) {
    //不带"_"的字符串,则直接首字母大写后返回
    if (!before.contains("_"))
      return firstChar2Upper ? initCap(before) : before;
    String[] strs = before.split("_");
    StringBuffer after = null;
    if (firstChar2Upper) {
      after = new StringBuffer(initCap(strs[0]));
    } else {
      after = new StringBuffer(strs[0]);
    }
    if (strs.length > 1) {
      for (int i=1; i<strs.length; i++)
        after.append(initCap(strs[i]));
    }
    return after.toString();
  }
 
  /**
   * 查找sql字段类型所对应的Java类型
   */
  private String sqlType2JavaType(String sqlType) {
    if (sqlType.equalsIgnoreCase("bit")) {
      return "boolean";
    } else if (sqlType.equalsIgnoreCase("tinyint")) {
      return "int";//return "byte";
    } else if (sqlType.equalsIgnoreCase("smallint")) {
      return "short";
    } else if (sqlType.equalsIgnoreCase("int")) {
      return "int";
    } else if (sqlType.equalsIgnoreCase("bigint")) {
      return "long";
    } else if (sqlType.equalsIgnoreCase("float")) {
      return "float";
    } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
        || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
        || sqlType.equalsIgnoreCase("smallmoney")) {
      return "double";
    } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
        || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
        || sqlType.equalsIgnoreCase("text")) {
      return "String";
    } else if (sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("datetime")) {
      return "String";//return "Date";
    } else if (sqlType.equalsIgnoreCase("image")) {
      return "Blod";
    }
    return null;
  }
 
  /**
   * 生成方法
   */
  private void generate() throws Exception {
    //与数据库的连接
    Connection con;
    PreparedStatement pst = null;
    ResultSetMetaData rsmd = null;
    Class.forName(DRIVER);
    con = DriverManager.getConnection(URL, NAME, PASS);
    System.out.println("connect database success ! please wait...");
    
    Properties props = new Properties();
    props.setProperty("remarks", "true"); //设置为true可以获取remarks信息 
    props.setProperty("useInformationSchema", "true");//设置为true可以获取tables remarks信息
    
    List<String> tableNames = new ArrayList<String>();
    List<String> tableComments = new ArrayList<String>();
/*    //获取数据库的元数据
    DatabaseMetaData db = con.getMetaData();
    //从元数据中获取到所有的表名
    ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });*/
    pst = con.prepareStatement(SCH);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      tableNames.add(rs.getString("TABLE_NAME"));
      tableComments.add(rs.getString("TABLE_COMMENT"));//
    }

    String tableCol;
    String tableSql;
    PrintWriter pw = null;
    for (int j = 0; j < tableNames.size(); j++) {
      tableName = tableNames.get(j);//表名
      comment = tableComments.get(j);//表注释
      tableSql = SQL + tableName;
      pst = con.prepareStatement(tableSql);
      rsmd = pst.getMetaData();
      int size = rsmd.getColumnCount();
      colNames = new String[size];
      colTypes = new String[size];
      colComment = new String[size];
      colSizes = new int[size];
      tableCol = COL + tableName;
      //获取字段备注信息
      ResultSet colRs = pst.executeQuery(tableCol);
      int i = 0;
      while (colRs.next()) {
        colComment[i] = colRs.getString("Comment");
        i++;
      }
      //获取所需的信息
      for (i = 0; i < size; i++) {
        colNames[i] = rsmd.getColumnName(i + 1);
        colTypes[i] = rsmd.getColumnTypeName(i + 1);
        if (colTypes[i].equalsIgnoreCase("datetime") || colTypes[i].equalsIgnoreCase("date"))
          needUtil = true;
        if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text"))
          needSql = true;
        colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
      }
      //解析生成class的所有内容
      String content = parse();
      //输出生成文件
      File directory = new File("");
      String dirName = directory.getAbsolutePath() + "/src/main/java/" + packageOutPath.replace(".", "/");
      File dir = new File(dirName);
      if (!dir.exists() && dir.mkdirs()) System.out.println("generate dir 【" + dirName + "】");
      String javaPath = dirName + "/" + getTransStr(tableName, true) + ".java";
      FileWriter fw = new FileWriter(javaPath);
      pw = new PrintWriter(fw);
      pw.println(content);
      pw.flush();
      System.out.println("create class 【" + tableName + "】");
    }
    if (pw != null)
      pw.close();
  }
 
  /**
   * 执行方法
   */
  public static void main(String[] args) {
    try {
      INSTANCE.generate();
      System.out.println("generate classes success!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}