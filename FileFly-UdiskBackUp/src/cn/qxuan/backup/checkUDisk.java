package cn.qxuan.backup;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

// U�̼��
public class checkUDisk {
	//u��������
	public static String capacityAll = null;
	//U��ʣ������
	public static  String capacityLast = null;
	//U��·��
	public static File DiskPath = null;
	public  static boolean IsDisk = false; //U���Ƿ����
	/**
	 * �ж�U������
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
	/**
	 * ����U���Ƿ����
	 * @return
	 */
    public String check(){
    	//��ʼ���̷�
    	 FileSystemView fsv1 = FileSystemView.getFileSystemView();
    	 File[] disks = File.listRoots();
    	File[] fs = null;
    	for(;;){
    		// ��ǰ�ļ�ϵͳ��
            FileSystemView fsv = FileSystemView.getFileSystemView();
            // �г�����windows ����
            fs = File.listRoots();
            
            if(!Arrays.equals(disks, fs)){
            	 System.out.print("��⵽U�̣�");
            	for (File o : fs) {  
                    /* 
                     * ɸѡ��������������ͬ��ֵ��>= 0 ��ʾ��ͬ��< 0 ��ʾ��ͬ �� 
                     * ����ֵͬ�Ƿ���Ԫ�ص��±�ֵ�� 
                     * �˴����õ��� "����������������ָ������"�� 
                     * */  
                    if(Arrays.binarySearch(disks, o) < 0){  
                        System.out.println(fsv.getSystemDisplayName(o));
                        capacityAll = FormetFileSize(o.getTotalSpace());
                        capacityLast = FormetFileSize(o.getFreeSpace());
                        DiskPath = o;
                        IsDisk = true;
                        return fsv.getSystemDisplayName(o);
                       
                    } 
            	}
            }else{
            	System.out.println("��U��");
            }
        }
    	
    }
   
}