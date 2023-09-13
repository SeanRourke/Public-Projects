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

public class CustomerMenu extends JFrame {
	
	public CustomerMenu() {
		// frame layout
		setSize(500, 700);
		setLocation(400, 50);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		// page header
		panel.add(Box.createVerticalStrut(50));
		JLabel header = new JLabel("Customers");
		Font heading = new Font("Arial", Font.BOLD, 24);
		header.setFont(heading);
		header.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(header);
		panel.add(Box.createVerticalStrut(120));
		
		// return button
		JButton add = new JButton("Add Customer");
		add.setAlignmentX(CENTER_ALIGNMENT);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// clear frame and open desired frame
				dispose();
				AddCustomer a = new AddCustomer();
				a.setVisible(true);
			}
			
		});
		panel.add(add);
		
		// buttons for each option
		panel.add(Box.createVerticalStrut(30));
		JButton amend = new JButton("View/Amend Customer");
		amend.setAlignmentX(CENTER_ALIGNMENT);
		amend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ViewCustomer a = new ViewCustomer();
				a.setVisible(true);
			}
			
		});
		panel.add(amend);
		panel.add(Box.createVerticalStrut(30));
		JButton delete = new JButton("Delete Customer");
		delete.setAlignmentX(CENTER_ALIGNMENT);
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				DeleteCustomer a = new DeleteCustomer();
				a.setVisible(true);
			}
			
		});
		panel.add(delete);
		panel.add(Box.createVerticalStrut(100));
		JButton back = new JButton("Return to Main Menu");
		back.setAlignmentX(CENTER_ALIGNMENT);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainMenu a = new MainMenu();
				a.setVisible(true);
			}
			
		});
		panel.add(back);
		
		add(panel);
	}

}
