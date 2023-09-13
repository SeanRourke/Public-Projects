package database;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

public class AddUser extends JFrame{
	
	private JTextField nameField;
	private JPasswordField passwordField;
	
	public AddUser() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		add(parentPanel);
		
		// page header
		JLabel addHeader = new JLabel("Add User");
		Font heading = new Font("Arial", Font.BOLD, 24);
		addHeader.setFont(heading);
		parentPanel.add(addHeader);
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
		lowerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
	    parentPanel.add(lowerPanel);
	    
	    JButton back = new JButton("Return to Login");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// close frame and open desired frame
				dispose();
				Login a = new Login();
				a.setVisible(true);
			}
			
		});
	    lowerPanel.add(back);
		
		lowerPanel.add(Box.createVerticalStrut(30));

	    
	    // labels and text boxes
		lowerPanel.add(new JLabel("Username"));
		nameField = new JTextField(30);
		nameField.setColumns(15);
		lowerPanel.add(nameField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		lowerPanel.add(new JLabel("Password"));
		passwordField = new JPasswordField(30);
		passwordField.setColumns(15);
		lowerPanel.add(passwordField);
		lowerPanel.add(Box.createVerticalStrut(15));
						
		JButton submit = new JButton("Submit");
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		submit.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	// check if empty
		    	String name = nameField.getText();
		    	char[] passwordChars = passwordField.getPassword();
				String password = new String(passwordChars);
				if(!name.equals("") && !password.equals("")) {
					
					JLabel confirmation = new JLabel("Are you sure?");
			    	lowerPanel.add(confirmation);
			    	lowerPanel.add(Box.createVerticalStrut(15));
			    	JButton confirm = new JButton("Confirm");
			    	lowerPanel.add(confirm);
			    	lowerPanel.add(Box.createVerticalStrut(15));
			    	JButton cancel = new JButton("Cancel");
			    	lowerPanel.add(cancel);
			    	
			    	confirm.addActionListener(new ActionListener() {
				    	
			    		public void actionPerformed(ActionEvent e) {
			    			
			    			final String DATABASEURL = "jdbc:mysql://localhost/db1";
							Connection connection = null ;
							PreparedStatement pstat = null;
							String name = nameField.getText();
							char[] passwordChars = passwordField.getPassword();
							String password = new String(passwordChars);
							int i =0;
							try {
								// connect to database and enter data
								connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
								pstat = connection.prepareStatement("Insert into users (Username, Password)VALUES (?,?)");
								pstat . setString (1, name );
								pstat . setString (2, password);
								i = pstat .executeUpdate();
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
							JLabel status = new JLabel();
			                if(i > 0){
			                	// reset text boxes and remove confirmation buttons
			                	lowerPanel.remove(confirmation);
			                	lowerPanel.remove(confirm);
			                	lowerPanel.remove(cancel);
			                    status.setText("User Added");
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
			    	});
			    	
			    	cancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							
							lowerPanel.remove(confirmation);
							lowerPanel.remove(confirm);
							lowerPanel.remove(cancel);
							nameField.setText("");
		                    passwordField.setText("");
							lowerPanel.revalidate();
							lowerPanel.repaint();
							
						}
			    		
			    		
			    	});
			    	lowerPanel.revalidate();
			    	lowerPanel.repaint();
								
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
		
		lowerPanel.add(Box.createVerticalStrut(15));
	}

}
