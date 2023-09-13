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

public class DeleteInvoice extends JFrame{
	
	private JTextField customerField;
	private JTextField dateField;
	private JTextField costField;
	
	public DeleteInvoice() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		// page header
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		add(parentPanel);
		
		// page header
		JLabel deleteHeader = new JLabel("Delete Invoice");
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
				// clear frame and open desred frame
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
	        	// add items to combobox
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
	    
		// labels and text boxes set as not editable
		lowerPanel.add(new JLabel("Customer ID"));
		customerField = new JTextField(30);
		customerField.setColumns(15);
		customerField.setEditable(false);
		lowerPanel.add(customerField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		lowerPanel.add(new JLabel("Date of Purchase"));
		dateField = new JTextField(30);
		dateField.setColumns(15);
		dateField.setEditable(false);
		lowerPanel.add(dateField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Cost"));
		costField = new JTextField(30);
		costField.setColumns(15);
		costField.setEditable(false);
		lowerPanel.add(costField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		invoiceIDs.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            int selectedInvoiceId = (int) invoiceIDs.getSelectedItem();

	            final String DATABASEURL = "jdbc:mysql://localhost/db1";
	            Connection connection = null;
	            PreparedStatement pstat = null;

	            try {
	            	// connect to database and get data with matching id
	                connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745");
	                String sql = "SELECT * FROM invoices WHERE InvoiceID=?";
	                pstat = connection.prepareStatement(sql);
	                pstat.setInt(1, selectedInvoiceId);
	                ResultSet rs = pstat.executeQuery();

	                if (rs.next()) {
	                	// populate text boxes
	                    customerField.setText(rs.getString("CustomerID"));
	                    dateField.setText(String.valueOf(rs.getDate("DateOfPurchase")));
	                    costField.setText(String.valueOf(rs.getDouble("TotalCost")));
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
		    			
				    	final String DATABASEURL = "jdbc:mysql://localhost/db1";
						Connection connection = null ;
						PreparedStatement pstat = null;
						int id = (int) invoiceIDs.getSelectedItem();
						int i=0;
						try {
							// delete selected record
							connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
							pstat = connection.prepareStatement("Delete from invoices where InvoiceID=?");
							pstat . setInt(1, id);
							i = pstat .executeUpdate();
							invoiceIDs.removeItem(id);
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
		                    status.setText("Product Deleted");
		                    customerField.setText("");
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
						customerField.setText("");
	                    dateField.setText("");
	                    costField.setText("");
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
