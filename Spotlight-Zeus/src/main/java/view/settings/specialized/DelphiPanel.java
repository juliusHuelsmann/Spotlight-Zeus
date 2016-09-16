package view.settings.specialized;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DelphiPanel extends JPanel {

  
  private final static String tttStart = "Enable or disable Methods for"
      + "word-lookup:\n\n";
  
  /**
   * The text that is displayed while hovering {@link #jcbLongClick}.
   */
  private final static String tttLongClick = tttStart + "LongClick: \n"
      + "Press mouse longer than usually "
      + "while \nhovering the word that is to be detected";

  /**
   * The text that is displayed while hovering {@link #jcbLongClick}.
   */
  private final static String tttShiftDown = tttStart + "ShiftDown: \n"
      + "Select text and hit shift for"
      + " word-lookup.";
  
  
  private JTextArea jtaContent;
  /**
   * 
   */
  private JCheckBox jcbLongClick, jcbShiftDown;
  public DelphiPanel(final ActionListener ali) {
    
    super();
    super.setLayout(null);
    super.setSize(300, 200);
    super.setOpaque(false);
    
    jtaContent = new JTextArea();
    jtaContent.setBounds(5, 0, getWidth()  - 10, getHeight() / 2);
    jtaContent.setOpaque(false);;
    jtaContent.setEditable(false);
    jtaContent.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    super.add(jtaContent);

    jcbLongClick = new XCheckBox("Long-Click", jtaContent);
    jcbLongClick.setOpaque(false);
    jcbLongClick.setSelected(true);
    jcbLongClick.setToolTipText(tttLongClick);
    jcbLongClick.setSize(150, 20);
    jcbLongClick.setLocation(5, getHeight() / 2 + 5);
    super.add(jcbLongClick);
    jcbShiftDown = new XCheckBox("Shift-Down", jtaContent);
    jcbShiftDown.setOpaque(false);
    jcbShiftDown.setSelected(true);
    jcbShiftDown.setToolTipText(tttShiftDown);
    jcbShiftDown.setSize(150, 20);
    jcbShiftDown.setLocation(5, 5 + jcbLongClick.getY() + jcbLongClick.getHeight());
    super.add(jcbShiftDown);
  }

  public static void main(String[] args) {

    JFrame jf = new JFrame();
    jf.setVisible(true);
    jf.setResizable(false);
    jf.setSize(300, 200);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    jf.add(new DelphiPanel(null));
    //().storeData();;
  }
}

/**
 * JCheckbox extended to display tooltip at special JTA
 * @author juli
 *
 */
class XCheckBox extends JCheckBox implements MouseListener {
  
  private String ttt;
  private final JTextArea jtaDisp;
  public XCheckBox(final String xcontent, final JTextArea xjtaDisp) {
    super(xcontent);
    this.jtaDisp = xjtaDisp;
    super.addMouseListener(this);
  }
  
  
  public void setToolTipText(final String text) {
    super.setToolTipText(text);
    ttt = text;
  }


  @Override
  public void mouseClicked(MouseEvent e) { }


  @Override
  public void mousePressed(MouseEvent e) { }


  @Override
  public void mouseReleased(MouseEvent e) { }


  @Override
  public void mouseEntered(MouseEvent e) {
    jtaDisp.setText(ttt);
  }


  @Override
  public void mouseExited(MouseEvent e) { }
}
