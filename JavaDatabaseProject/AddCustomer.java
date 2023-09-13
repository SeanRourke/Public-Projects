package database;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;

public class AddCustomer extends JFrame{
	
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField telephoneField;
	private JTextField emailField;
	private JTextField addressField;
	private static final String emailPattern =
	            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	    private static final Pattern pattern = Pattern.compile(emailPattern);
	
	public AddCustomer() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		add(parentPanel);
		
		// page header
		JLabel addHeader = new JLabel("Add Customer");
		Font heading = new Font("Arial", Font.BOLD, 24);
		addHeader.setFont(heading);
		parentPanel.add(addHeader);
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
		lowerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
	    parentPanel.add(lowerPanel);
	    
	    // return button
	    JButton back = new JButton("Return to Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// close frame and open desired frame
				dispose();
				CustomerMenu a = new CustomerMenu();
				a.setVisible(true);
			}
			
		});
	    lowerPanel.add(back);
	    lowerPanel.add(Box.createVerticalStrut(30));
		
	    // labels and text boxes
		lowerPanel.add(new JLabel("First Name"));
		firstNameField = new JTextField(30);
		firstNameField.setColumns(15);
		lowerPanel.add(firstNameField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		lowerPanel.add(new JLabel("Last Name"));
		lastNameField = new JTextField(30);
		lastNameField.setColumns(15);
		lowerPanel.add(lastNameField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		// set to only numeric input
		lowerPanel.add(new JLabel("Telephone"));
		telephoneField = new JTextField(10);
		telephoneField.setColumns(15);
		((AbstractDocument) telephoneField.getDocument()).setDocumentFilter(new NumberFilter(12));
		lowerPanel.add(telephoneField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Email Address"));
		emailField = new JTextField(30);
		emailField.setColumns(15);
		lowerPanel.add(emailField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Address"));
		addressField = new JTextField(30);
		addressField.setColumns(15);
		lowerPanel.add(addressField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		// clear text boxes
		JButton reset = new JButton("Reset");
		
		reset.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        firstNameField.setText("");
		        lastNameField.setText("");
		        telephoneField.setText("");
		        emailField.setText("");
		        addressField.setText("");
		    }
		    
		});
		lowerPanel.add(reset);
		
		lowerPanel.add(Box.createVerticalStrut(15));
		
		JButton submit = new JButton("Submit");
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		submit.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	// check if empty
		    	String firstname = firstNameField.getText();
				String lastname = lastNameField.getText();
				String telephone = telephoneField.getText();
				String email = emailField.getText();
				String address = addressField.getText();
				if(!firstname.equals("") && !lastname.equals("") && !telephone.equals("") && !address.equals("")) {
				
			    	Matcher matcher = pattern.matcher(email);
			    	if(matcher.matches()) {
			    		// prompt confirmation
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
				    			// declare variables for columns
						    	final String DATABASEURL = "jdbc:mysql://localhost/db1";
								Connection connection = null ;
								PreparedStatement pstat = null;
								String firstname = firstNameField.getText();
								String lastname = lastNameField.getText();
								String telephone = telephoneField.getText();
								String email = emailField.getText();
								String address = addressField.getText();
								int i=0;
								try {
									// connect to database and enter data
									connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
									pstat = connection.prepareStatement("INSERT INTO customers (FirstName, LastName, Telephone, Email, Address)VALUES (?,?,?,?, ?)");
									pstat . setString (1, firstname );
									pstat . setString (2, lastname);
									pstat . setString (3, telephone);
									pstat . setString (4, email);
									pstat . setString (5, address);
		
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
				                    status.setText("Customer Added");
				                    firstNameField.setText("");
				                    lastNameField.setText("");
				                    telephoneField.setText("");
				                    emailField.setText("");
				                    addressField.setText("");
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
								firstNameField.setText("");
			                    lastNameField.setText("");
			                    telephoneField.setText("");
			                    emailField.setText("");
			                    addressField.setText("");
								lowerPanel.revalidate();
								lowerPanel.repaint();
								
							}
				    		
				    		
				    	});
				    	lowerPanel.revalidate();
				    	lowerPanel.repaint();
			    	}
			    
				    else {
				    	JLabel incorrect = new JLabel("Invalid Email");
				    	Timer timer = new Timer(1000, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								incorrect.setText("");	
							}
	                    });
	                    // run timer once
	                    timer.setRepeats(false); 
	                    timer.start(); 
	                    lowerPanel.add(incorrect);
		                lowerPanel.revalidate();
		                lowerPanel.repaint();
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
		lowerPanel.add(Box.createVerticalStrut(15));
	}

}
