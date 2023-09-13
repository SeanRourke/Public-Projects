package database;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class ViewInvoice extends JFrame{
	
	private JTextField dateField;
	private JTextField costField;
	private static final String datePattern = "\\d{2}-\\d{2}-\\d{4}";
	private static final Pattern pattern = Pattern.compile(datePattern);

	
	public ViewInvoice() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		// page layout
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		add(parentPanel);
		
		// page header
		JLabel viewHeader = new JLabel("View Invoice");
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
				InvoiceMenu a = new InvoiceMenu();
				a.setVisible(true);
			}
			
		});
	    lowerPanel.add(back);
	    lowerPanel.add(Box.createVerticalStrut(30));
	    
	    // combobox of id values
	    JComboBox<Integer> invoiceIDs = new JComboBox<Integer>();
	    lowerPanel.add(invoiceIDs);
	    lowerPanel.add(Box.createVerticalStrut(15));

	    final String DATABASEURL = "jdbc:mysql://localhost/db1";
	    Connection connection = null;
	    PreparedStatement pstat = null;

	    try {
	    	// retrieve id values
	        connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
	        String sql = "SELECT InvoiceID FROM invoices";
	        pstat = connection.prepareStatement(sql);
	        ResultSet rs = pstat.executeQuery();

	        while (rs.next()) {
	            invoiceIDs.addItem(rs.getInt("InvoiceID"));
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
		
	    // labels and textboxes set as not editable
		lowerPanel.add(new JLabel("Date"));
		lowerPanel.add(new JLabel("(dd-mm-yyyy)"));
		dateField = new JTextField(30);
		dateField.setColumns(15);
		dateField.setEditable(false);
		lowerPanel.add(dateField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Cost"));
		costField = new JTextField(30);
		costField.setColumns(15);
		((AbstractDocument) costField.getDocument()).setDocumentFilter(new DecimalFilter(20));
		costField.setEditable(false);
		lowerPanel.add(costField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		JLabel custID = new JLabel("Customer ID");
		lowerPanel.add(custID);
		JComboBox<Integer> customerIDs = new JComboBox<Integer>();
		customerIDs.setEnabled(false);
		lowerPanel.add(customerIDs);
		lowerPanel.add(Box.createVerticalStrut(15));

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
	    } 
		catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		finally {
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
		
		invoiceIDs.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            int selectedInvoiceId = (int) invoiceIDs.getSelectedItem();

	            final String DATABASEURL = "jdbc:mysql://localhost/db1";
	            Connection connection = null;
	            PreparedStatement pstat = null;
	            // get data for selected id
	            try {
	                connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
	                String sql = "SELECT * FROM invoices WHERE InvoiceID=?";
	                pstat = connection.prepareStatement(sql);
	                pstat.setInt(1, selectedInvoiceId);
	                ResultSet rs = pstat.executeQuery();
	                // populate text boxes with data
	                if (rs.next()) {
	                    dateField.setText(String.valueOf(rs.getDate("DateOfPurchase")));
	                    costField.setText(String.valueOf(rs.getDouble("TotalCost")));
	                    customerIDs.setSelectedItem(rs.getInt("CustomerID"));
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
		    	// if editing is disabled, set as enabled and change button text
		    	if (amend.getText().equals("Amend Details")) {
		    		dateField.setEditable(true);
		    		customerIDs.setEnabled(true);
		    		amend.setText("View Details");
		    	}
		    	// if editing is enabled, set as disabled and change button text
		    	else {
		    		dateField.setEditable(false);
		    		customerIDs.setEnabled(false);
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
		    	String name = dateField.getText();
				String cost = costField.getText();
				String date = dateField.getText();				
				if(!name.equals("") && !cost.equals("") && !date.equals("")) {
					
					Matcher matcher = pattern.matcher(date);
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
			    			// variables for columns
					    	final String DATABASEURL = "jdbc:mysql://localhost/db1";
							Connection connection = null ;
							PreparedStatement pstat = null;
							int id = (int) invoiceIDs.getSelectedItem();
							String dateString = dateField.getText();
					    	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					    	java.util.Date utilDate = null;
							try {
								utilDate = format.parse(dateString);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				            Date sqlDate = new Date(utilDate.getTime());
							int customerID = (int) customerIDs.getSelectedItem();
							int i=0;
							try {
			
								// update tables with entered data
								connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
								pstat = connection.prepareStatement("Update invoices SET DateOfPurchase=?, CustomerID=?, where InvoiceID=?");
								pstat . setDate (1, sqlDate );
								pstat . setInt (2, customerID);
								pstat . setDouble (3, id);
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
			                	// reset text fields and remove confirm buttons
			                	lowerPanel.remove(confirmation);
			                	lowerPanel.remove(confirm);
			                	lowerPanel.remove(cancel);
			                    status.setText("Details Amended");
			                    dateField.setText("");
			                    costField.setText("");
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
							dateField.setText("");
		                    costField.setText("");
		                    costField.setText("");
							lowerPanel.revalidate();
							lowerPanel.repaint();
							
						}
			    		
			    		
			    	});
			    	lowerPanel.revalidate();
			    	lowerPanel.repaint();
			    	
			    	}
				    
			    	else {
				    	JLabel incorrect = new JLabel("Invalid Date");
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
