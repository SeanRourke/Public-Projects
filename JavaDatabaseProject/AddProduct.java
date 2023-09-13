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
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;

public class AddProduct extends JFrame{
	
	private JTextField nameField;
	private JTextField descriptionField;
	private JTextField costField;
	private JTextField stockField;
	
	public AddProduct() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		JPanel parentPanel = new JPanel();
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		add(parentPanel);
		
		// page header
		JLabel addHeader = new JLabel("Add Product");
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
				ProductMenu a = new ProductMenu();
				a.setVisible(true);
			}
			
		});
	    lowerPanel.add(back);
	    lowerPanel.add(Box.createVerticalStrut(30));
		
	    // labels and text boxes
		lowerPanel.add(new JLabel("Name"));
		nameField = new JTextField(30);
		nameField.setColumns(15);
		lowerPanel.add(nameField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		
		lowerPanel.add(new JLabel("Description"));
		descriptionField = new JTextField(30);
		descriptionField.setColumns(15);
		lowerPanel.add(descriptionField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Cost"));
		costField = new JTextField(10);
		costField.setColumns(15);
		((AbstractDocument) costField.getDocument()).setDocumentFilter(new DecimalFilter(10));
		lowerPanel.add(costField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		lowerPanel.add(new JLabel("Stock"));
		stockField = new JTextField(30);
		stockField.setColumns(15);
		((AbstractDocument) stockField.getDocument()).setDocumentFilter(new NumberFilter(5));
		lowerPanel.add(stockField);
		lowerPanel.add(Box.createVerticalStrut(15));
		
		// clear text boxes
		JButton reset = new JButton("Reset");
		
		reset.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        nameField.setText("");
		        descriptionField.setText("");
		        costField.setText("");
		        stockField.setText("");
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
			    			
			    			// declare variables for columns
					    	final String DATABASEURL = "jdbc:mysql://localhost/db1";
							Connection connection = null ;
							PreparedStatement pstat = null;
							String name = nameField.getText();
							String description = descriptionField.getText();
							String cost = costField.getText();
							double costNum = Double.parseDouble(cost);
							String stock = stockField.getText();
							int stockNum = Integer.parseInt(stock);
							int i=0;
							try {
								// connect to database and enter data
								connection = DriverManager.getConnection(DATABASEURL, "root", "Sean@8745" );
								pstat = connection.prepareStatement("INSERT INTO products (ProductName, ProductDescription, Cost, Stock)VALUES (?,?,?,?)");
								pstat . setString (1, name );
								pstat . setString (2, description);
								pstat . setDouble (3, costNum);
								pstat . setInt (4, stockNum);
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
			                    status.setText("Product Added");
			                    nameField.setText("");
			                    descriptionField.setText("");
			                    costField.setText("");
			                    stockField.setText("");
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
