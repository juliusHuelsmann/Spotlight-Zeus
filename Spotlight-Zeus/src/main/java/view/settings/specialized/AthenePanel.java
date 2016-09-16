package view.settings.specialized;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AthenePanel extends JPanel {
  
  
  
  private JButton jbtnAddNewJava, jbtnReload;
  private JTable jtbScripts;
  private DefaultTableModel model;

  
  
  
  // Stuff that is to be extracted to the controller packages
  public static final Vector<DictionarySettingsItem> readDictionaries() {
    final Vector<DictionarySettingsItem> vdsi = new Vector<DictionarySettingsItem>();
    File root = new File(System.getProperty("user.home") + "/.Spotlight/serial");
    for (File f : root.listFiles()) {
      System.out.println(f);
      vdsi.add(new DictionarySettingsItem(f.getAbsolutePath()));
    }
    
    return vdsi;
  }
  
  
  public AthenePanel(final ActionListener ali) {
    
    super();
    super.setSize(300, 300);
    super.setOpaque(false);
    super.setLayout(null);
    
    final int defwidth = 150;
    final int defheight = 20;

    jbtnAddNewJava = new JButton("Create new java");
    jbtnAddNewJava.setOpaque(false);
    jbtnAddNewJava.setBounds(5, 5, defwidth, defheight);
    jbtnAddNewJava.addActionListener(ali);
    super.add(jbtnAddNewJava);

    jbtnReload = new JButton("Reload");
    jbtnReload.setOpaque(false);
    jbtnReload.setBounds(5 + jbtnAddNewJava.getWidth()
        + jbtnAddNewJava.getX(), 5, defwidth, defheight);
    jbtnReload.addActionListener(ali);
    super.add(jbtnReload);

    model  = new DefaultTableModel(); 
    jtbScripts = new JTable(model);
    jtbScripts.setOpaque(false);
    jtbScripts.setLocation(jbtnAddNewJava.getX(), jbtnAddNewJava.getY() +jbtnAddNewJava.getHeight() + 5);
    jtbScripts.setSize(getWidth(), getHeight()-getY());
    super.add(jtbScripts);
    model.addColumn("Name"); 
    model.addColumn("Kind"); 
    model.addColumn("Path"); 
    
    Vector<DictionarySettingsItem> vdsi = readDictionaries();
    refresh(vdsi);
    
    //active
    
    //all
    
    
    //create new
    // edit old
  }
  
  
  /**
   * Reloads the entries of the JTable from the Vector containing Dictionary
   * SettingsItems.
   */
  public final void refresh(final Vector<DictionarySettingsItem> vdsi) {
    for (DictionarySettingsItem dsi : vdsi) {

      model.addRow(new Object[]{dsi.getName(), dsi.getKind(), dsi.getPath()});
    }
    // Create a couple of columns 

    // Append a row 
  }

  public static void main(String[] args) {

    JFrame jf = new JFrame();
    jf.setVisible(true);
    jf.setResizable(false);
    jf.setSize(400, 400);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    jf.add(new AthenePanel(null));
    //().storeData();;
  }


  /**
   * @return the jbtnAddNewJava
   */
  public JButton getJbtnAddNewJava() {
    return jbtnAddNewJava;
  }


  /**
   * @param jbtnAddNewJava the jbtnAddNewJava to set
   */
  public void setJbtnAddNewJava(JButton jbtnAddNewJava) {
    this.jbtnAddNewJava = jbtnAddNewJava;
  }


  /**
   * @return the jbtnReload
   */
  public JButton getJbtnReload() {
    return jbtnReload;
  }


  /**
   * @param jbtnReload the jbtnReload to set
   */
  public void setJbtnReload(JButton jbtnReload) {
    this.jbtnReload = jbtnReload;
  }
}

// stuff that is to be extracted to the model package
class DictionarySettingsItem {
  private String path;
  private String kind;
  
  
  public DictionarySettingsItem(final String xpath) {
    this.path = xpath;
  }
  
  public String getName() {
    final int beginIndex = path.lastIndexOf("/") + 1;
    final int lastIndex = path.lastIndexOf(".");
    return this.path.substring(beginIndex, lastIndex);
  }
  public final String getPath() {
    return this.path;
  }
  
  public final String getKind() {
    return this.kind;
  }
}
