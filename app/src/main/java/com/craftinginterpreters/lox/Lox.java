package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      System.out.println("Usage: jlox [script]");
    } else if (args.length == 1) {
      runFile(args[0]);
    } else {
      runPrompt();
    }
  }

  private static void runPrompt() throws IOException {
    var input = new InputStreamReader(System.in);
    var reader = new BufferedReader(input);

    for (;;) {
      System.out.print("> ");
      var line = reader.readLine();
      if (line == null)
        break;
      run(line);
    }
  }

  private static void runFile(String path) throws IOException {
    var bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));
  }

  private static void run(String source) {
    var scanner = new com.craftinginterpreters.lox.Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    for (var token : tokens) {
      System.out.println(token);
    }
  }
}
