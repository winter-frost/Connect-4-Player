package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import player.Player;
import player.yourPlayer;

/**
 * Class that defines GUI and waits for connection from main application. DO NOT
 * TOUCH OR YOU WILL NOT BE ABLE TO TEST YOUR PLAYER!
 * 
 * @author Jake Mason
 */
public class Application extends JFrame
{

	private JPanel mcontentPane;
	private JLabel lblWaitingForConnection;
	private ServerSocket sSocket;
	private Socket socket;
	private Player player;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Application frame = new Application();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Application()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		setBounds(100, 100, 300, 200);
		mcontentPane = new JPanel();
		mcontentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mcontentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(mcontentPane);

		JLabel lblWaitingForConnection = new JLabel("Waiting for connection...");
		mcontentPane.add(lblWaitingForConnection, BorderLayout.CENTER);

		this.waitForConnection();
	}

	/**
	 * Waits for a connection from the main application and subsequently runs
	 * game if connection is succesfully made.
	 */
	private void waitForConnection()
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					while (true)
					{
						Application.this.player = new yourPlayer();
						Application.this.sSocket = new ServerSocket(10100);
						Application.this.socket = Application.this.sSocket.accept();

						Application.this.lblWaitingForConnection = new JLabel("Connected");

						Byte myMove;
						Byte oppositionMove = null;

						while (oppositionMove == null || oppositionMove != 100)
						{
							myMove = Application.this.player.requestMove(oppositionMove);
							Application.this.socket.getOutputStream().write(myMove);

							oppositionMove = (byte) Application.this.socket.getInputStream().read();
						}

						Application.this.sSocket.close();
						Application.this.socket.close();
					}
				}
				catch (IOException ex)
				{
					Application.this.lblWaitingForConnection.setText("Connection error, close the application, wait a minute and try again or seek help");
					ex.printStackTrace();

					try
					{
						Thread.sleep(5000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					finally
					{
						Application.this.dispatchEvent(new WindowEvent(Application.this, WindowEvent.WINDOW_CLOSING));
					}
				}
			}
		}).start();
	}

}
