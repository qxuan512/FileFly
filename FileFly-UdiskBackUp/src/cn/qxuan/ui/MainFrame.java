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
 * main����
 * @author ����
 *
 */
public class MainFrame extends JFrame {

	private JPanel contentPane;
	public static JTextField backUpPath;
	private static JTextField Udisk;
	private static JTextField capaAll;
	private static JTextField capLast;
	
	//����·��
	public static String BackUpPath = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//�߳�һ
					Thread thread_1 = new Thread(new Runnable() {
						@Override
						public void run() {
							checkUDisk cud = new checkUDisk();//��ʼ�����̼�����
							String disk = cud.check();//����⵽���������ƴ���disk
							Udisk.setText(disk);//��ʾ��������
							capaAll.setText(cud.capacityAll);//��ʾ������
							capLast.setText(cud.capacityLast);//��ʾʣ������
							String path = "C:\\FileFly\\FileFly����·��.txt";
							//�ж�U���Ƿ���뼰���ر���·���ļ��Ƿ����
							if(checkUDisk.IsDisk = true && new GetBackUpPath().pathIsExist(path)){
								System.out.println(cud.DiskPath.getPath());
								//����U���ļ�������
								new CopyDirectory().copyFileOrDir(cud.DiskPath.getPath(), backUpPath.getText());
							}
						}
					});
					//�̶߳�
					Thread thread_2 = new Thread(new Runnable() {
						public void run() {
							//������
							MainFrame frame = new MainFrame();
							frame.setVisible(true);
							String path = "C:\\FileFly\\FileFly����·��.txt";
							//�жϱ��ر���·���ļ��Ƿ����
							if(new GetBackUpPath().pathIsExist(path)){
						        // ��ȡ����·��
								String path1 = "C:\\FileFly\\FileFly����·��.txt";
								File fr = new File(path1) ;
						        FileReader fr1;
								try {
									fr1 = new FileReader(fr);
									BufferedReader br = new BufferedReader(fr1);
									String str = br.readLine();
									//�ı�����ʾ����·��
									backUpPath.setText(str);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else if(!(new GetBackUpPath().pathIsExist(path))&&new MainFrame().pathIsEmpty()){
								//�ޱ��ر����ļ���д�뱸��·��
								String path1 = "C:\\FileFly\\FileFly����·��.txt";
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
					//�߳���
					Thread thread_3 = new Thread(new Runnable() {
						public void run() {
							//�ж�backUpPath�ı����Ƿ�Ϊ��
							new MainFrame().pathIsEmpty();
						}
					});
					/**
					 * �߳���
					 * ÿ������ʮ���㣬ͬ�����ر���Ŀ¼������
					 */
					Thread thread_4 = new Thread(new Runnable() {
						
						@Override
						public void run() {
							 final SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        long period=1000*60*24;//���ִ��ʱ��
						        //����ʱ��Ϊ����12����
						        Calendar cal = Calendar.getInstance();
						        cal.set(Calendar.HOUR_OF_DAY, 12);
						        cal.set(Calendar.MINUTE, 0);
						        cal.set(Calendar.SECOND, 0);
						        //�������ʱ�䳬��12�㣬��ʱ������ʱ������Ϊ��һ��
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
					//�߳�����
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
		//���ھ�����ʾ
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setLocation(screenWidth / 2 - this.getWidth() / 2, screenHeight
				/ 2 - this.getHeight() / 2);
		//���ڴ�С���ɸı�
		setResizable(false);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u5907\u4EFD\u8DEF\u5F84\uFF1A");
		label.setBounds(28, 32, 78, 15);
		label.setFont(new Font("����", Font.PLAIN, 15));
		contentPane.add(label);
		
		backUpPath = new JTextField();
		backUpPath.setBounds(106, 29, 98, 21);
		contentPane.add(backUpPath);
		backUpPath.setColumns(10);
		
		JCheckBox checkBox = new JCheckBox("\u7248\u672C\u63A7\u5236");
		checkBox.setBounds(354, 28, 98, 23);
		checkBox.setToolTipText("\u5F00\u542F\u7248\u672C\u63A7\u5236\u540E\u4E0B\u4E00\u6B21\u7684\u5907\u4EFD\u6587\u4EF6\u4E0D\u4F1A\u8986\u76D6\u73B0\u6709\u5907\u4EFD");
		checkBox.setFont(new Font("����", Font.PLAIN, 15));
		contentPane.add(checkBox);
		
		JLabel lblu = new JLabel("\u63D2\u5165U\u76D8\uFF1A");
		lblu.setBounds(28, 106, 78, 15);
		lblu.setFont(new Font("����", Font.PLAIN, 15));
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
		lblU.setFont(new Font("����", Font.PLAIN, 15));
		contentPane.add(lblU);
		
		capaAll = new JTextField();
		capaAll.setBounds(334, 103, 52, 21);
		capaAll.setEditable(false);
		contentPane.add(capaAll);
		capaAll.setColumns(10);
		
		JLabel lblU_1 = new JLabel("\u5269\u4F59\u5BB9\u91CF\uFF1A");
		lblU_1.setBounds(396, 106, 98, 15);
		lblU_1.setFont(new Font("����", Font.PLAIN, 15));
		contentPane.add(lblU_1);
		
		capLast = new JTextField();
		capLast.setBounds(479, 103, 52, 21);
		capLast.setEditable(false);
		capLast.setColumns(10);
		contentPane.add(capLast);
		/**
		 * ����Ŀ¼ѡ��
		 */
		JButton pathButton = new JButton("\u9009\u53D6\u8DEF\u5F84");
		pathButton.setBounds(214, 28, 93, 23);
		pathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc=new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//ֻ��ѡ��Ŀ¼
				fc.showOpenDialog(pathButton);
				File f = fc.getSelectedFile();
				BackUpPath = f.getPath();//Ŀ¼���浽BackUpPath����
				backUpPath.setText(f.getPath());//��ѡ���Ŀ¼��ʾ���ı���
			}
		});
		contentPane.add(pathButton);
		
		JLabel label_1 = new JLabel("\u5907\u4EFD\u6587\u4EF6\uFF1A");
		label_1.setBounds(28, 132, 78, 15);
		label_1.setFont(new Font("����", Font.PLAIN, 15));
		contentPane.add(label_1);
		
		JLabel lblU_2 = new JLabel("U\u76D8\u6587\u4EF6\uFF1A");
		lblU_2.setBounds(308, 132, 78, 15);
		lblU_2.setFont(new Font("����", Font.PLAIN, 15));
		contentPane.add(lblU_2);
	}
	/***
	 * �ж�backUpPath�ı����Ƿ�Ϊ��
	 * @return
	 */
	public boolean pathIsEmpty(){
		while(true){
			if(!("".equals(backUpPath.getText()))){
				return true;
			}else{
				System.out.println("�ı�����·��");
			}
		}
	}
}
