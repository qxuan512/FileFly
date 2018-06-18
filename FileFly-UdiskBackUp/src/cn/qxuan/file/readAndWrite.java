package cn.qxuan.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * 读取文件，写入文件操作
 * @author 齐轩
 *
 */
public class readAndWrite {
	public String read(String path){
		File fr = new File(path) ;
        FileReader fr1;
        String str = null;
		try {
			fr1 = new FileReader(fr);
			BufferedReader br = new BufferedReader(fr1);
			str = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	public void write(String path,String writeStr){
		try {
	    	File file = new File(path);
		    if(!file.exists()){
		        file.getParentFile().mkdirs();          
		    }
		    file.createNewFile();
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(writeStr);
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
