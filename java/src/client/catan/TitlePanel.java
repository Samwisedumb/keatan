package client.catan;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import shared.definitions.CatanColor;

@SuppressWarnings("serial")
public class TitlePanel extends JPanel
{
	private JLabel titleLabel;
	
	public TitlePanel()
	{
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		titleLabel = new JLabel("CS 340: Settlers of Keatan");
		titleLabel.setOpaque(true);
		
		Font font = titleLabel.getFont();
		Font newFont = font.deriveFont(font.getStyle(), 48);
		titleLabel.setFont(newFont);
		
		this.add(titleLabel, BorderLayout.CENTER);
	}
	
	public void setLocalPlayerColor(CatanColor value)
	{
		this.setBackground(value.getJavaColor());
		titleLabel.setBackground(value.getJavaColor());
	}
	
}

