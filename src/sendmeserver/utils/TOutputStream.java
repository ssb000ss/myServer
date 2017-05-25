package sendmeserver.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TOutputStream extends OutputStream {
    private JTextArea textArea;
     
    public TOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
   public void write(byte[] buffer, int offset, int length) throws IOException {
  final String text = new String(buffer, offset, length);
  SwingUtilities.invokeLater(new Runnable() {
   @Override
   public void run() {
    textArea.append (text);
   }
  });
 }

 @Override
 public void write(int b) throws IOException {
  write(new byte[] { (byte) b }, 0, 1);
 }
}
   