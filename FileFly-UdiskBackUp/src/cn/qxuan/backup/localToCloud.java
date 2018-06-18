package cn.qxuan.backup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;

import cn.qxuan.file.GetBackUpPath;
import cn.qxuan.file.readAndWrite;
/**
 * ͬ�����ر����ļ�������
 * @author ����
 *
 */
public class localToCloud {
	public void toCloud(){
		//��ȡ��������·��
		String path = "C:\\FileFly\\FileFly����·��.txt";
		File fr = new File(path) ;
        FileReader fr1;
        String str = null;
		try {
			fr1 = new FileReader(fr);
			BufferedReader br = new BufferedReader(fr1);
			str = br.readLine();
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//·���ӹ�����  "\"
		String s = new localToCloud().stringUtil(str);
		//д�����̱����ļ�
		String path3 = "C:\\FileFly\\FileFle���̱����ļ�.txt";
		if(new GetBackUpPath().pathIsExist(path3)){
			// ��ȡ���̱���·��
			String path1 = "C:\\FileFly\\FileFly����·��.txt";
			new readAndWrite().read(path1);
		}else{ 
				String up = "{\"src_dir\"            :   \""+s+"\","
						+ "\"bucket\"             :   \"filefly\","
						+ "\"ignore_dir\"         :   true,"
						+ "\"overwrite\"          :   false,"
						+ "\"check_exists\"       :   true,"
						+ "\"check_hash\"         :   true,"
						+ "\"check_size\"         :   true,"
						+ "\"rescan_local\"       :   true}";
				new readAndWrite().write(path3, up);
		}
		//ִ��CMD���ͬ�������ļ�������
		String command="qshell qupload 1 "+path3;  
		System.out.println(command);
	    String line = "";  
	    StringBuilder sb = new StringBuilder();  
	    Runtime runtime = Runtime.getRuntime();  
	    try {  
	    Process process = runtime.exec(command);  
	    BufferedReader  bufferedReader = new BufferedReader  
	            (new InputStreamReader(process.getInputStream()));  
	        while ((line = bufferedReader.readLine()) != null ) {  
	            sb.append(line + "\n");  
	                System.out.println(line);  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	public String stringUtil(String str){
		StringBuilder sb = new StringBuilder(str);
		int j = 1;
		for(int i = 0;i < str.length();i++){
			String temp = str.substring(i, i+1);
			if(temp.equals("\\")){
				sb = sb.insert(i+j, "\\");
				j++;
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
}
