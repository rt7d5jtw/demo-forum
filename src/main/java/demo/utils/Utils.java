package demo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Enumeration;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

  private static Logger logger = LoggerFactory.getLogger(Utils.class);

  /** Returns template file path as String from classpath:/templates */
  public String getTemplatesFile(String pathToFile) {
    return this.getClass().getClassLoader().getResource("templates/" + pathToFile).getPath();
  }

  /**
  * Returns list of files from the directory path as a set of strings
  */
  public Set<String> fileList(String dir) throws IOException {
    try (Stream<Path> stream = Files.list(Paths.get(dir))) {
      return stream
        .filter(file -> !Files.isDirectory(file))
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toSet());
    }
  }

  /**
  * Returns current working directory 
  */
  public String cwd() {
    String userDir = Paths.get("")
      .toAbsolutePath()
      .toString();
    return userDir;
  }

  /** Returns a File from given String path */
  public File getFileAsIOStream(String filePath) {
    return new File(filePath);
  }

  /** Returns the contents of the file */
  private void printFileContents(InputStream is) throws IOException {
    BufferedReader br = 
      new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    String line;
    while ((line = br.readLine()) != null) {
      System.out.println(line);
    }
    is.close();
  }

  /** Returns the file as a single string */
  public String readFileAsString(File inputFile) {
    try {
      InputStream inputStream = new FileInputStream(inputFile);
      int ch;
      String file = "";
      // while inputStream can read the next byte from input stream, loop
      while ((ch = inputStream.read()) != -1) {
        file = file + (char) ch;
      }

      inputStream.close();
      return file;
    } catch (IOException e) {
      logger.debug("Error at readFileAsString, {}", e.getCause());
      e.printStackTrace();
    }
    return null;
  }
}
