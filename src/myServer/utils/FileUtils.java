package myServer.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;




// утилита для работы с файлами
public class FileUtils {

    // удалить текущий файл-фильтр и установить новый переданный    
    public static void addFileFilter(JFileChooser jfc, FileFilter ff) {
        jfc.removeChoosableFileFilter(jfc.getFileFilter());
        jfc.setFileFilter(ff);
        jfc.setSelectedFile(new File(""));// удалить последнее имя открываемого/сохраняемого файла
    }

    public static boolean saveFile(String path, int fileSize, SSLSocket socket) {
        FileOutputStream fos = null;
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            fos = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int read = 0;
            int totalRead = 0;
            int remaining = fileSize;
            //чисто для интереса
            System.out.println(new myDate().getDate()+"приём файла");
            while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                fos.write(buffer, 0, read);
            }
            System.out.println(new myDate().getDate()+"Файл сохранён ...");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return false;
    }
    
    public static boolean sendFile(String fileName, SSLSocket socket)  {
               File myfile=null;
               DataOutputStream out=null;
           
               try {
                   myfile=new File(fileName);
                   FileInputStream  fis = new FileInputStream(myfile);
                   out = new DataOutputStream(socket.getOutputStream());
                   out.writeUTF(myfile.getName());
                   out.writeLong(myfile.length());
                   out.flush();
                  
                    try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
                       byte[] buffer=new byte[1024];
                       while (fis.read(buffer)>0){
                           dos.write(buffer);
                       }
                    }
                       System.out.println("Файл "+myfile.getName()+" отправлен");
                   
               } catch (IOException ex) {
                   Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
               } finally {
                   try {
                       out.close();
                   } catch (IOException ex) {
                       Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
               return true;
    }
    //сохраненить настройки
    public static boolean writeSittingFile(String path,String []mas){
        File sitting=new File(path+"//sitting.stg");
       try{
            if(!sitting.exists()){
            sitting.createNewFile();
            }
            PrintWriter out=new PrintWriter(sitting.getAbsoluteFile()); 
            try{
                for (int i = 0; i < mas.length; i++) {
                    if(mas[i]==null){
                    mas[i]="---";}
                    out.println(mas[i]);
                }
            }finally{
            out.close();
            }
    }   catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
            return  true;
 }
    
     public static boolean writeLogFile(String path,String str){
        File sitting=new File(path+"//"+"logs "+new myDate().getFormatDir()+".lgs");
       try{
            if(!sitting.exists()){
            sitting.createNewFile();
            }
            PrintWriter out=new PrintWriter(sitting.getAbsoluteFile()); 
            try{
                out.println(str);
            }finally{
            out.close();
            }
    }   catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
            return  true;
 }
    //импортировать настройки
    public static String [] readSittingFile(String fileName) throws FileNotFoundException{
        String [] mas=new String[5];
        exists(fileName);
        try {
           Scanner in=new Scanner(new File(fileName));
            String temp;
            while(in.hasNextLine()){
                for (int i = 0; i < mas.length; i++) {
                    mas[i]=in.nextLine();
                }
        }
           
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return  mas;
    }

    public static boolean exists(String fileName) throws FileNotFoundException {
       File file = new File(fileName);
        return file.exists();
  }
    
    public static String createDir (String path, String user){
        
    SimpleDateFormat format=new SimpleDateFormat("ddMMyyyy");
    String dir = new String (path +"\\"+new myDate().getFormatDir()+"\\" + user+"\\");

    final File dir1 = new File(dir);
        if(!dir1.exists()) {
            if(dir1.mkdirs()) {
                System.out.println("Каталог " + dir1.getAbsolutePath()
                        + " успешно создан.");
                return dir;
            } else {
                System.out.println("Каталог " + dir1.getAbsolutePath()
                        + " создвть не удалось.");
            return null;
            }
        } else {
            System.out.println("Каталог " + dir1.getAbsolutePath()
                        + " уже существует.");
            return dir;
        }      
    
    }
}
