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
import javax.swing.text.AbstractDocument;

public class AddOrderItem extends JFrame {
	
	private JTextField quantityField;
	private JTextField costField;
	
	public AddOrderItem() {
		
		// frame layout
				setSize(500, 700);
				setLocation(400, 50);
				setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
				
				JPanel parentPanel = new JPanel();
				parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
				add(parentPanel);
				
				// page header
				JLabel addHeader = new JLabel("Add Item");
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
						InvoiceMenu a = new InvoiceMenu();
						a.setVisible(true);
					}
					
				});
			    lowerPanel.add(back);
			    lowerPanel.add(Box.createVerticalStrut(30));
			    
			    JLabel invoiceID = new JLabel("Invoice ID");
			    lowerPanel.add(invoiceID);
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
			    
			    JLabel productID = new JLabel("Product ID");
			    lowerPanel.add(productID);
			    JComboBox<Integer> productIDs = new JComboBox<Integer>();
				lowerPanel.add(productIDs);
				lowerPanel.add(Box.createVerticalStrut(15));

				try {
					// retrieve id values
				    connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
				    String sql = "SELECT ProductID FROM products";
				    pstat = connection.prepareStatement(sql);
			        ResultSet rs = pstat.executeQuery();

			        while (rs.next()) {
			        	// add ids to combobox
				        productIDs.addItem(rs.getInt("ProductID"));
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
				
				lowerPanel.add(new JLabel("Quanity"));
				quantityField = new JTextField(30);
				quantityField.setColumns(15);
				((AbstractDocument) quantityField.getDocument()).setDocumentFilter(new NumberFilter(5));
				lowerPanel.add(quantityField);
				lowerPanel.add(Box.createVerticalStrut(15));
				
				lowerPanel.add(new JLabel("Cost"));
				costField = new JTextField(30);
				costField.setColumns(15);
				lowerPanel.add(costField);
				lowerPanel.add(Box.createVerticalStrut(15));
				
				JButton calculate = new JButton("Calculate Cost");
				
				calculate.addActionListener(new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				    	 try {
				    		 	Connection connection = null;
								connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );

				    	        // Get the quantity entered by the user
				    	        int quantity = Integer.parseInt(quantityField.getText());
				    	        
				    	        // Get the price of the selected item from the database
				    	        int selectedProduct = (int) productIDs.getSelectedItem();
				    	        PreparedStatement statement = connection.prepareStatement("SELECT Cost FROM products WHERE ProductID= ?");
				    	        statement.setInt(1, selectedProduct);
				    	        ResultSet result = statement.executeQuery();
				    	        result.next();
				    	        double iPrice = result.getDouble("Cost");
				    	        
				    	        // Calculate the total cost
				    	        double tPrice = iPrice * quantity;
				    	        
				    	        // Display the total cost
				    	        costField.setText(String.valueOf(tPrice));
				    	        
				    	    } catch (NumberFormatException | SQLException ex) {
				    	        ex.printStackTrace();
				    	    }
				    	}				  
				});
				
				lowerPanel.add(calculate);
				lowerPanel.add(Box.createVerticalStrut(15));
				
				// clear text boxes
				JButton reset = new JButton("Reset");
				
				reset.addActionListener(new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        quantityField.setText("");
				        costField.setText("");
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
				    	String quantity = quantityField.getText();
						if(!quantity.equals("")) {
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
									int invID = (int) invoiceIDs.getSelectedItem();
									int prodID = (int) productIDs.getSelectedItem();
									String quantity = quantityField.getText();
									int quantityNum = Integer.parseInt(quantity);
									String cost = costField.getText();
									double costNum = Double.parseDouble(cost);
									int i=0;
									try {
										// connect to database and enter data
										connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
										pstat = connection.prepareStatement("INSERT INTO orders (InvoiceID, ProductID, Quantity, ItemCost)VALUES (?,?,?,?)");
										pstat . setInt (1, invID );
										pstat . setInt (2, prodID);
										pstat . setInt (3, quantityNum);
										pstat . setDouble (4, costNum);
										i = pstat .executeUpdate();

										}
									catch(SQLException sqlException){
											sqlException . printStackTrace () ;
										}
										finally {
											try {
												pstat . close();
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
					                    status.setText("Item Added");
					                    quantityField.setText("");
					                    costField.setText("");
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
					    	
					    	confirm.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									
									try {
							            Connection connection = null;
							            connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
							            
							            // Get the selected invoice ID
							            int selectedInvoice = (int) invoiceIDs.getSelectedItem();
							            
							            // Retrieve the TotalCost from the invoices table
							            PreparedStatement statement = connection.prepareStatement("SELECT TotalCost FROM invoices WHERE InvoiceID = ?");
							            statement.setInt(1, selectedInvoice);
							            ResultSet result = statement.executeQuery();
							            result.next();
							            double totalCost = result.getDouble("TotalCost");
							            
							            // Add the value of costField to the TotalCost
							            double newTotalCost = totalCost + Double.parseDouble(costField.getText());
							            
							            // Update the TotalCost in the invoices table
							            PreparedStatement updateStatement = connection.prepareStatement("UPDATE invoices SET TotalCost = ? WHERE InvoiceID = ?");
							            updateStatement.setDouble(1, newTotalCost);
							            updateStatement.setInt(2, selectedInvoice);
							            updateStatement.executeUpdate();
							            							            
							        } catch (NumberFormatException | SQLException ex) {
							            ex.printStackTrace();
							        }
										
								}
								
							});
					    	
					    	cancel.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									
									lowerPanel.remove(confirmation);
									lowerPanel.remove(confirm);
									lowerPanel.remove(cancel);
									quantityField.setText("");
				                    costField.setText("");
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
