package cn.qxuan.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.border.LineBorder;

import cn.qxuan.backup.CopyDirectory;
import cn.qxuan.backup.checkUDisk;
import cn.qxuan.backup.localToCloud;
import cn.qxuan.file.GetBackUpPath;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/****
 * main主类
 * @author 齐轩
 *
 */
public class MainFrame extends JFrame {

	private JPanel contentPane;
	public static JTextField backUpPath;
	private static JTextField Udisk;
	private static JTextField capaAll;
	private static JTextField capLast;
	
	//备份路径
	public static String BackUpPath = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//线程一
					Thread thread_1 = new Thread(new Runnable() {
						@Override
						public void run() {
							checkUDisk cud = new checkUDisk();//初始化优盘检测程序
							String disk = cud.check();//将检测到的优盘名称存入disk
							Udisk.setText(disk);//显示优盘名称
							capaAll.setText(cud.capacityAll);//显示总容量
							capLast.setText(cud.capacityLast);//显示剩余容量
							String path = "C:\\FileFly\\FileFly备份路径.txt";
							//判断U盘是否插入及本地备份路径文件是否存在
							if(checkUDisk.IsDisk = true && new GetBackUpPath().pathIsExist(path)){
								System.out.println(cud.DiskPath.getPath());
								//复制U盘文件到本地
								new CopyDirectory().copyFileOrDir(cud.DiskPath.getPath(), backUpPath.getText());
							}
						}
					});
					//线程二
					Thread thread_2 = new Thread(new Runnable() {
						public void run() {
							//主界面
							MainFrame frame = new MainFrame();
							frame.setVisible(true);
							String path = "C:\\FileFly\\FileFly备份路径.txt";
							//判断本地备份路径文件是否存在
							if(new GetBackUpPath().pathIsExist(path)){
						        // 读取备份路径
								String path1 = "C:\\FileFly\\FileFly备份路径.txt";
								File fr = new File(path1) ;
						        FileReader fr1;
								try {
									fr1 = new FileReader(fr);
									BufferedReader br = new BufferedReader(fr1);
									String str = br.readLine();
									//文本框显示备份路径
									backUpPath.setText(str);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else if(!(new GetBackUpPath().pathIsExist(path))&&new MainFrame().pathIsEmpty()){
								//无本地备份文件，写入备份路径
								String path1 = "C:\\FileFly\\FileFly备份路径.txt";
							    try {
							    	File file = new File(path1);
								    if(!file.exists()){
								        file.getParentFile().mkdirs();          
								    }
								    file.createNewFile();
									FileWriter fw = new FileWriter(file, true);
									BufferedWriter bw = new BufferedWriter(fw);
									bw.write(backUpPath.getText());
									bw.flush();
									bw.close();
									fw.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					});
					//线程三
					Thread thread_3 = new Thread(new Runnable() {
						public void run() {
							//判断backUpPath文本框是否为空
							new MainFrame().pathIsEmpty();
						}
					});
					/**
					 * 线程四
					 * 每天中午十二点，同步本地备份目录至云盘
					 */
					Thread thread_4 = new Thread(new Runnable() {
						
						@Override
						public void run() {
							 final SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        long period=1000*60*24;//间隔执行时间
						        //设置时间为当天12点钟
						        Calendar cal = Calendar.getInstance();
						        cal.set(Calendar.HOUR_OF_DAY, 12);
						        cal.set(Calendar.MINUTE, 0);
						        cal.set(Calendar.SECOND, 0);
						        //如果当天时间超过12点，定时器启动时间设置为下一天
						        if(new Date().getTime() > cal.getTimeInMillis()){
						            cal.add(Calendar.DAY_OF_YEAR, 1);
						        }
						        Timer timer = new Timer();
						        timer.schedule(new TimerTask() {
						            @Override
						            public void run() {
						            	new localToCloud().toCloud();
						            }
						        },cal.getTime(),period);
						}
					});
					//线程启动
					thread_1.start();
					thread_2.start();
					thread_3.start();
					thread_4.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\\u9F50\u8F69\\Pictures\\\u56FE\u6807\\\u4E91\u7AEF\u5907\u4EFD.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//窗口居中显示
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setLocation(screenWidth / 2 - this.getWidth() / 2, screenHeight
				/ 2 - this.getHeight() / 2);
		//窗口大小不可改变
		setResizable(false);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u5907\u4EFD\u8DEF\u5F84\uFF1A");
		label.setBounds(28, 32, 78, 15);
		label.setFont(new Font("等线", Font.PLAIN, 15));
		contentPane.add(label);
		
		backUpPath = new JTextField();
		backUpPath.setBounds(106, 29, 98, 21);
		contentPane.add(backUpPath);
		backUpPath.setColumns(10);
		
		JCheckBox checkBox = new JCheckBox("\u7248\u672C\u63A7\u5236");
		checkBox.setBounds(354, 28, 98, 23);
		checkBox.setToolTipText("\u5F00\u542F\u7248\u672C\u63A7\u5236\u540E\u4E0B\u4E00\u6B21\u7684\u5907\u4EFD\u6587\u4EF6\u4E0D\u4F1A\u8986\u76D6\u73B0\u6709\u5907\u4EFD");
		checkBox.setFont(new Font("等线", Font.PLAIN, 15));
		contentPane.add(checkBox);
		
		JLabel lblu = new JLabel("\u63D2\u5165U\u76D8\uFF1A");
		lblu.setBounds(28, 106, 78, 15);
		lblu.setFont(new Font("等线", Font.PLAIN, 15));
		contentPane.add(lblu);
		
		Udisk = new JTextField();
		Udisk.setBounds(106, 103, 98, 21);
		Udisk.setEditable(false);
		Udisk.setText("\u65E0U\u76D8\u63D2\u5165");
		Udisk.setToolTipText("");
		contentPane.add(Udisk);
		Udisk.setColumns(10);
		
		JLabel lblU = new JLabel("U\u76D8\u603B\u5BB9\u91CF\uFF1A");
		lblU.setBounds(236, 106, 103, 15);
		lblU.setFont(new Font("等线", Font.PLAIN, 15));
		contentPane.add(lblU);
		
		capaAll = new JTextField();
		capaAll.setBounds(334, 103, 52, 21);
		capaAll.setEditable(false);
		contentPane.add(capaAll);
		capaAll.setColumns(10);
		
		JLabel lblU_1 = new JLabel("\u5269\u4F59\u5BB9\u91CF\uFF1A");
		lblU_1.setBounds(396, 106, 98, 15);
		lblU_1.setFont(new Font("等线", Font.PLAIN, 15));
		contentPane.add(lblU_1);
		
		capLast = new JTextField();
		capLast.setBounds(479, 103, 52, 21);
		capLast.setEditable(false);
		capLast.setColumns(10);
		contentPane.add(capLast);
		/**
		 * 备份目录选择
		 */
		JButton pathButton = new JButton("\u9009\u53D6\u8DEF\u5F84");
		pathButton.setBounds(214, 28, 93, 23);
		pathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc=new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
				fc.showOpenDialog(pathButton);
				File f = fc.getSelectedFile();
				BackUpPath = f.getPath();//目录储存到BackUpPath变量
				backUpPath.setText(f.getPath());//把选择的目录显示在文本框
			}
		});
		contentPane.add(pathButton);
		
		JLabel label_1 = new JLabel("\u5907\u4EFD\u6587\u4EF6\uFF1A");
		label_1.setBounds(28, 132, 78, 15);
		label_1.setFont(new Font("等线", Font.PLAIN, 15));
		contentPane.add(label_1);
		
		JLabel lblU_2 = new JLabel("U\u76D8\u6587\u4EF6\uFF1A");
		lblU_2.setBounds(308, 132, 78, 15);
		lblU_2.setFont(new Font("等线", Font.PLAIN, 15));
		contentPane.add(lblU_2);
	}
	/***
	 * 判断backUpPath文本框是否为空
	 * @return
	 */
	public boolean pathIsEmpty(){
		while(true){
			if(!("".equals(backUpPath.getText()))){
				return true;
			}else{
				System.out.println("文本框无路径");
			}
		}
	}
}
