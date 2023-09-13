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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;

public class ViewCustomer extends JFrame{
	
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField telephoneField;
	private JTextField emailField;
	private JTextField addressField;
	private static final String emailPattern =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(emailPattern);
	
	public ViewCustomer() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		// page header
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		add(parentPanel);
		
		JLabel viewHeader = new JLabel("View Customer");
		Font heading = new Font("Arial", Font.BOLD, 24);
		viewHeader.setFont(heading);
		parentPanel.add(viewHeader);

		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));
		lowerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
	    parentPanel.add(lowerPanel);
	    
	    // return button
	    JButton back = new JButton("Return to Menu");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// clear frame and open desired frame
				dispose();
				CustomerMenu a = new CustomerMenu();
				a.setVisible(true);
			}
			
		});
	    lowerPanel.add(back);
	    lowerPanel.add(Box.createVerticalStrut(30));
	    
	    // combobox of id values
	    JComboBox<Integer> customerIDs = new JComboBox<Integer>();
	    lowerPanel.add(customerIDs);
	    lowerPanel.add(Box.createVerticalStrut(15));

	    final String DATABASEURL = "jdbc:mysql://localhost/db1";
	    Connection connection = null;
	    PreparedStatement pstat = null;

	    try {
	    	// retrieve id values
	        connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
	        String sql = "SELECT CustomerID FROM customers";
	        pstat = connection.prepareStatement(sql);
	        ResultSet rs = pstat.executeQuery();

	        while (rs.next()) {
	        	// add ids to combobox
	            customerIDs.addItem(rs.getInt("CustomerID"));
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } finally {
	        try {
	            if (pstat != null) {
	                pstat.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
		
	    // labels and text boxes set as not editable
		lowerPanel.add(new JLabel("First Name"));
		firstNameField = new JTextField(30);
		firstNameField.setColumns(15);
		firstNameField.setEditable(false);
		lowerPanel.add(firstNameField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		lowerPanel.add(new JLabel("Last Name"));
		lastNameField = new JTextField(30);
		lastNameField.setColumns(15);
		lastNameField.setEditable(false);
		lowerPanel.add(lastNameField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		// set to only numeric input
		lowerPanel.add(new JLabel("Telephone"));
		telephoneField = new JTextField(30);
		telephoneField.setColumns(15);
		telephoneField.setEditable(false);
		((AbstractDocument) telephoneField.getDocument()).setDocumentFilter(new NumberFilter(12));
		lowerPanel.add(telephoneField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Email Address"));
		emailField = new JTextField(30);
		emailField.setColumns(15);
		emailField.setEditable(false);
		lowerPanel.add(emailField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Address"));
		addressField = new JTextField(30);
		addressField.setColumns(15);
		addressField.setEditable(false);
		lowerPanel.add(addressField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		customerIDs.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            int selectedCustomerId = (int) customerIDs.getSelectedItem();

	            final String DATABASEURL = "jdbc:mysql://localhost/db1";
	            Connection connection = null;
	            PreparedStatement pstat = null;

	            try {
	            	// get data of selected id
	                connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
	                String sql = "SELECT * FROM customers WHERE CustomerID=?";
	                pstat = connection.prepareStatement(sql);
	                pstat.setInt(1, selectedCustomerId);
	                ResultSet rs = pstat.executeQuery();

	                if (rs.next()) {
	                    firstNameField.setText(rs.getString("FirstName"));
	                    lastNameField.setText(rs.getString("LastName"));
	                    telephoneField.setText(rs.getString("Telephone"));
	                    emailField.setText(rs.getString("Email"));
	                    addressField.setText(rs.getString("Address"));
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            } finally {
	                try {
	                    if (pstat != null) {
	                        pstat.close();
	                    }
	                    if (connection != null) {
	                        connection.close();
	                    }
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                }
	            }
	        }
	    });
		
		JButton amend = new JButton("Amend Details");
		
		amend.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	
		    	// if editing is disabled, set to enabled and change button text
		    	if (amend.getText().equals("Amend Details")) {
		    		firstNameField.setEditable(true);
		    		lastNameField.setEditable(true);
		    		telephoneField.setEditable(true);
		    		emailField.setEditable(true);
		    		addressField.setEditable(true);
		    		amend.setText("View Details");
		    	}
		    	// if editing is enabled, set to disabled and change button text
		    	else {
		    		firstNameField.setEditable(false);
		    		lastNameField.setEditable(false);
		    		telephoneField.setEditable(false);
		    		emailField.setEditable(false);
		    		addressField.setEditable(false);
		    		amend.setText("Amend Details");
		    	}
		    }
		});
		
		lowerPanel.add(amend);
		
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		JButton submit = new JButton("Submit");
		
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
		    		// check email format
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
				    			// variables for info
						    	final String DATABASEURL = "jdbc:mysql://localhost/db1";
								Connection connection = null ;
								PreparedStatement pstat = null;
								int id = (int) customerIDs.getSelectedItem();
								String firstname = firstNameField.getText();
								String lastname = lastNameField.getText();
								String telephone = telephoneField.getText();
								String email = emailField.getText();
								String address = addressField.getText();
								int i=0;
								try {
				
									// connect to database and run sql query
									connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
									pstat = connection.prepareStatement("Update customers SET FirstName=?, LastName=?, Telephone=?, Email=?, Address=? where CustomerID=?");
									pstat . setString (1, firstname );
									pstat . setString (2, lastname);
									pstat . setString (3, telephone);
									pstat . setString (4, email);
									pstat . setString (5, address);
									pstat . setInt(6, id);
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
				                	// reset text boxes and remove confirm buttons
				                	lowerPanel.remove(confirmation);
				                	lowerPanel.remove(confirm);
				                	lowerPanel.remove(cancel);
				                    status.setText("Details Amended");
				                    firstNameField.setText("");
				                    lastNameField.setText("");
				                    telephoneField.setText("");
				                    emailField.setText("");
				                    addressField.setText("");
				                    // display message for one second
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
