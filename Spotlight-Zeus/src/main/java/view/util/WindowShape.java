package view.util;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import movelistener.control.Movelistener;
import opaqueFrame.OpFrame;

public class WindowShape extends OpFrame {

  private JLabel jlblShape;
  
  public WindowShape() {
    super(true);
    
    jlblShape = new JLabel();
    jlblShape.setOpaque(false);
    
    super.add(jlblShape);

    setVisible(true);
    
  }
  

  public void setSize(final int width, final int height) {
    super.setSize(width, height);
    generateShape();
    
    
  }
  
  
  public void generateShape() {

    if (jlblShape == null) {
      return;
    }
    jlblShape.setSize(getSize());
    BufferedImage biBackground = new BufferedImage(getWidth(),getHeight(), 
        BufferedImage.TYPE_INT_ARGB);

    final int argbOp = new Color(0, 0, 0, 0).getRGB();
    final int argbBorder = new Color(105, 117, 119, 255).getRGB();
    final int argbInside = new Color(245, 246, 247, 230).getRGB();
    final int posBorder = 14;
    final int thicknessBorder = 2;
    
    for (int i = 0; i < biBackground.getWidth(); i++) {
      for (int j = 0; j < biBackground.getHeight(); j++) {
        
        if (j > posBorder ) {
          if (j <= posBorder + thicknessBorder
              || biBackground.getHeight() - j <= thicknessBorder
              ||i <= thicknessBorder
              ||biBackground.getWidth() - i <= thicknessBorder) {

            final int v1 = Math.min(
                thicknessBorder, Math.max(biBackground.getHeight() - j, 0));
            final int v2 = Math.min(
                thicknessBorder, Math.max(j - posBorder, 0));
            final int v3 = Math.min(
                thicknessBorder, Math.max(biBackground.getWidth() - i, 0));
            final int v4 = Math.min(thicknessBorder, Math.max(i, 0));
            final int abstand = Math.min(
                Math.min(v1, v2),
                Math.min(v3, v4));
            final Color cc = new Color(argbBorder);
            biBackground.setRGB(i, j, new Color(
                cc.getRed(), cc.getGreen(), cc.getBlue(),
                255 * (abstand) / (thicknessBorder)).getRGB());
          } else {
            biBackground.setRGB(i, j, argbInside);
          }
        } else {
          biBackground.setRGB(i, j, argbOp);
        }
      }
    }
    final int xpos = getWidth() / 2;
    /*
    final double stretch = 1.0;
    for (int i = 0; i <= posBorder + thicknessBorder; i++) {
      for (int j = -i; j <= i; j++) {
        if (Math.abs (j - i) < thicknessBorder 
            || Math.abs (j + i) < thicknessBorder) {

          biBackground.setRGB(xpos + j, i, argbBorder);
        } else {
          biBackground.setRGB(xpos + j, i, argbInside);
        }
      }
    }*/
    
    final int sinCourveWidth = 30;
    final int maxUnterteilung = 100;
    for (int origX = 0; origX <= maxUnterteilung; origX++) {
      
      final double x = -Math.PI + 2.0 * origX / maxUnterteilung * Math.PI;
      final double thresh = (Math.cos(x) + 1) / 2 * posBorder;
      final int xc = xpos + origX * sinCourveWidth / maxUnterteilung - sinCourveWidth/ 2;

      final int yBorderF = posBorder - (int) Math.floor(thresh);
      final int yBorderC = posBorder - (int) Math.ceil(thresh);
      for (int y = yBorderC; y <= yBorderF + thicknessBorder; y++) {

        final Color cc = new Color(argbBorder);
        biBackground.setRGB(xc, y, new Color(cc.getRed(), cc.getBlue(), cc.getGreen(),
            255 * (y - yBorderC) / (yBorderF + thicknessBorder - yBorderC)).getRGB());
      }
      
      
//      biBackground.setRGB(xc, yBorderC, argbBorder);
      for (int y = yBorderF + thicknessBorder; y <= posBorder + thicknessBorder; y++) {
        final Color ins = new Color(argbInside, true);
        final Color bor = new Color(argbBorder, true);
        // hoch wenn nah an border. also der Max teil.
        int uebergang = 2;
        final double perc;
        if (uebergang == 0) {
          perc = 1;
          
        } else {
          perc = 1 - 1.0 * Math.max(0, yBorderF + 1 + uebergang - y) / uebergang;
          
        }
        biBackground.setRGB(xc, y, new Color(
            (int) (ins.getRed() * perc + bor.getRed() * (1 - perc)),
            (int) (ins.getGreen() * perc + bor.getGreen() * (1 - perc)),
            (int) (ins.getBlue() * perc + bor.getBlue() * (1 - perc)),
            (int) (ins.getAlpha() * perc + bor.getAlpha() * (1 - perc))).getRGB());
        
      }
      
    }
    for (double k = -Math.PI; k <= -Math.PI/2; k+= 0.001) {
      final int x = 2 + (int) ((k + Math.PI) * 20);
      final int y = thicknessBorder + (int) ((1 + Math.sin(k)) * 10);
      final int z = thicknessBorder + posBorder + (int) ((1 + Math.cos(k)) * 10);
      for (int l = posBorder; l < 10 + posBorder; l++) {
        if (l < z) {
          biBackground.setRGB(y, l, argbOp);
        } else {
          biBackground.setRGB(y, l, argbInside);
        }
      }
      biBackground.setRGB(y, z, argbBorder);
    }
    
    jlblShape.setIcon(new ImageIcon(biBackground));
  }
}
