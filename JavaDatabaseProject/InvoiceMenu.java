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

public class InvoiceMenu extends JFrame {
	
	public InvoiceMenu() {
		
		setSize(500, 700);
		setLocation(400, 50);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(Box.createVerticalStrut(50));
		JLabel header = new JLabel("Invoices");
		Font heading = new Font("Arial", Font.BOLD, 24);
		header.setFont(heading);
		header.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(header);
		panel.add(Box.createVerticalStrut(120));
		
		JButton add = new JButton("Add Invoice");
		add.setAlignmentX(CENTER_ALIGNMENT);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				AddInvoice a = new AddInvoice();
				a.setVisible(true);
			}
			
		});
		panel.add(add);
		panel.add(Box.createVerticalStrut(30));
		JButton amend = new JButton("View/Amend Invoice");
		amend.setAlignmentX(CENTER_ALIGNMENT);
		amend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ViewInvoice a = new ViewInvoice();
				a.setVisible(true);
			}
			
		});
		panel.add(amend);
		panel.add(Box.createVerticalStrut(30));
		JButton delete = new JButton("Delete Invoice");
		delete.setAlignmentX(CENTER_ALIGNMENT);
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				DeleteInvoice a = new DeleteInvoice();
				a.setVisible(true);
			}
			
		});
		panel.add(delete);
		panel.add(Box.createVerticalStrut(30));
		JButton addItem = new JButton("Add Item to Invoice");
		addItem.setAlignmentX(CENTER_ALIGNMENT);
		addItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				AddOrderItem a = new AddOrderItem();
				a.setVisible(true);
			}
			
		});
		panel.add(addItem);
		
		panel.add(Box.createVerticalStrut(30));
		JButton deleteItem = new JButton("Delete Item from Invoice");
		deleteItem.setAlignmentX(CENTER_ALIGNMENT);
		deleteItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				DeleteOrderItem a = new DeleteOrderItem();
				a.setVisible(true);
			}
			
		});
		panel.add(deleteItem);
		
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
