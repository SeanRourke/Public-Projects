package database;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame {
	
	public MainMenu() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		// page header
		panel.add(Box.createVerticalStrut(50));
		JLabel header = new JLabel("Main Menu");
		Font heading = new Font("Arial", Font.BOLD, 24);
		header.setFont(heading);
		header.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(header);
		panel.add(Box.createVerticalStrut(120));
		
		// buttons to branch to each section
		JButton add = new JButton("Customers");
		add.setAlignmentX(CENTER_ALIGNMENT);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				CustomerMenu a = new CustomerMenu();
				a.setVisible(true);
			}
			
		});
		panel.add(add);
		panel.add(Box.createVerticalStrut(30));
		JButton amend = new JButton("Products");
		amend.setAlignmentX(CENTER_ALIGNMENT);
		amend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ProductMenu a = new ProductMenu();
				a.setVisible(true);
			}
			
		});
		panel.add(amend);
		panel.add(Box.createVerticalStrut(30));
		JButton delete = new JButton("Invoices");
		delete.setAlignmentX(CENTER_ALIGNMENT);
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				InvoiceMenu a = new InvoiceMenu();
				a.setVisible(true);
			}
			
		});
		panel.add(delete);
		
		panel.add(Box.createVerticalStrut(50));
		JButton logout = new JButton("Logout");
		logout.setAlignmentX(CENTER_ALIGNMENT);
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login a = new Login();
				a.setVisible(true);
			}
			
		});
		panel.add(logout);
		
		add(panel);
	}

}
