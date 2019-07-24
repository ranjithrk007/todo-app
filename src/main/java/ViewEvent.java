import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.*;

public class ViewEvent extends JFrame implements ActionListener{
    private String pkey;
    private static ArrayList<Model> ar1=new ArrayList();
    private static ArrayList<String> ar=new ArrayList();
    private JButton  editb = new JButton("Edit");
    private JButton deleteb = new JButton("Delete");
    private JButton nameb = new JButton("Name");
    private JButton dateb = new JButton("Date");
    private JTextField primaryt = new JTextField(10);

    public ViewEvent()
    {
        ViewEvent.toolkit = Toolkit.getDefaultToolkit();
        ViewEvent.timer = new Timer();
        ViewEvent.timer.schedule(new ViewEvent.RemindTask(),0,        //initial delay
                2000);
    }



    private static void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300)
                width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    private static Toolkit toolkit;
    private static Timer timer;

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static class RemindTask extends TimerTask {

        int numWarningBeeps = 3;
        public void run() {
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date date = new Date();
//            System.out.println("alaraam activated");
//            System.out.println(ar);
//            System.out.println("The acurrent time is "+date);
            JFrame frame=new JFrame("Snooze");

            if (ar.contains(date.toString())) {
                toolkit.beep();

                JOptionPane.showMessageDialog(null, "You have a reminder", "Reminder",
                        JOptionPane.ERROR_MESSAGE);
                timer.cancel();
                String[] options = {"Yes! Please.", "No! Not now."};
                int result = JOptionPane.showOptionDialog(
                        frame,
                        "Sure? You want to snooze?",
                        "Snooze",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,     //no custom icon
                        options,  //button titles
                        options[0] //default button
                );
                if(result == JOptionPane.YES_OPTION){
                    try {
                        Thread.sleep(5000);
                        toolkit.beep();
                        JOptionPane.showMessageDialog(null, "You have a reminder", "Reminder",
                                JOptionPane.ERROR_MESSAGE);
                        timer.cancel();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }else if (result == JOptionPane.NO_OPTION){
                    timer.cancel();
                }

                System.out.println("Time's up!");


            }
        }
    }
    public void sort() {
        String[] columnNames = {"Date", "Details"};
        JFrame frame = new JFrame("Total Details");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(32, 179, 179));
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        JTable table = new JTable();
        table.setModel(model);
        resizeColumnWidth(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        String date = "";
        String detail = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "oviya1002");
            Statement stmt = con.createStatement();
            String sql = null;
            System.out.println("sql is ");
            System.out.println("name +"+nameb.getText());
            System.out.println("name +"+dateb.getText());
            if (nameb.getText().equals("Name")) {
                sql = "SELECT * FROM todo ORDER BY details";
            } else if (dateb.getText().equals("Date")) {
                sql = "SELECT * FROM todo ORDER BY dt";
            }
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                date = rs.getString("dt");
                detail = rs.getString("details");
                i++;
                String res[]=date.split(" ");
                String sDate6 = res[2]+"-"+res[1]+"-"+res[5]+" "+res[3];
                SimpleDateFormat formatter6=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                Date date6=formatter6.parse(sDate6);
                ar.add(date6.toString());
                ar1.add(new Model(date6,detail));
                System.out.println("the edited date is "+date6);
            }
            Collections.sort(ar1);
            for(int ip=0;ip<ar1.size();ip++)
            {
                Model temp=ar1.get(ip);
                model.addRow(new Object[]{temp.getDate(), temp.getName()});
            }
            if (dateb.getText().equals("date")) {
                Collections.sort(ar);
                System.out.println(ar);
            }
            if (i < 1) {
                JOptionPane.showMessageDialog(null, "No Record Found", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        panel.add(scroll);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(400, 300);
    }
    void showtable(String word) {

        nameb.setActionCommand("name");
        ActionListener b1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if (cmd.equals("name")) {
                    sort();
                }
            }
        };
        nameb.addActionListener(b1);
        dateb.setActionCommand("date");
        ActionListener b2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if (cmd.equals("date")) {
                    sort();
                }
            }
        };
        dateb.addActionListener(b2);
//        JButton editb ;
//        JButton deleteb = new JButton("Delete");
        String[] columnNames = {"Id","Date", "Details"};
        JFrame frame = new JFrame("Total Details");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(32, 179, 179));
//        primaryt.setBounds();
        panel.add(nameb);
        panel.add(dateb);
        panel.add(primaryt);
        panel.add(deleteb);
        panel.add(editb);
        pkey = primaryt.getText();
//        System.out.println("1111111111111111111111111*************"+primaryt.getText());

        ActionListener del = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String value = primaryt.getText();
//                System.out.println(value);
                ViewEvent viewevent = new ViewEvent();
                String cmd = e.getActionCommand();
                if (cmd.equals("delete")) {
                    dispose();
                    viewevent.delete(value);
                }
            }
        };

        deleteb.addActionListener(del);
        deleteb.setActionCommand("delete");

        ActionListener edi = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Picker picker=new Picker();

                Picker picker = new Picker(1);

                String value = primaryt.getText();
                String cmd = e.getActionCommand();
                if (cmd.equals("edit")){
                    dispose();
                    picker.picker(value);
                }
            }
        };
        editb.addActionListener(edi);
        editb.setActionCommand("edit");
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        JTable table = new JTable();
        table.setModel(model);
        resizeColumnWidth(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        String id ="";
        String date = "";
        String detail = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "oviya1002");
            Statement stmt = con.createStatement();
            String sql = null;
            if (word.equals("total")) {
                sql = "SELECT * FROM todo";
            } else if (word.equals("important")) {
                sql = "SELECT * FROM todo WHERE important = 'important';";
            } else if (word.equals("personal")) {
                sql = "SELECT * FROM todo WHERE personal = 'personal'";
            }
            String day,month,datee,time,year;
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                id = rs.getString("id");
                date = rs.getString("dt");
                detail = rs.getString("details");
                model.addRow(new Object[]{id,date, detail});
                i++;
            }
            if (i < 1) {
                JOptionPane.showMessageDialog(null, "No Record Found", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        panel.add(scroll);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(400, 300);
    }

    private void delete(String value) {
//        System.out.println(primaryt.getText());
        int num = Integer.parseInt(value);

        System.out.println("hello++>"+value);
        if (num>=1 && num<=1000) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "oviya1002");
                Statement stmt = con.createStatement();
                String sql = "DELETE FROM todo WHERE id = " + num;
                int rs = stmt.executeUpdate(sql);
                if (rs==1){
//                    System.out.println("One User Successfully Deleted==>" + rs);
                    JOptionPane.showMessageDialog(null,"Event deleted successfully");
                }

                if (rs < 1) {
                    JOptionPane.showMessageDialog(null, "No Record Found", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Invalid Input");
        }
    }

    class ButtonReaderer extends JButton implements TableCellRenderer{
        public ButtonReaderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row, int col) {
//SET PASSED OBJECT AS BUTTON TEXT
            setText((obj==null) ? "":obj.toString());
            return this;
        }
    }
}