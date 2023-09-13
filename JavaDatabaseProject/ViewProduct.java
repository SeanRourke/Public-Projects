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

public class ViewProduct extends JFrame{
	
	private JTextField nameField;
	private JTextField descriptionField;
	private JTextField costField;
	private JTextField stockField;
	
	public ViewProduct() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		// page layout
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		add(parentPanel);
		
		// page header
		JLabel viewHeader = new JLabel("View Product");
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
				ProductMenu a = new ProductMenu();
				a.setVisible(true);
			}
			
		});
	    lowerPanel.add(back);
	    lowerPanel.add(Box.createVerticalStrut(30));
	    
	    // combobox of id values
	    JComboBox<Integer> productIDs = new JComboBox<Integer>();
	    lowerPanel.add(productIDs);
	    lowerPanel.add(Box.createVerticalStrut(15));

	    final String DATABASEURL = "jdbc:mysql://localhost/db1";
	    Connection connection = null;
	    PreparedStatement pstat = null;

	    try {
	    	// retrieve id values
	        connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
	        String sql = "SELECT ProductID FROM products";
	        pstat = connection.prepareStatement(sql);
	        ResultSet rs = pstat.executeQuery();

	        while (rs.next()) {
	            productIDs.addItem(rs.getInt("ProductID"));
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
		lowerPanel.add(new JLabel("Name"));
		nameField = new JTextField(30);
		nameField.setColumns(15);
		nameField.setEditable(false);
		lowerPanel.add(nameField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		lowerPanel.add(new JLabel("Description"));
		descriptionField = new JTextField(30);
		descriptionField.setColumns(15);
		descriptionField.setEditable(false);
		lowerPanel.add(descriptionField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Cost"));
		costField = new JTextField(30);
		costField.setColumns(15);
		((AbstractDocument) costField.getDocument()).setDocumentFilter(new DecimalFilter(10));
		costField.setEditable(false);
		lowerPanel.add(costField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Stock"));
		stockField = new JTextField(30);
		stockField.setColumns(15);
		((AbstractDocument) stockField.getDocument()).setDocumentFilter(new NumberFilter(5));
		stockField.setEditable(false);
		lowerPanel.add(stockField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		productIDs.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            int selectedProductId = (int) productIDs.getSelectedItem();

	            final String DATABASEURL = "jdbc:mysql://localhost/db1";
	            Connection connection = null;
	            PreparedStatement pstat = null;
	            // get data for selected id
	            try {
	                connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
	                String sql = "SELECT * FROM products WHERE ProductID=?";
	                pstat = connection.prepareStatement(sql);
	                pstat.setInt(1, selectedProductId);
	                ResultSet rs = pstat.executeQuery();
	                // populate text boxes with data
	                if (rs.next()) {
	                    nameField.setText(rs.getString("ProductName"));
	                    descriptionField.setText(rs.getString("ProductDescription"));
	                    costField.setText(String.valueOf(rs.getDouble("Cost")));
	                    stockField.setText(String.valueOf(rs.getInt("Stock")));
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
		    		nameField.setEditable(true);
		    		descriptionField.setEditable(true);
		    		costField.setEditable(true);
		    		stockField.setEditable(true);
		    		amend.setText("View Details");
		    	}
		    	// if editing is enabled, set as disabled and change button text
		    	else {
		    		nameField.setEditable(false);
		    		descriptionField.setEditable(false);
		    		costField.setEditable(false);
		    		stockField.setEditable(false);
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
		    	String name = nameField.getText();
				String description = descriptionField.getText();
				String cost = costField.getText();
				String stock = stockField.getText();
				if(!name.equals("") && !description.equals("") && !cost.equals("") && !stock.equals("")) {
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
							int id = (int) productIDs.getSelectedItem();
							String name = nameField.getText();
							String description = descriptionField.getText();
							String cost = costField.getText();
							double costNum = Double.parseDouble(cost);
							String stock = stockField.getText();
							int stockNum = Integer.parseInt(stock);
							int i=0;
							try {
			
								// update tables with entered data
								connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
								pstat = connection.prepareStatement("Update products SET ProductName=?, ProductDescription=?, Cost=?, Stock=? where ProductID=?");
								pstat . setString (1, name );
								pstat . setString (2, description);
								pstat . setDouble (3, costNum);
								pstat . setInt (4, stockNum);
								pstat . setInt(5, id);
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
			                    nameField.setText("");
			                    descriptionField.setText("");
			                    costField.setText("");
			                    stockField.setText("");
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
							nameField.setText("");
		                    descriptionField.setText("");
		                    costField.setText("");
		                    stockField.setText("");
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
