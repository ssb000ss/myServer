package myServer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class myDate {
   private Date date;
   private SimpleDateFormat format;
   private SimpleDateFormat formatDir;

   public myDate() {
   date=new Date();
   format=new SimpleDateFormat("dd.MM.yyyy hh.mm");
   formatDir=new SimpleDateFormat("dd.MM.hh.mm");
   }
   
   public  String getDate(){
   return new String(format.format(date)+" | "); 
   }

    public String getFormatDir() {
        return formatDir.format(date);
    }
   
   
   
}
