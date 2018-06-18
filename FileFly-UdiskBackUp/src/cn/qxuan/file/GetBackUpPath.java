package cn.qxuan.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * 获取备份路径
 * @author 齐轩
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
	 * 判断是否备份路径文件是否存在
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
