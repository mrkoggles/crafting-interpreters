package com.craftinginterpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("Usage: generate_ast <output directory>");
      System.exit(64);
    }

    String outputDir = args[0];
    defineAst(outputDir, "Expr", Arrays.asList(
        "Binary   : Expr left, Token operator, Expr right",
        "Grouping : Expr expression",
        "Literal  : Object value",
        "Unary    : Token operator, Expr right"));
  }

  private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
    var path = outputDir + "/" + baseName + ".java";
    var writer = new PrintWriter(path, "UTF-8");

    writer.println("package com.craftinginterpreters.lox");
    writer.println();
    writer.println("import java.util.List;");
    writer.println();
    writer.println("abstract class " + baseName + "{");

    writer.println("}");
    writer.close();

    for (var type : types) {
      var className = type.split(":")[0].trim();
      var fields = type.split(":")[1].trim();
      defineType(writer, baseName, className, fields);
    }
  }

  private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
    writer.println(" static class " + className + " extends " + baseName + " {");

    // ctor
    writer.println(" " + className + "(" + fieldList + ") {");

    // params
    var fields = fieldList.split(", ");
    for (var field : fields) {
      var name = field.split(" ")[1];
      writer.println("   this." + name + " = " + name + ";");
    }
    writer.println("}");

    // fields
    writer.println();
    for (var field : fields) {
      writer.println("   final " + field + ";");
    }

    writer.println("}");
  }
}
