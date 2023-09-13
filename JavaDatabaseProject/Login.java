package database;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Login extends JFrame{
	
	private JTextField nameField;
	private JPasswordField passwordField;
	
	public Login() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		add(parentPanel);
		
		parentPanel.add(Box.createVerticalStrut(50));
		// page header
		JLabel addHeader = new JLabel("Login");
		Font heading = new Font("Arial", Font.BOLD, 24);
		addHeader.setAlignmentX(Box.CENTER_ALIGNMENT);
		addHeader.setFont(heading);
		parentPanel.add(addHeader);
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
		lowerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
	    parentPanel.add(lowerPanel);
		
		lowerPanel.add(Box.createVerticalStrut(50));

	    
	    // labels and text boxes
		JLabel usernameLabel = new JLabel("Username");
		nameField = new JTextField(30);
		nameField.setColumns(15);
		usernameLabel.setAlignmentX(Box.CENTER_ALIGNMENT);
		lowerPanel.add(usernameLabel);
		lowerPanel.add(nameField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		JLabel passwordLabel = new JLabel("Password");
		passwordField = new JPasswordField(30);
		passwordField.setColumns(15);
		passwordField.setEchoChar('*');
		passwordLabel.setAlignmentX(Box.CENTER_ALIGNMENT);
		lowerPanel.add(passwordLabel);
		lowerPanel.add(passwordField);
		lowerPanel.add(Box.createVerticalStrut(15));
						
		JButton submit = new JButton("Login");
		lowerPanel.add(Box.createVerticalStrut(15));
		
		JLabel status = new JLabel();
		
		
		submit.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	// check if empty
		    	String name = nameField.getText();
		    	char[] passwordChars = passwordField.getPassword();
				String password = new String(passwordChars);
				if(!name.equals("") && !password.equals("")) {
					final String DATABASEURL = "jdbc:mysql://localhost/db1";
					Connection connection = null ;
					PreparedStatement pstat = null;
					ResultSet rs = null;
					try {
						// connect to database and enter data
						connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
						pstat = connection.prepareStatement("Select * from users");
						rs = pstat.executeQuery();
						while (rs.next()) {
							String username = rs.getString("Username");
							String pass = rs.getString("Password");
							
							if (name.equals(username) && password.equals(pass)) {
								
								dispose();
								MainMenu a = new MainMenu();
								a.setVisible(true);
							}
							else {
								status.setText("Incorrect Details");
			                    nameField.setText("");
			                    passwordField.setText("");
			                    // display completed message for one second
			                    Timer timer = new Timer(1000, new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										status.setText("");
										
									}
			                    });
			                    // run timer once
			                    timer.setRepeats(false); 
			                    timer.start(); 
			                }
			                // refresh page
			                lowerPanel.add(status);
			                lowerPanel.revalidate();
			                lowerPanel.repaint();
							
							
						}
					}
					catch(SQLException sqlException){
						sqlException . printStackTrace () ;
					}
					finally {
						try {
							pstat . close () ;
							connection. close () ;
						}
						catch (Exception exception){
							exception . printStackTrace () ;
						}	
					}
								
				}
				else {
			    	JLabel empty = new JLabel("Empty Fields");
			    	Timer timer = new Timer(1000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							empty.setText("");	
						}
                    });
                    // run timer once
                    timer.setRepeats(false); 
                    timer.start(); 
                    lowerPanel.add(empty);
	                lowerPanel.revalidate();
	                lowerPanel.repaint();
			    }
		    }
		});
		
		lowerPanel.add(submit);
		submit.setAlignmentX(CENTER_ALIGNMENT);
		lowerPanel.add(Box.createVerticalStrut(15));

		JButton create = new JButton("New User");
		create.setAlignmentX(CENTER_ALIGNMENT);
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				AddUser a = new AddUser();
				a.setVisible(true);
			}
			
		});
		
		lowerPanel.add(create);
		lowerPanel.add(Box.createVerticalStrut(15));
	}

}
