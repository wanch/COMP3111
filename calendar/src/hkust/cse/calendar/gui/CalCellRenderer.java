package hkust.cse.calendar.gui;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class CalCellRenderer extends DefaultTableCellRenderer

{

	private int r;

	private int c;

	//public CalCellRenderer(Object value) {
	public CalCellRenderer(Object value, boolean exist){
		if (value != null) {
			setForeground(Color.red);
		} else
			setForeground(Color.black);
		//setBackground(Color.white);
		
		if (exist == true){
			setBackground(Color.blue);
		}else{
			setBackground(Color.white);
		}
		
		setHorizontalAlignment(SwingConstants.RIGHT);
		setVerticalAlignment(SwingConstants.TOP);
	}
	
	//public static int row=-1,col=-1,state;

	/*public CalCellRenderer(Object value,int r,int c) {
		if (value != null) {
			setForeground(Color.red);
		} else
			setForeground(Color.black);
		if(r==row&&c==col)
		{
			if(state==1)setBackground(Color.yellow);
			//else if(state==2)setBackground(Color.yellow);
			//else if(state==3)setBackground(Color.blue);
			//else if(state==4)setBackground(Color.red);
			else setBackground(Color.white);
		}
		else setBackground(Color.white);
		
		setHorizontalAlignment(SwingConstants.RIGHT);
		setVerticalAlignment(SwingConstants.TOP);
	}*/

	// ADD
	/*public CalCellRenderer(Object value, boolean exist){
		if (exist == true){
			setBackground(Color.blue);
		}else{
			setBackground(Color.white);
		}
	}*/
	
	public int row() {
		return r;
	}

	public int col() {
		return c;
	}

}
