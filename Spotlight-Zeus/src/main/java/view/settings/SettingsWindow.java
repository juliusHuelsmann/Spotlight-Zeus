package view.settings;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import opaqueFrame.OpFrame;
import spotlight.delphi.utils.BufferedViewer;
import spotlight.delphi.utils.Utils;
import tabPnl.TabPanel;
import view.settings.specialized.AthenePanel;
import view.settings.specialized.DelphiPanel;
import view.settings.specialized.MainPanel;

public class SettingsWindow extends OpFrame {
  
  
  private JLabel jlbl;
  private final static int defWidth = 374;
  private static final int defHeight = 256;

  public SettingsWindow(final ActionListener ali) {
    this(Utils.resize("/res/settingsWinOpaque.png", defWidth, defHeight), ali);
  }
  
  private TabPanel tp;
  
  private MainPanel mp;
  private AthenePanel ap;
  private DelphiPanel dp;

  public SettingsWindow(BufferedImage xbi, final ActionListener ali) {
    
    super(false);
    super.setSize(defWidth, defHeight);
    super.addFocusListener(new FocusListener() {
      
      @Override
      public void focusLost(FocusEvent e) {
        
      }
      
      @Override
      public void focusGained(FocusEvent e) {
        // TODO Auto-generated method stub
        
      }
    });
    
    tp = new TabPanel();
    tp.setVisible(true);
    tp.setSize(getWidth() - 16, getHeight() - 30);
    tp.setLocation(8, 20);
    final int buttonsize = 118;
    
    mp = new MainPanel(ali);
    ap = new AthenePanel(ali);
    dp = new DelphiPanel(ali);

    tp.add(mp, "Main", 0, buttonsize);
    tp.add(dp, "Spotlight-Delphi", 1, buttonsize);
    tp.add(ap, "Spotlight-Athene", 2, buttonsize);
    super.add(tp);
    
    
    
    
    final TrayIcon icnTray =
    
        new TrayIcon(Utils.resize("/res/settingsWinOpaque.png", 10, 10), "/res/settingsWinOpaque.png");
    final SettingsWindowML sml = new SettingsWindowML(this);
    icnTray.addMouseListener(sml);
    super.addWindowFocusListener(sml);
    final SystemTray tray = SystemTray.getSystemTray();
    try {
        tray.add(icnTray);
    } catch (AWTException e) {
        System.out.println("TrayIcon could not be added.");
    }
    jlbl = new JLabel();
    jlbl.setIcon(new ImageIcon(Utils.resize(xbi, defWidth, defHeight)));
    jlbl.setSize(defWidth, defHeight);
    super.add(jlbl);
    
    super.setVisible(false);
    super.setResizable(false);
  }
  
  
  
  public static void main(String[] args) {
    generateImage();
  }

  public static final void generateImage() {
    
    
    BufferedImage bi = Utils.resize("/res/settingsWin.png", defWidth, defHeight);
    adaptImageBorders(bi);
    try {
      // retrieve image
      File outputfile = new File("settingsWinOpaque.png");
      ImageIO.write(bi, "png", outputfile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    new SettingsWindow(bi, null);
  }
  public static final void generateImageOther() {
    
    //feldSingleSelected
    BufferedImage bi = Utils.resize("/res/feldSingleSelected.png", 44, 44);
    adaptImageBorders(bi);
//    adaptImageGlobal(bi);
    try {
      // retrieve image
      File outputfile = new File("feldSingleSelected.png");
      ImageIO.write(bi, "png", outputfile);
    } catch (IOException e) {
      e.printStackTrace();
    }

    BufferedViewer.getInstance().show(bi);
  }
  
  

  
  public static void adaptImageBorders(final BufferedImage xbi) {
    
    
    //
    // go through the image from left
    for (int w = 0; w < xbi.getWidth(); w++) {
      for (int h = 0; h < xbi.getHeight(); h++) {
        if (!testPX(xbi, new Point(w, h))) {
          h = xbi.getHeight();
          continue;
        }
      }
    }

    //
    // go through the image from right
    for (int w = xbi.getWidth() - 1; w >= 0; w--) {
      for (int h = 0; h < xbi.getHeight(); h++) {
        if (!testPX(xbi, new Point(w, h))) {
          h = xbi.getHeight();
          continue;
        }
      }
    }
    

    //
    // go through the image from top
    for (int h = 0; h < xbi.getHeight(); h++) {
      for (int w = 0; w < xbi.getWidth(); w++) {
        if (!testPX(xbi, new Point(w, h))) {
          w = xbi.getWidth();
          continue;
        }
      }
    }

    //
    // go through the image from bottom
    for (int h = xbi.getHeight() - 1; h >= 0; h--) {
      for (int w = 0; w < xbi.getWidth(); w++) {
        if (!testPX(xbi, new Point(w, h))) {
          w = xbi.getWidth();
          continue;
        }
      }
    }
    //
    // go through the image from bottom
    for (int h = xbi.getHeight() - 1; h >= 0; h--) {
      for (int w = xbi.getWidth() - 1; w >= 0; w--) {
        if (!testPX(xbi, new Point(w, h))) {
          w = -1;
          continue;
        }
      }
    }
  }

  
  public static void adaptImageGlobal(final BufferedImage xbi) {
    
    
    //
    // go through the image from left
    for (int w = 0; w < xbi.getWidth(); w++) {
      for (int h = 0; h < xbi.getHeight(); h++) {
        testPX(xbi, new Point(w, h));
      }
    }
  }
  
  private static final boolean testPX(
      final BufferedImage xbi, final Point xpntCurr) {

    final int thresh = 615;
    final int aimgrayscale = thresh / 3;
    
    
    
    final int rgb =  xbi.getRGB(xpntCurr.x, xpntCurr.y);
    final Color clr = new Color(rgb, true);
      final int sum = clr.getRed() + clr.getGreen() + clr.getBlue();
      if (sum > thresh && clr.getAlpha() == 255) {
        
        int abstd = sum - thresh;
        final int maxabstd = 255 * 3 - thresh;
        final double additionalFactor = 1.9;
        abstd = (int) Math.round(abstd * 255  * additionalFactor / maxabstd);
        abstd = Math.min(abstd, 255);
        abstd = 255 - abstd;

        final int rgbaresult 
        = new Color(aimgrayscale, aimgrayscale, aimgrayscale, abstd).getRGB();
//        = new Color(255, 0, 0, 254).getRGB();
        
        //TODO: abstd normieren
        
        // select
        xbi.setRGB(xpntCurr.x, xpntCurr.y, 
            rgbaresult);
        return true;
      } else if (clr.getAlpha() != 255) {
        return true;
      }
    return false;
  }



  /**
   * @return the mp
   */
  public MainPanel getMp() {
    return mp;
  }



  /**
   * @param mp the mp to set
   */
  public void setMp(MainPanel mp) {
    this.mp = mp;
  }



  /**
   * @return the ap
   */
  public AthenePanel getAp() {
    return ap;
  }



  /**
   * @param ap the ap to set
   */
  public void setAp(AthenePanel ap) {
    this.ap = ap;
  }



  /**
   * @return the dp
   */
  public DelphiPanel getDp() {
    return dp;
  }



  /**
   * @param dp the dp to set
   */
  public void setDp(DelphiPanel dp) {
    this.dp = dp;
  }
}

class SettingsWindowML implements MouseListener, WindowFocusListener {

  SettingsWindow sw;
  public SettingsWindowML(SettingsWindow xsw) {
    this.sw = xsw;
  }
    private final int thresh = 10;
    private int oldx = -thresh;
    private boolean enabled = false;
    @Override
    public void mouseClicked(MouseEvent e) {

      GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
      Rectangle bounds = gd.getDefaultConfiguration().getBounds();
      Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());
      final int gap =  bounds.y + insets.top;

      enabled = !enabled;
      sw.setVisible(enabled);
      
      final int newx = e.getX();
      if (enabled && Math.abs(oldx - newx) > thresh) {

        sw.setLocation(newx  - sw.getWidth() / 2, gap);
        oldx = newx;
      }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void windowGainedFocus(WindowEvent e) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void windowLostFocus(WindowEvent e) {

      enabled = false;
      sw.setVisible(false);
          
    }

    
    
}



