package cn.qxuan.backup;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

// U盘检测
public class checkUDisk {
	//u盘总容量
	public static String capacityAll = null;
	//U盘剩余容量
	public static  String capacityLast = null;
	//U盘路径
	public static File DiskPath = null;
	public  static boolean IsDisk = false; //U盘是否插入
	/**
	 * 判断U盘容量
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
	 * 监听U盘是否插入
	 * @return
	 */
    public String check(){
    	//初始化盘符
    	 FileSystemView fsv1 = FileSystemView.getFileSystemView();
    	 File[] disks = File.listRoots();
    	File[] fs = null;
    	for(;;){
    		// 当前文件系统类
            FileSystemView fsv = FileSystemView.getFileSystemView();
            // 列出所有windows 磁盘
            fs = File.listRoots();
            
            if(!Arrays.equals(disks, fs)){
            	 System.out.print("检测到U盘：");
            	for (File o : fs) {  
                    /* 
                     * 筛选出两个数组中相同的值，>= 0 表示相同，< 0 表示不同 。 
                     * 有相同值是返回元素的下标值。 
                     * 此处采用的是 "二分搜索法来搜索指定数组"。 
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
            	System.out.println("无U盘");
            }
        }
    	
    }
   
}