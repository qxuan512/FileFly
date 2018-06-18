package cn.qxuan.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * ��ȡ����·��
 * @author ����
 *
 */
public class GetBackUpPath {
	public void getPath(String path) throws IOException{
	
    File file = new File(path);
    if(!file.exists()){
        file.getParentFile().mkdirs();          
    }
    file.createNewFile();

    // write
    FileWriter fw = new FileWriter(file, true);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(path);
    bw.flush();
    bw.close();
    fw.close();
	}
	/**
	 * �ж��Ƿ񱸷�·���ļ��Ƿ����
	 */
	public boolean pathIsExist(String path){
		File file = new File(path);
	    if(!file.exists()){
	    	return false;
	    }else{
	    	return true;
	    }
	}
}
