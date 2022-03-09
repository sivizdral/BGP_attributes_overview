

import com.ireasoning.protocol.snmp.SnmpConst;
import com.ireasoning.protocol.snmp.*;
import com.ireasoning.protocol.snmp.SnmpTableModel;
import javax.swing.table.*;

import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.*;

public class Projekat {
	static int portNumber = 161;
	static String communityValue = "si2019";
	
	public static void showTable(TableModel model)
    {
        JFrame frame = new JFrame();
        JTable table = new JTable(model);
        table.setGridColor(Color.DARK_GRAY);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.YELLOW);
        table.setBackground(Color.LIGHT_GRAY);
        table.setRowHeight(50);
        table.removeColumn(table.getColumn("bgpPeerAdminStatus"));
        table.removeColumn(table.getColumn("bgpPeerLocalAddr"));
        table.removeColumn(table.getColumn("bgpPeerLocalPort"));
        table.removeColumn(table.getColumn("bgpPeerRemotePort"));
        table.removeColumn(table.getColumn("bgpPeerInTotalMessages"));
        table.removeColumn(table.getColumn("bgpPeerOutTotalMessages"));
        table.removeColumn(table.getColumn("bgpPeerLastError"));
        table.removeColumn(table.getColumn("bgpPeerFsmEstablishedTransitions"));
        table.removeColumn(table.getColumn("bgpPeerFsmEstablishedTime"));
        table.removeColumn(table.getColumn("bgpPeerConnectRetryInterval"));
        table.removeColumn(table.getColumn("bgpPeerHoldTime"));
        table.removeColumn(table.getColumn("bgpPeerHoldTimeConfigured"));
        table.removeColumn(table.getColumn("bgpPeerKeepAliveConfigured"));
        table.removeColumn(table.getColumn("bgpPeerMinASOriginationInterval"));
        table.removeColumn(table.getColumn("bgpPeerMinRouteAdvertisementInterval"));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(7).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(8).setCellRenderer( centerRenderer );
        table.setAutoCreateColumnsFromModel(true);
        JScrollPane pane = new JScrollPane(table);
        frame.getContentPane().add(pane);
        frame.setSize(1600, 700);
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter()
        {
          public void windowClosing(java.awt.event.WindowEvent e)
          {
            System.exit(0);
          }
        });
    }
	
	
	public static void main(String[] args) {
		try {
			SnmpSession.loadMib("BGP4-MIB");
		} catch (IOException e1) {
			System.out.println("Doslo je do greske prilikom ucitavanja MIB-a!");
			e1.printStackTrace();
		} catch (ParseException e1) {
			System.out.println("Doslo je do greske prilikom ucitavanja MIB-a!");
			e1.printStackTrace();
		}
		
		SnmpSession newSession = null;
		
		try {
			newSession = new SnmpSession("192.168.122.100", portNumber, communityValue, communityValue, SnmpConst.SNMPV2);
		} catch (IOException e) {
			System.out.println("Doslo je do greske pri kreiranju SNMP sesije!");
			e.printStackTrace();
		}
		
		SnmpTableModel table = null;
		
		try {
			table = newSession.snmpGetTable("bgpPeerTable");
		} catch (IOException e) {
			System.out.println("Doslo je do greske pri dohvatanju bgpPeerTable!");
			e.printStackTrace();
		}
		
		if(table == null)
        {
            System.out.println("Doslo je do greske, tabela nije pronadjena!");
            return;
        }
		
        table.setTranslateValue(true);
        table.startPolling(10);
        showTable(table);
   
	}
}
