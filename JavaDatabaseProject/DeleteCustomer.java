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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class DeleteCustomer extends JFrame{
	
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField telephoneField;
	private JTextField emailField;
	private JTextField addressField;
	
	public DeleteCustomer() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		// page header
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		add(parentPanel);
		
		JLabel deleteHeader = new JLabel("Delete Customer");
		Font heading = new Font("Arial", Font.BOLD, 24);
		deleteHeader.setFont(heading);
		parentPanel.add(deleteHeader);

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
	    
	    // combobox for id values
	    JComboBox<Integer> customerIDs = new JComboBox<Integer>();
	    lowerPanel.add(customerIDs);
	    lowerPanel.add(Box.createVerticalStrut(15));

	    final String DATABASEURL = "jdbc:mysql://localhost/db1";
	    Connection connection = null;
	    PreparedStatement pstat = null;

	    try {
	        connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
	        String sql = "SELECT CustomerID FROM customers";
	        pstat = connection.prepareStatement(sql);
	        ResultSet rs = pstat.executeQuery();

	        // Add ids to the combobox
	        while (rs.next()) {
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
		
		lowerPanel.add(new JLabel("Telephone"));
		telephoneField = new JTextField(30);
		telephoneField.setColumns(15);
		telephoneField.setEditable(false);
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
	            // get the selected customer ID
	            int selectedCustomerId = (int) customerIDs.getSelectedItem();

	            // retrieve the customer information from the database
	            final String DATABASEURL = "jdbc:mysql://localhost/db1";
	            Connection connection = null;
	            PreparedStatement pstat = null;

	            try {
	                connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
	                String sql = "SELECT * FROM customers WHERE CustomerID=?";
	                pstat = connection.prepareStatement(sql);
	                pstat.setInt(1, selectedCustomerId);
	                ResultSet rs = pstat.executeQuery();

	                // populate the text fields with the retrieved information
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
				
		JButton submit = new JButton("Submit");
		
		submit.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	// prompt comfirmation
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
						int id = (int) customerIDs.getSelectedItem();
						int i=0;
						try {
		
							// connect to database and delete entry with selected id
							connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
							pstat = connection.prepareStatement("Delete from customers where CustomerID=?");
							pstat . setInt(1, id);
							i = pstat .executeUpdate();
							// remove entry from combobox
							customerIDs.removeItem(id);
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
		                    status.setText("Customer Deleted");
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
		                    
		                    //run timer once
		                    timer.setRepeats(false); 
		                    timer.start(); 
		                }
		                // refresh pahe
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
		});
		lowerPanel.add(submit);
		lowerPanel.add(Box.createVerticalStrut(15));
	}

}
