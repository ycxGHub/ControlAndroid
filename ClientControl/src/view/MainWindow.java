package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.eclipse.ui.statushandlers.StatusAdapter;

import bean.ClientBean;
import bean.ClientBean.ClientBeanType;
import handler.ChannelManager;
import service.HeartService;
import service.INotifyServer;
import service.ServerConnectService;
import util.NumberPicker;
import util.CmdManager.ConnectCmd;
import util.CmdManager.MeaCmd;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Component;
import javax.swing.JTextArea;

public class MainWindow extends JFrame implements INotifyServer {
	private JPanel contentPane;
	private Box root;
	private JTextArea textArea;
	private Button login;
	private JTextField androidId;
	private JTextField currentDeviceId;
	private JTextField data;
	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		addBoxes();
	}

	public void addBoxes() {
		root = Box.createVerticalBox();
		Box boxh1 = Box.createHorizontalBox();
		boxh1.add(new JLabel("Android DeviceId"));
		boxh1.add(Box.createHorizontalStrut(10));
		androidId = new JTextField(20);
		androidId.addKeyListener(new NumberPicker());
		boxh1.add(androidId);

		Box boxh2 = Box.createHorizontalBox();
		boxh2.add(new JLabel("Current DeviceId"));
		boxh2.add(Box.createHorizontalStrut(10));
		currentDeviceId = new JTextField(20);
		currentDeviceId.addKeyListener(new NumberPicker());
		boxh2.add(currentDeviceId);  
		
		
		Box boxh5 = Box.createHorizontalBox();
		boxh5.add(new JLabel("Data To RemoteDevice"));
		boxh5.add(Box.createHorizontalStrut(10));
		data = new JTextField(20);
		boxh5.add(data);  
		
		
		Box boxh4 = Box.createHorizontalBox();
		Button senCmd = new Button("Send Meausure Cmd");
		boxh4.add(senCmd);
		senCmd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ChannelManager.sendMeaCmd(currentDeviceId.getText(),androidId.getText(),data.getText());
			}
		});
		
		
		Box boxh3 = Box.createHorizontalBox();
		login = new Button("LOGIN");
		boxh3.add(login);
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							textArea.setText("Main login click");
							ServerConnectService.start(MainWindow.this);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				login.setVisible(false);
			}
		});

		root.add(boxh1);
		root.add(Box.createVerticalStrut(10));
		root.add(boxh2);
		root.add(Box.createVerticalStrut(10));
		root.add(boxh5);
		root.add(Box.createVerticalStrut(10));
		root.add(boxh3);
		root.add(Box.createVerticalStrut(10));
		root.add(boxh4);
		contentPane.add(root);
		getContentPane().add(contentPane, BorderLayout.NORTH);

		textArea = new JTextArea();
		JScrollPane jsp = new JScrollPane(textArea);
		jsp.setVerticalScrollBarPolicy( 
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		getContentPane().add(jsp, BorderLayout.CENTER);
	}

	@Override
	public void notifyClientData(String data) {
		addTextAreaData(data);
	}

	@Override
	public void notifyClientConnectState(boolean state) {
		// TODO Auto-generated method stub
		login.setVisible(!state);
		if (state) {
			addTextAreaData("login success!\n" + "ServerAddress" + ChannelManager.getServerAddress());
			ChannelManager.sendConnectCmd(ConnectCmd.ConnectServer,currentDeviceId.getText());
			HeartService heartService = new HeartService();
			heartService.startConnectServiceOnTime(1000 * 90, currentDeviceId.getText());
		} else {
			addTextAreaData("login failed!");
		}
	}

	public void addTextAreaData(String data) {
		textArea.append("\n"+data);
		textArea.setCaretPosition(textArea.getText().length());
	}
}
