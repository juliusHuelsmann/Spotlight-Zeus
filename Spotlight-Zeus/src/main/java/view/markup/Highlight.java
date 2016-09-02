package view.markup;

import javax.swing.*;
import javax.swing.text.*;

import utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

public class Highlight extends JFrame {
  
  final String str0 [] = new String[] {
      "abstract",  "continue",  "for", "new", "switch",
      "assert", "default", "goto", "package", "synchronized",
      "boolean", "do",  "if",   "private",  "this",
      "break", "double",  "implements",  "protected", "throw",
      "byte",  "else",  "import",  "public",  "throws",
      "case",  "enum",  "instanceof",  "return",  "transient",
      "catch", "extends", "int", "short", "try",
      "char",  "final", "interface", "static",  "void",
      "class", "finally", "long",  "strictfp",  "volatile",
      "const",  "float", "native",  "super", "while",
      "int", "boolean", "double", "float", "char", "null"};

  private final JScrollPane jsp;
  private final Style styleHighlight, styleNormal, styleComment1, styleComment2;
  private final JTextPane pane;
  private final DefaultStyledDocument doc;
  
  private final JButton jbtnSave;
  private final JButton jbtnCompile;
  private final JLabel jlblErrors;
  
  private String className;
  public Highlight() {
    

    super("Editor");
    super.setLayout(null);

    jbtnSave = new JButton("save");
    jbtnSave.setBounds(0, 0, 100, 20);
    super.add(jbtnSave);

    jbtnCompile = new JButton("compile");
    jbtnCompile.setBounds(100, 0, 100, 20);
    super.add(jbtnCompile);
    
    jlblErrors = new JLabel();
    jlblErrors.setOpaque(true);
    jlblErrors.setBackground(Color.green);
    jlblErrors.setBounds(200, 0, 50, 20);
    super.add(jlblErrors);
    
    
    // Create the StyleContext, the document and the pane
    StyleContext sc = new StyleContext();
    doc = new DefaultStyledDocument(sc);
    pane = new JTextPane(doc);
    pane.addKeyListener(new KeyListener() {
      
      public void keyTyped(KeyEvent e) {
        System.out.println("kt");
      }
      
      public void keyReleased(KeyEvent e) {

        if (e.getKeyChar() == '\n') {
              int cp = pane.getCaretPosition() - 1;
              final String first = pane.getText().substring(0, cp);
              final String second = pane.getText().substring(cp + 1, pane.getText().length());
              int lindex = 1 + first.lastIndexOf("\n");
              String d = pane.getText().substring(lindex, cp);
              String prefix = "";
              for (int i = 0; i < d.length(); i++) {
                if (d.charAt(i) == ' ') {
                  prefix += " ";
                } else if (d.charAt(i) == '\t') {
                  prefix += "\t";
                } else {
                  break;
                }
              }
              try {
                doc.remove(0, doc.getLength());
                doc.insertString(0, first + "\n" + prefix + second , styleNormal);
                pane.setCaretPosition(cp + prefix.length() + 1);
              } catch (BadLocationException e1) {
                e1.printStackTrace();
              }
          
        } else if (e.getKeyChar() == '(') {

          try {
            int cp = pane.getCaretPosition();
            final String prefix = pane.getText().substring(0, cp);
            final String postfix = pane.getText().substring(cp, pane.getText().length());
            final String infix = ")";
            doc.remove(0, doc.getLength());
            doc.insertString(0, prefix + infix + postfix , styleNormal);
            pane.setCaretPosition(cp);
          } catch (BadLocationException e1) {
            e1.printStackTrace();
          }
          
        } else if (e.getKeyChar() == '{') {

          try {
            int cp = pane.getCaretPosition();
            final String prefix = pane.getText().substring(0, cp);
            final String postfix = pane.getText().substring(cp, pane.getText().length());
            final String infix = "}";
            doc.remove(0, doc.getLength());
            doc.insertString(0, prefix + infix + postfix , styleNormal);
            pane.setCaretPosition(cp);
          } catch (BadLocationException e1) {
            e1.printStackTrace();
          }
          
        } else if ('"' == (e.getKeyChar())) {

          
          try {
            int cp = pane.getCaretPosition();
            final String prefix = pane.getText().substring(0, cp);
            final String postfix = pane.getText().substring(cp, pane.getText().length());
            final String infix = "\"";
            doc.remove(0, doc.getLength());
            doc.insertString(0, prefix + infix + postfix , styleNormal);
            pane.setCaretPosition(cp );
          } catch (BadLocationException e1) {
            e1.printStackTrace();
          }
          
        } else if (e.getKeyChar() == '\'') {

          try {
            int cp = pane.getCaretPosition();
            final String prefix = pane.getText().substring(0, cp);
            final String postfix = pane.getText().substring(cp, pane.getText().length());
            final String infix = "'";
            doc.remove(0, doc.getLength());
            doc.insertString(0, prefix + infix + postfix , styleNormal);
            pane.setCaretPosition(cp);
          } catch (BadLocationException e1) {
            e1.printStackTrace();
          }
          
        } 

        System.out.println(e.getKeyChar());
        wordFinished();        
      }
      
      public void keyPressed(KeyEvent e) {
        System.out.println("kp");
        // TODO Auto-generated method stub
        
      }
    });
    
    // set tab size (2 characters for now)
    final int anz = 40;
    TabStop[] tabstops = new TabStop[anz];
    for (int i = 0; i < anz; i++) {
      tabstops[i] = new TabStop(16 * i);
    }
    TabSet tabs = new TabSet(tabstops);
    AttributeSet paraSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabs);
    pane.setParagraphAttributes(paraSet, false);
    pane.setMargin(new Insets(0, 15, 0 , 0));
    
    
    // Create and add the style
    styleHighlight = sc.addStyle("highlight", null);
    styleHighlight.addAttribute(StyleConstants.Foreground, new Color(150, 100, 50));
    styleHighlight.addAttribute(StyleConstants.FontSize, new Integer(13));
    styleHighlight.addAttribute(StyleConstants.FontFamily, "Courier new");
    styleHighlight.addAttribute(StyleConstants.Bold, new Boolean(true));
    // Create and add the style
    styleNormal = sc.addStyle("normal", null);
    styleNormal.addAttribute(StyleConstants.Foreground, new Color(0, 0, 0));
    styleNormal.addAttribute(StyleConstants.FontSize, new Integer(13));
    styleNormal.addAttribute(StyleConstants.FontFamily, "Courier new");
    styleNormal.addAttribute(StyleConstants.Bold, new Boolean(false));
    // Create and add the style
    styleComment1 = sc.addStyle("c1", null);
    styleComment1.addAttribute(StyleConstants.Foreground, new Color(50, 200, 50));
    styleComment1.addAttribute(StyleConstants.FontSize, new Integer(13));
    styleComment1.addAttribute(StyleConstants.FontFamily, "Courier new");
    styleComment1.addAttribute(StyleConstants.Bold, new Boolean(false));
    // Create and add the style
    styleComment2 = sc.addStyle("c2", null);
    styleComment2.addAttribute(StyleConstants.Foreground, new Color(50, 20, 150));
    styleComment2.addAttribute(StyleConstants.FontSize, new Integer(13));
    styleComment2.addAttribute(StyleConstants.FontFamily, "Courier new");
    styleComment2.addAttribute(StyleConstants.Bold, new Boolean(false));


    jsp = new JScrollPane(pane);
    
    super.add(jsp);
    this.setSize(700, 500);
    super.setLocationRelativeTo(null);
    super.setVisible(true);

    try {
      doc.insertString(0, readSampleFile(), styleNormal);
      wordFinished();
    } catch (BadLocationException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    

  
  
  }
  
  private boolean isHighlighted(final String s) {
    for (String s2 : str0) {
      if (s2.equals(s)) {
        return true;
      }
    }
    return false;
  }
  
  
  /**
   * 0) initialize git repository.
   * 
   * 1) a)  Download the .java files that are stored inside the Athene 
   *        Repository at github into the new created git repository.
   *    b)  Alter the Serialize .java file 
   *    c)  Move the Serialize.java file and store the user-created java
   *        file at the same location.
   * 
   * 2) Call the compilefrom git jar file
   */
  public void storeData() {

    // 0) initialize git repository
    final String serialPath = System.getProperty("user.home") + "/.Spotlight/serial/";
    final String gitRepopath = System.getProperty("user.home") + "/.Spotlight/temporary/gitRepository/";
    new File(gitRepopath).mkdirs();
    new File(serialPath).mkdirs();
    Utils.executeCommandLinux("git init " + gitRepopath);

    // 1) download files 
    
    final String prefix = "https://raw.githubusercontent.com/juliusHuelsmann/"
        + "Spotlight-Athene/master/spotlight-athene/src/main/java/spotlight/"
        + "athene/view/dictionaries/";
    final String[] contents = new String[] {
        "Dictionaries.java", "VDictionary.java", "VDictionaryLocal.java",
        "VDictionaryWeb.java", "ViewDictSettings.java"   
    };
    final String pathinfix = "src/main/java/";
    new File(gitRepopath + pathinfix).mkdirs();
    for (int j = 0; j < contents.length; j++) {
      final String resultingPath = gitRepopath + pathinfix + contents[j];
      try {
        saveUrl(resultingPath, prefix + contents[j]);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      
      // remove package declaration
      String content = readFile(resultingPath);
      final int pind = content.indexOf("package");
      content = content.substring(pind, content.length());
      final int nind = content.indexOf("\n");
      content = content.substring(nind, content.length());
      writeToFile(resultingPath, content);
    }
    

    // remove package description in files
    
    
    //
    // 2) Download
    
    //https://raw.githubusercontent.com/juliusHuelsmann/Spotlight-Athene/master
    ///spotlight-athene/src/main/java/spotlight/athene/view/dictionaries/VDictionaryWeb.java

    // load the file
    final String outputFileLocation = serialPath + getClassName() + ".serialized";
    String s = readFileFromJar("/res/Serialize");
    s = s.replaceAll("\\[FILE_LOCATION\\]",  outputFileLocation);
    s = s.replaceAll("\\[JAR_CLASS_NAME\\]", getClassName());
    writeToFile(gitRepopath + pathinfix + "Start.java", s);
    

    writeToFile(gitRepopath + pathinfix + getClassName() + ".java", pane.getText());
    
    
    // commit
    final String scriptPath = gitRepopath + "commitScript";
    System.out.println(scriptPath);
    Utils.generateExecutableScript(scriptPath, "#!/bin/bash\ncd " 
        + scriptPath + "\ngit add .\n"
        + "git commit -am \".\"");
    Utils.executeCommandLinux(scriptPath);
    
    // compile
    final String namePostfix = "Serial";
    final String namePath = System.getProperty("user.home")  + "/.Spotlight/";
    final String nameJarFile = namePath + namePostfix + ".jar";
    
    final String totalcommand = System.getProperty("user.home") 
        + "/.CompileFromGithub " + namePostfix + " " + gitRepopath 
        + " src/main/java/ master Start " + namePath;
    System.out.println(totalcommand);
    Utils.executeCommandLinux(totalcommand);
    //need the following files:
    // Dictionaries (all dependencies)
    // the jar file for serializing the new created java file
    // the new created java file
    
    Utils.executeCommandLinux("java -jar " + nameJarFile);
    //TODO: copy the result to the given folder
    
    
  }
  
  public void saveUrl(final String filename, final String urlString)
      throws MalformedURLException, IOException {
  BufferedInputStream in = null;
  FileOutputStream fout = null;
  try {
      in = new BufferedInputStream(new URL(urlString).openStream());
      fout = new FileOutputStream(filename);

      final byte data[] = new byte[1024];
      int count;
      while ((count = in.read(data, 0, 1024)) != -1) {
          fout.write(data, 0, count);
      }
  } finally {
      if (in != null) {
          in.close();
      }
      if (fout != null) {
          fout.close();
      }
  }
}

  /**
   * Called whenever a new word has been inserted.
   */
  public void wordFinished() {

    try {
      
      doc.setCharacterAttributes(0, doc.getLength(), styleNormal, false);
      final String text = doc.getText(0, doc.getLength());
      Strint[] wordsplit = characterSplit(text);
      String[] linesplit = doc.getText(0, doc.getLength()).split("\n", -1);
      
      int cumulativeStart = 0;
      boolean classFound = false;
      for (int i = 0; i < wordsplit.length; i++) {
        // split at \t, "\n", ",", ";", "(", ")", "{", "}", "[", "]", too
        
        
        if (isHighlighted(wordsplit[i].getContent())) {
          if (!classFound && wordsplit[i].getContent().equals("class")) {
          
            for (int j = i + 1; j < linesplit.length; j++) {
              if (wordsplit[j].getContent().length() >= 1) {
                
                setClassName(wordsplit[j].getContent());
                classFound = true;
                break;
              }
            }
          }
          
          doc.setCharacterAttributes(cumulativeStart,
              wordsplit[i].getContent().length(), styleHighlight, false);
        }
        
        cumulativeStart += wordsplit[i].getContent().length() + 1;
//        System.out.println(text.substring(cumulativeStart, text.length()));
      }
      cumulativeStart = 0;
      for (int i = 0; i < linesplit.length; i++) {
        final int occurrence = linesplit[i].indexOf("//");
        if (occurrence != -1) {

          doc.setCharacterAttributes(cumulativeStart,
              linesplit[i].length(), styleComment1, false);
        }
        cumulativeStart += linesplit[i].length() + 1;
      }
      
      //
      // Comment multiline 1
      String txtCopy = text;
      cumulativeStart = 0;
      for (int oc1 = txtCopy.indexOf("/*"); 
          oc1 != -1; oc1 = txtCopy.indexOf("/*")) {
        
        int oc2 = txtCopy.indexOf("*/"); 
        if (oc1 != -1 && oc2 != -1) {


          doc.setCharacterAttributes(cumulativeStart + oc1,
              oc2 + 2 - oc1, styleComment1, false);
          

          txtCopy = txtCopy.substring(oc2 + 2, txtCopy.length());
          cumulativeStart += oc2 + 2;
          
        } else if (oc1 != -1 && oc2 == -1){

          doc.setCharacterAttributes(cumulativeStart + oc1,
              txtCopy.length(), styleComment1, false);
          oc1 = -1;
          return;
        }
      }
      //
      // Comment multiline 2
      txtCopy = text;
      cumulativeStart = 0;
      for (int oc1 = txtCopy.indexOf("/**"); 
          oc1 != -1; oc1 = txtCopy.indexOf("/**")) {
        
        int oc2 = txtCopy.indexOf("*/"); 
        if (oc1 != -1 && oc2 != -1) {


          doc.setCharacterAttributes(cumulativeStart + oc1,
              oc2 + 2 - oc1, styleComment2, false);
          

          txtCopy = txtCopy.substring(oc2 + 2, txtCopy.length());
          cumulativeStart += oc2 + 2;
          
        } else if (oc1 != -1 && oc2 == -1){

          doc.setCharacterAttributes(cumulativeStart + oc1,
              txtCopy.length(), styleComment1, false);
          oc1 = -1;
          return;
        }
      }

      doc.setCharacterAttributes(0,
         0, styleNormal, false);
      
    } catch (BadLocationException e) {
    }
  }
  
  
  private final static String[] splitList = new String[] {" " , 
      "\t", "\n", ",", ";", "\\(", "\\)", "\\{", "\\}", "\\[", "\\]"};
  
  private Vector<Strint> characterSplit(
      final Vector<Strint> curr, final int depth) {
    
    //
    // interrupt the recursion 
    if (depth >= splitList.length) {
      return curr;
    }
    
    Vector<Strint> deeper = new Vector<Strint>();
    for (Strint s: curr) {

      String[] d;
      try {
       d = s.getContent().split(splitList[depth], -1);
      } catch (PatternSyntaxException e) {
        d = null;
      }
      for (int k = 0; k < d.length; k++) {
        String s2 = d[k];
        int dep = s.getLen();
        if (k != 0) {
          dep = splitList[depth].length();
        }
        deeper.addElement(new Strint(dep, s2));
      }
    }
    
    return characterSplit(deeper, depth + 1);
  }
  
  private Strint[] characterSplit(final String xtext) {
    final Vector<Strint> start = new Vector<Strint>();
    start.addElement(new Strint(0, xtext));
    final Vector<Strint> result = characterSplit(start, 0);
    final Strint[] res = new Strint[result.size()];
    for (int i = 0; i < res.length; i++) {
      res[i] = result.get(i);
    }
    
      
    return res;
    }

  
  public void setSize(final int width, final int height) {
    
    super.setSize(width, height);
    jsp.setSize(width, height - 20);
    jsp.setLocation(0, 20);
  }
  public static String readSampleFile() {
    return readFileFromJar("/res/SampleFile");
  }

  public static String readFileFromJar(final String path) {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(
        Highlight.class.getResourceAsStream(path)))) {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
  
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        return sb.toString();
    } catch (IOException e) {
      return "";
    }
  }
  public static String readFile(final String path) {
    try(BufferedReader br = new BufferedReader(new FileReader(path))) {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
  
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        return sb.toString();
    } catch (IOException e) {
      return "";
    }
  }
  
  public static void writeToFile(final String path, final String content) {
    Writer writer = null;

    try {
        writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(path), "utf-8"));
        writer.write(content);
    } catch (IOException ex) {
      // report
    } finally {
       try {writer.close();} catch (Exception ex) {/*ignore*/}
    }
  }
  
  public static void main(String[] args) {

    new Highlight().storeData();;
  }

  /**
   * @return the className
   */
  public String getClassName() {
    return className;
  }

  /**
   * @param className the className to set
   */
  public void setClassName(String className) {
    setTitle("Edit class [" + className + "]");
    this.className = className;
  }


}

/**
 * Mixture of string and int
 * @author juli
 *
 */
class Strint {
  
  private String content;
  private int len = 0;
  public Strint(final int xd, final String xs) {
    this.len = xd;
    this.content = xs;
  }
  /**
   * @return the content
   */
  public String getContent() {
    return content;
  }
  /**
   * @param content the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }
  /**
   * @return the len
   */
  public int getLen() {
    return len;
  }
  /**
   * @param len the len to set
   */
  public void setLen(int len) {
    this.len = len;
  }
  
  
}

           
         