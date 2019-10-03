package com.ocheretyany.alexander.messenger;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * The class is used to create a chat frame for the program Messenger
 * 
 * @author Alexander Ocheretyany
 */
public class Chat implements ActionListener, KeyListener, WindowListener, ListSelectionListener {

	private JFrame mainFrame; // Main frame
	private JTextArea input;
	private JTextArea messages;
	private JScrollPane scrollMessages;
	private String name;
	private String friend;
	private String conversation;
	private JButton send;
	private boolean shiftIsReleased;
	private String currentMessages;
	private ArrayList<Friend> listOfFriends;
	private JList<String> list; // List of friends in String format
	private boolean exit; // Flag for exit

	/** Main constructor */
	Chat(String login) {

		// Initialization
		name = login;
		friend = null;
		shiftIsReleased = true;
		exit = false;
		listOfFriends = new ArrayList<Friend>();

		// Main frame
		mainFrame = new JFrame(login);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addWindowListener(this);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.setLocationRelativeTo(null);

		// Left Panel
		JPanel friends = new JPanel();
		friends.setSize(120, 438);
		friends.setLayout(null);

		list = new JList<>();
		list.addListSelectionListener(this);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only one item can be selected at one time
		list.setMaximumSize(new Dimension(50, 100));
		JPopupMenu popup = new JPopupMenu();
		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getActionCommand().equals("Close")) {
					setFriend(null);
					String item = list.getSelectedValue();
					for (Friend f : listOfFriends) {
						if (f.getName().equals(item)) {
							listOfFriends.remove(f);
							break;
						}
					}
					list.setListData(getNamesOfAll());
				}
			}

		});
		popup.add(close);
		popup.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				String item = list.getSelectedValue();
				if (item == null || item.length() == 0) {
					close.setEnabled(false);
				} else {
					close.setEnabled(true);
				}
			}
		});
		list.setComponentPopupMenu(popup);
		JScrollPane scrollFriends = new JScrollPane(list);
		scrollFriends.setPreferredSize(new Dimension(190, 320));
		scrollFriends.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
		scrollFriends.setBounds(1, 1, 248, 638);
		getNews();

		// Send button
		send = new JButton("<html>S<br>e<br>n<br>d</html>");
		send.setMnemonic('s');
		send.addActionListener(this);
		send.setBounds(630, 540, 61, 126);
		send.setEnabled(false);

		// New conversation button
		JButton newConversation = new JButton("New conversation");
		newConversation.addActionListener(this);
		newConversation.setBounds(1, 640, 247, 26);

		// Messages panel
		messages = new JTextArea();
		messages.setEditable(false);
		messages.setEnabled(false);
		messages.setSize(450, 700);
		scrollMessages = new JScrollPane(messages);
		scrollMessages.setBounds(250, 1, 442, 539);
		scrollMessages.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 8));
		scrollMessages.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));

		// Input panel
		input = new JTextArea();
		input.addKeyListener(this);
		input.setEnabled(false);
		input.setSize(300, 160);
		JScrollPane scrollInput = new JScrollPane(input);
		scrollInput.setBounds(250, 540, 380, 127);
		scrollInput.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 8));
		scrollInput.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));

		mainFrame.getContentPane().add(scrollFriends);
		mainFrame.getContentPane().add(newConversation);
		mainFrame.getContentPane().add(scrollMessages);
		mainFrame.getContentPane().add(scrollInput);
		mainFrame.getContentPane().add(send);
		mainFrame.pack();
		mainFrame.setSize(700, 700);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		setStatus("ONLINE");

		Thread t = startDaemon();
		t.start();
	}

	private Thread startDaemon() {
		return new Thread(new Runnable() {
			public void run() {
				while (!exit) {
					getHidden();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("<html>S<br>e<br>n<br>d</html>")) {
			String str = input.getText().trim();
			String text = messages.getText();
			if (str.length() > 0) {
				input.setText(null);

				// Update database
				if (text.length() > 0) {
					text = text + "\n" + name + ": " + str;
				} else {
					text = name + ": " + str;
				}
				Connection con = Main.getConnection();
				try {
					String query = "UPDATE chats SET conversation = ? WHERE names = ?";
					PreparedStatement statement = con.prepareStatement(query);
					statement.setString(1, text);
					statement.setString(2, conversation);
					statement.executeUpdate();
					if (!isOnline(friend)) {
						notifyFriend(friend, "Offline");
					} else {
						notifyFriend(friend, "Online");
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (ae.getActionCommand().equals("New conversation")) {
			JFrame newFriend = new JFrame("Add");
			newFriend.setSize(150, 100);
			newFriend.setResizable(false);
			newFriend.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			newFriend.setLocationRelativeTo(mainFrame);
			JTextField friendsName = new JTextField();
			friendsName.setText("Name");
			friendsName.setPreferredSize(new Dimension(80, 20));
			friendsName.selectAll();
			JButton ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean flag = false;

					for (Friend f : listOfFriends) {
						if (f.getName().equals(friendsName.getText())) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						Connection con = Main.getConnection();

						try {
							String query = "SELECT id FROM data WHERE login = ?";
							PreparedStatement statement = con.prepareStatement(query);
							statement.setString(1, friendsName.getText());
							ResultSet set = statement.executeQuery();
							if (set.next()) {
								Friend fr = new Friend(friendsName.getText(), new Detector(friendsName.getText()));
								listOfFriends.add(fr);
								list.setListData(getNamesOfAll());
								list.setSelectedValue(fr.getName(), true);
							} else {
								JOptionPane.showMessageDialog(mainFrame, "This user doesn't exist!", "No such a user",
										JOptionPane.ERROR_MESSAGE);
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(mainFrame, "You already have this friend in the list!",
								"Already exists", JOptionPane.ERROR_MESSAGE);
					}
					newFriend.dispose();
				}

			});
			newFriend.setLayout(new FlowLayout());
			newFriend.getContentPane().add(friendsName);
			newFriend.add(ok);
			newFriend.getRootPane().setDefaultButton(ok);
			newFriend.invalidate();
			newFriend.validate();
			newFriend.pack();
			newFriend.setVisible(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
			shiftIsReleased = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			if (shiftIsReleased) {
				send.doClick();
				arg0 = null;
			} else {
				input.append("\n");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
			shiftIsReleased = true;
		}
		if (shiftIsReleased && arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			input.setText(null);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		setStatus("OFFLINE");
		exit = true;
		Connection con = Main.getConnection();
		try {
			String query = "UPDATE data SET online = 'FALSE' WHERE login = ?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, name);
			statement.executeUpdate();
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (!arg0.getValueIsAdjusting()) {
			if (list.getSelectedValue() != null) {

				messages.setEnabled(true);
				input.setEnabled(true);
				send.setEnabled(true);

				if (!list.getSelectedValue().equals(friend)) {

					messages.setText(null); // Empty messages frame
					messages.repaint();
					setFriend(list.getSelectedValue().toString().trim()); // Set up current friend
					Connection con = Main.getConnection();

					try {
						String query = "SELECT conversation FROM chats WHERE names = ?";
						PreparedStatement statement = con.prepareStatement(query);
						conversation = name + " AND " + friend;
						statement.setString(1, conversation);
						ResultSet set = statement.executeQuery();
						if (!set.next()) {
							conversation = friend + " AND " + name;
							statement.setString(1, conversation);
							set = statement.executeQuery();

							if (!set.next()) {
								query = "INSERT INTO chats(conversation, names) VALUES(null, ?)";
								statement = con.prepareStatement(query);
								statement.setString(1, conversation);
								statement.executeUpdate();
							}
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
					}

					for (Friend f : listOfFriends) {
						if (f.getName().equals(friend)) {
							if (!f.getThread().isAlive())
								f.getThread().start();
						}
					}
				}
			} else {
				setFriend(null);
			}
		}
	}

	private String[] getNamesOfAll() {
		String[] names = new String[0];
		if (listOfFriends != null && !listOfFriends.isEmpty()) {
			names = new String[listOfFriends.size()];
			int index = 0;
			for (Friend f : listOfFriends) {
				names[index++] = f.getName();
			}
		}
		return names;
	}

	/**
	 * The class for representation of friends. Each instance of Friend class
	 * contains its name and a reference to its unique thread in which messages are
	 * being updated for this particular friend.
	 */
	class Friend {

		private String name;
		private Thread thread;

		Friend(String name, Thread thread) {
			this.name = name;
			this.thread = thread;
		}

		private String getName() {
			return name;
		}

		private Thread getThread() {
			return thread;
		}
	}

	/** The class for creation of threads for friends. */
	class Detector extends Thread {

		Detector(String str) {
			this.setName(str);
		}

		/**
		 * The function permanently checks whether there are new messages from a
		 * particular friend.
		 */
		public void run() {
			Connection con = Main.getConnection();
			while (!exit) {
				if (friend != null && friend.trim().equals(this.getName())) {
					try {
						String query = "SELECT conversation FROM chats WHERE names = ?";
						PreparedStatement statement = con.prepareStatement(query);
						statement.setString(1, conversation);
						ResultSet set = statement.executeQuery();
						if (set.next()) {
							String s = set.getString(1);
							if (s != null && ((currentMessages != null && !currentMessages.equals(s))
									|| currentMessages == null)) {
								setMessage(s);
							}
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	/**
	 * The function manages the buttons of the main frame. It activates them, when a
	 * friend has been set for communications.
	 */
	private synchronized void setFriend(String str) {
		friend = str;
		if (str != null) {
			messages.setEnabled(true);
			input.setEnabled(true);
			send.setEnabled(true);
		} else {
			setMessage(null);
			messages.setEnabled(false);
			input.setEnabled(false);
			send.setEnabled(false);
		}
	}

	/** The function sets messages for the conversation area */
	private synchronized void setMessage(String msg) {
		messages.setText(msg);
		messages.setCaretPosition(messages.getDocument().getLength());
		currentMessages = msg;
		input.setText(null);
	}

	/**
	 * The function is used to check, whether there are some new messages for the
	 * user that were received while the user was offline.
	 */
	private void getNews() {
		Connection con = Main.getConnection();
		try {
			String query = "SELECT news FROM data WHERE login = ?";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, name);
			ResultSet set = statement.executeQuery();
			String newsFrom = null;
			if (set.next()) {
				newsFrom = set.getString(1);
			}

			if (newsFrom != null && newsFrom.length() > 0) {
				ArrayList<String> names = parseNames(newsFrom);

				for (String s : names) {
					Friend fr = new Friend(s, new Detector(s));
					listOfFriends.add(fr);
				}

				list.setListData(getNamesOfAll());

				// Delete news
				query = "UPDATE data SET news = null WHERE login = ?";
				statement = con.prepareStatement(query);
				statement.setString(1, name);
				statement.executeUpdate();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Auxiluary function which helps getNews() function recognize names of friends
	 * from whom new messages were received.
	 */
	private ArrayList<String> parseNames(String text) {
		ArrayList<String> toReturn = new ArrayList<>();
		StringBuilder acc = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) != ':') {
				acc.append(text.charAt(i));
			} else {
				toReturn.add(acc.toString().trim());
				acc = new StringBuilder();
			}
		}
		if (acc.length() > 0)
			toReturn.add(acc.toString());
		return toReturn;
	}

	/** The function sets status for the user in the database. */
	private void setStatus(String status) {
		Connection con = Main.getConnection();
		try {
			if (status != null) {
				String query = "UPDATE data SET online = ? WHERE login = ?";
				PreparedStatement statement = con.prepareStatement(query);
				if (status.equals("ONLINE"))
					statement.setBoolean(1, true);
				else
					statement.setBoolean(1, false);
				statement.setString(2, name);
				statement.executeUpdate();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * The function notifies friends who are offline about new messages.
	 */
	private void notifyFriend(String n, String status) {
		Connection con = Main.getConnection();
		try {

			if (n != null) {

				if (status.equals("Offline")) {
					String query = "SELECT news FROM data WHERE login = ?";
					PreparedStatement statement = con.prepareStatement(query);
					statement.setString(1, friend);
					ResultSet set = statement.executeQuery();
					String s = null;
					boolean alreadyExists = false;

					if (set.next()) {
						s = set.getString(1);
					}

					if (s != null) {
						ArrayList<String> alreadyThere = parseNames(s);
						for (String h : alreadyThere) {
							if (h.equals(name)) {
								alreadyExists = true;
								break;
							}
						}
						if (!alreadyExists) {
							String update = s + ":" + name;
							query = "UPDATE data SET news = ? WHERE login = ?";
							statement = con.prepareStatement(query);
							statement.setString(1, update);
							statement.setString(2, friend);
							statement.executeUpdate();
						}
					} else {
						query = "UPDATE data SET news = ? WHERE login = ?";
						statement = con.prepareStatement(query);
						statement.setString(1, name);
						statement.setString(2, friend);
						statement.executeUpdate();
					}
				} else if (status.equals("Online")) {

					String query = "SELECT news FROM " + friend + " WHERE login = ?";
					PreparedStatement statement = con.prepareStatement(query);
					statement.setString(1, friend);
					ResultSet set = statement.executeQuery();
					String s = null;
					boolean alreadyExists = false;

					if (set.next()) {
						s = set.getString(1);
					}

					if (s != null) {
						ArrayList<String> alreadyThere = parseNames(s);

						for (String h : alreadyThere) {
							if (h.equals(name)) {
								alreadyExists = true;
								break;
							}
						}

						if (!alreadyExists) {
							String update = s + ":" + name;
							query = "UPDATE " + friend + " SET news = ? WHERE login = ?";
							statement = con.prepareStatement(query);
							statement.setString(1, update);
							statement.setString(2, friend);
							statement.executeUpdate();
						}

					} else {
						query = "UPDATE " + friend + " SET news = ? WHERE login = ?";
						statement = con.prepareStatement(query);
						statement.setString(1, name);
						statement.setString(2, friend);
						statement.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** The function checks whether a friend is online */
	private boolean isOnline(String str) {
		boolean status = false;
		Connection con = Main.getConnection();
		try {
			if (str != null) {
				String query = "SELECT online FROM data WHERE login = ?";
				PreparedStatement statement = con.prepareStatement(query);
				statement.setString(1, str);
				ResultSet set = statement.executeQuery();
				if (set.next()) {
					status = set.getBoolean(1);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return status;
	}

	/**
	 * The function checks whether there are new messages from friends who are not
	 * in the current friend list.
	 */
	private void getHidden() {
		Connection con = Main.getConnection();
		try {
			String query = "SELECT news FROM " + name + " WHERE login = ?";
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, name);
			ResultSet set = st.executeQuery();
			String newsFrom = null;

			if (set.next()) {
				newsFrom = set.getString(1);
			}

			if (newsFrom != null && newsFrom.length() > 0) {

				ArrayList<String> names = parseNames(newsFrom);
				ArrayList<String> exist = new ArrayList<>(); // Names of existing friends

				for (Friend f : listOfFriends) {
					exist.add(f.getName());
				}

				for (String s : names) {
					if (!exist.contains(s)) {
						Friend fr = new Friend(s, new Detector(s));
						listOfFriends.add(fr);
						list.setListData(getNamesOfAll());
					}
				}

				// Delete news
				query = "UPDATE " + name + " SET news = null WHERE login = ?";
				st = con.prepareStatement(query);
				st.setString(1, name);
				st.executeUpdate();
			}
			st.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(mainFrame, "Unknown error!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
