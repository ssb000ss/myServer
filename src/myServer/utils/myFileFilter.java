package sendmeserver.utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

// фильтр для возможности выбора файлов только с раширением mp3 для компонента FileChooser
public class myFileFilter extends FileFilter {
    
    private String fileExtension;
    private String fileDescription;

    public myFileFilter(String fileExtension, String fileDescription) {
        this.fileExtension = fileExtension;
        this.fileDescription = fileDescription;
    }
  
    @Override
    public boolean accept(File file) {// Разрешить только папки, а также файлы с расширением jks
        return file.isDirectory() || file.getAbsolutePath().endsWith(fileExtension);
    }   

    @Override
    public String getDescription() {// описание для формата jks при выборе в диалоговом окне
        return fileDescription+" (*."+fileExtension+")";
    }
}
