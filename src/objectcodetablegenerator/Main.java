//8984
package objectcodetablegenerator;

import java.io.*;
import java.util.*;

public class Main {
  public Main() {
    // Empty.
  }

  private static Set<String> m_opNameList = new TreeSet<>();
  private static final byte NopOperator = -112; // 144
  private static int m_count = 0;

  public static void load() { // jz, jnz
    try {      
      String cSharpPath = "C:\\Users\\Stefan\\Documents\\A A C_Compiler_Assembler - A 16 bits 0 CSharp\\C_Compiler_CSharp\\C_Compiler_CSharp\\";
      //String cSharpPath = "C:\\Users\\Stefan\\Documents\\A A C_Compiler_Assembler - A 16 bits 0 CSharp 0 Assembly\\C_Compiler_CSharp\\C_Compiler_CSharp\\";
      PrintStream printStream = new PrintStream(cSharpPath + "ObjectCodeTable.cs");
      printStream.println("using System.Collections.Generic;");
      printStream.println();
      printStream.println("namespace CCompiler {");
      printStream.println("  public class ObjectCodeTable {");
      printStream.println("    public static IDictionary<ObjectCodeInfo, byte[]> MainArrayMap = new SortedDictionary<ObjectCodeInfo, byte[]>(new ObjectCodeComparer()); // ListMapXXX");
      printStream.println();
      printStream.println("    public static void Init() {");
      read(printStream, "C:\\Test\\x");
      printStream.println("    }");
      printStream.println("  }");
      printStream.print("}");
      printStream.close();

      m_opNameList.add("mov_qword");
      m_opNameList.add("cmp_qword");

      m_opNameList.add("add_qword");
      m_opNameList.add("sub_qword");
      m_opNameList.add("mul_qword");
      m_opNameList.add("imul_qword");
      m_opNameList.add("div_qword");
      m_opNameList.add("idiv_qword");
      m_opNameList.add("neg_qword");

      m_opNameList.add("inc_qword");
      m_opNameList.add("dec_qword");

      m_opNameList.add("and_qword");
      m_opNameList.add("or_qword");
      m_opNameList.add("xor_qword");
      m_opNameList.add("not_qword");

      m_opNameList.add("shl_qword");
      m_opNameList.add("shr_qword");

      m_opNameList.add("fld_qword");
      m_opNameList.add("fst_qword");
      m_opNameList.add("fild_qword");
      m_opNameList.add("fist_qword");
      m_opNameList.add("fistp_qword");
      m_opNameList.add("fstp_qword");

      m_opNameList.add("define_value");
      m_opNameList.add("define_address");
      m_opNameList.add("define_zero_sequence");
      m_opNameList.add("set_track_size");
      m_opNameList.add("call");
      m_opNameList.add("empty");
      m_opNameList.add("label");
      m_opNameList.add("comment");
      m_opNameList.add("syscall");
      m_opNameList.add("address_return");

/*      m_opNameList.add("short_je");
      m_opNameList.add("short_jne");
      m_opNameList.add("short_jl");
      m_opNameList.add("short_jle");
      m_opNameList.add("short_jg");
      m_opNameList.add("short_jge");
      m_opNameList.add("short_jb");
      m_opNameList.add("short_jbe");
      m_opNameList.add("short_ja");
      m_opNameList.add("short_jae");
      m_opNameList.add("short_jc");
      m_opNameList.add("short_jnc");
      m_opNameList.add("short_jz");
      m_opNameList.add("short_jnz");
      m_opNameList.add("short_jmp");
      
      m_opNameList.add("long_je");
      m_opNameList.add("long_jne");
      m_opNameList.add("long_jl");
      m_opNameList.add("long_jle");
      m_opNameList.add("long_jg");
      m_opNameList.add("long_jge");
      m_opNameList.add("long_jb");
      m_opNameList.add("long_jbe");
      m_opNameList.add("long_ja");
      m_opNameList.add("long_jae");
      m_opNameList.add("long_jc");
      m_opNameList.add("long_jnc");
      m_opNameList.add("long_jz");
      m_opNameList.add("long_jnz");
      m_opNameList.add("long_jmp");*/
      
      PrintStream operatorStream = new PrintStream(cSharpPath + "AssemblyOperator.cs");
      operatorStream.println("namespace CCompiler {");
      operatorStream.println("  public enum AssemblyOperator {");
      
      boolean first = true;
      for (String opName : m_opNameList) {
        operatorStream.print((first ? "    " : ",\n    ") + opName);
        first = false;
      }
      
      operatorStream.println("\n  };");
      operatorStream.println("};");
      operatorStream.close();

      System.out.println("Count: " + m_count);
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
    }        
  }

  private enum Status {None, Load, Store};

  private static boolean isDigits(String text) {
    for (char c : text.substring(0, text.length() - 1).toCharArray()) {
      if (!Character.isDigit(c)) {
        return false;
      }
    }      

    return true;
  }
  
  private static void read(PrintStream printStream, String pathName) {
    try {
      File file = new File(pathName);
      if (file.isDirectory()) {
        for (String fileName : file.list()) {
          if (fileName.endsWith(".com")) {
            int comIndex = fileName.lastIndexOf(".com");
            int index3 = fileName.lastIndexOf("_", comIndex - 1);
            int index2 = fileName.lastIndexOf("_", index3 - 1);
            int index1 = fileName.lastIndexOf("_", index2 - 1);

            String operator = fileName.substring(0, index1);
            operator = operator.replace("int", "interrupt");
            m_opNameList.add(operator);
            
            String operand1 = fileName.substring(index1 + 1, index2);
            String operand2 = fileName.substring(index2 + 1, index3);
            String operand3 = fileName.substring(index3 + 1, comIndex);

            operand1 = ((isDigits(operand1) || operand1.equals("null")) ? operand1 : ("Register." + operand1));
            operand2 = ((isDigits(operand2) || operand2.equals("null")) ? operand2 : ("Register." + operand2));
            operand3 = ((isDigits(operand3) || operand3.equals("null")) ? operand3 : ("Register." + operand3));
            
            operand1 = operand1.equals("0") ? "0" : operand1;
            operand2 = operand2.equals("0") ? "0" : operand2;
            operand3 = operand3.equals("0") ? "0" : operand3;
            
            operand1 = operand1.equals("1") ? "1" : operand1;
            operand2 = operand2.equals("1") ? "1" : operand2;
            operand3 = operand3.equals("1") ? "1" : operand3;
            
            operand1 = operand1.equals("256") ? "2" : operand1;
            operand2 = operand2.equals("256") ? "2" : operand2;
            operand3 = operand3.equals("256") ? "2" : operand3;
            
            operand1 = operand1.equals("65536") ? "4" : operand1;
            operand2 = operand2.equals("65536") ? "4" : operand2;
            operand3 = operand3.equals("65536") ? "4" : operand3;
            
            operand1 = operand1.equals("4294967296") ? "8" : operand1;
            operand2 = operand2.equals("4294967296") ? "8" : operand2;
            operand3 = operand3.equals("4294967296") ? "8" : operand3;

            operand1 = operand1.equals("256") ? "2" : operand1;
            operand2 = operand2.equals("256") ? "2" : operand2;
            operand3 = operand3.equals("256") ? "2" : operand3;
            
            operand1 = operand1.equals("65536") ? "4" : operand1;
            operand2 = operand2.equals("65536") ? "4" : operand2;
            operand3 = operand3.equals("65536") ? "4" : operand3;
            
            operand1 = operand1.equals("4294967296") ? "8" : operand1;
            operand2 = operand2.equals("4294967296") ? "8" : operand2;
            operand3 = operand3.equals("4294967296") ? "8" : operand3;

            printStream.print("      MainArrayMap.Add(new ObjectCodeInfo(AssemblyOperator." +
                              operator + ", " + operand1 + ", " + operand2 + ", " + operand3 +
                              "), " + "new byte[]{");

            boolean firstByte = true;
            File filePath = new File(file, fileName);
            FileInputStream comStream = new FileInputStream(filePath);

            while (comStream.available() != 0) {
              byte b = (byte) comStream.read();

              if (b != NopOperator) {
                int i = (int) b;
              
                if (i < 0) {
                  i += 256;
                }

                printStream.print((firstByte ? "" : ", ") + Integer.toString(i));
                firstByte = false;
              }
            }

            ++m_count;
            comStream.close();
            printStream.println("});");
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  public static void main(String args[]) {
    Main.load();
  }
}