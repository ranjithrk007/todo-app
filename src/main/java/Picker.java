import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
class Picker {
    int counter=99;
    Picker()
    {

    }
    Picker(int n)
    {
        this.counter=n;
        System.out.println(counter);
    }
    Connection con=null;
    JPanel p = new JPanel();
    //    private String getdate="";
//    private String getdetail="";
    JTextField text = new JTextField(20);
    JTextArea text1 = new JTextArea(5, 20);
    JCheckBox importantc = new JCheckBox("Important Event",false);
    JCheckBox personalc = new JCheckBox("personal Event", false);
    String detail = text.getText();
    String detail1 = text1.getText();
    String event = importantc.getText();
    String event1 = personalc.getText();
    String query = "";
    private int number;
    public static String result="";
    public  void picker(String value) {


        String getdate = "";
        String getdetail = "";
        String getinfo = "";
        String getinfo1 = "";
        number = Integer.parseInt(value);
        JLabel label = new JLabel("Selected Date:");
        JLabel label1 = new JLabel("Enter the Details:");


//        text1.setText(getdetail);

        importantc.setText("important");
        personalc.setText("personal");
        JButton b = new JButton("Popup");
        JButton b1 = new JButton("Save Details");
        final DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setFormats(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM));
        dateTimePicker.setTimeFormat(DateFormat.getTimeInstance(DateFormat.MEDIUM));
        final Date date = new Date();

        if (number>=1) {

//            String getdate;
//            String getdetail;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "oviya1002");
                detail = text.getText();
                detail1 = text1.getText();
                event = importantc.getText();
                event1 = personalc.getText();
                query = "";
                if (number >= 1) {
                    query = "SELECT * FROM todo WHERE id =" + number;
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    while (rs.next()) {
                        getdate = rs.getString("dt");
                        getdetail = rs.getString("details");
                        getinfo = rs.getString("important");
                        getinfo1 = rs.getString("personal");
                        //  System.out.println(importantc.isEnabled());
//                        System.out.println(getinfo);
                        if (getinfo.equals("important" )){
//                            System.out.println("notnumm");
                            importantc.setSelected(true);

                        }else if (getinfo1.equals("personal")){
                            personalc.setSelected(true);
//                            System.out.println("not null");
                        }
                        text.setText(getdate);
                        text1.setText(getdetail);
//                        System.out.println(getdate + " *************************" + getdetail);
                    }
                    event="null";
                    event1="null";
                    detail = text.getText();
                    detail1 = text1.getText();
                    event = importantc.getText();
                    event1 = personalc.getText();
                    System.out.println(event+" "+event1);


//                     b1.addActionListener(new ActionListener() {
//                         @Override
//                         public void actionPerformed(ActionEvent e) {
//
//                             try {
//
//                                 if (importantc.isSelected()==true){
//                                     System.out.println("selected f1111gfd");
//                                     event = "important";
//
//                                 }else {
//                                     System.out.println("selected fgfd");
//                                     event = "null";
//                                 }
//                                 if (personalc.isSelected()==true){
//                                     System.out.println("selected dddd");
//                                     event1 = "personal";
//                                 }else {
//                                     System.out.println("selected fddgfd");
//                                     event1 = "null";
//                                 }
//                                 query = "UPDATE todo SET dt = "+detail+ ", detail = "+detail1 +", important ="+ event +", personal = "+event1+" WHERE id ="+number;
//                                 System.out.println(query);
//                                 PreparedStatement updated = con.prepareStatement(query);
//                                 System.out.println("detail"+detail);
//                                 System.out.println("detail1"+detail1);
//                                 System.out.println("event"+event);
//                                 System.out.println("event1"+event1);
//
//                                 int set = updated.executeUpdate();
//                                 if (set > 0) {
//                                     JOptionPane.showMessageDialog(null, "updated successfull");
//                                 } else {
//                                     JOptionPane.showMessageDialog(null, "insert valid data");
//                                 }
//                             } catch (SQLException ex) {
//                                 ex.printStackTrace();
//                             }
//
//
//                         }
//                     });

//                    query = "UPDATE todo SET (dt,details,important,personal)values(?,?,?,?) WHERE id ="+number;
//                    PreparedStatement updated = con.prepareStatement(query);
//                    updated.setString(1,detail);
//                    updated.setString(2,detail1);
//                    updated.setString(3, event);
//                    updated.setString(4,event1);
//                    int set = updated.executeUpdate();
//                    if (set > 0) {
//                        JOptionPane.showMessageDialog(null, "updated successfull");
//                    } else {
//                        JOptionPane.showMessageDialog(null, "insert valid data");
//                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//        b2.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    Class.forName("com.mysql.jdbc.Driver");
//                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "root");
//                    Statement stmt = conn.createStatement();
//                    ResultSet rs = stmt.executeQuery("select * from plan order by details");
//                    StringBuffer st = new StringBuffer();
//                    while (rs.next()) {
//                        st.append(rs.getString(1) + "  " + rs.getString(2));
//                        st.append("\n");
//                    }
//                    text2.setText(st.toString());
//                    conn.close();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });

        p.add(label);
        p.add(text);
        p.add(b);
        p.add(label1);
        p.add(text1);
        p.add(b1);
        p.add(importantc);
        p.add(personalc);
        JFrame f = new JFrame();
        f.setSize(400, 400);
        f.add(p);

        f.setVisible(true);
        ActionListener at1=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String getdate;
//                String getdetail;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "oviya1002");
                    detail = text.getText();
                    detail1 = text1.getText();
                    event=null;
                    event1 = null;
                    if (importantc.isSelected()==true){
                        event = "important";

                    }else {
                        event = "null";
                    }
                    if (personalc.isSelected()==true){
                        event1 = "personal";
                    }else {
                        event1 = "null";
                    }
//                    String query="";
//                    if (number>=1 ){
//                        query = "SELECT * FROM todo WHERE id ="+number;
//                        Statement stmt = con.createStatement();
//                        ResultSet rs = stmt.executeQuery(query);
//
//                        while (rs.next()) {
//
//                            getdate = rs.getString("dt");
//                            getdetail = rs.getString("details");
//                            System.out.println(getdate+" *************************"+getdetail);
//                        }
//                    }
                    String query = "insert into todo (dt,details,important,personal)values(?,?,?,?)";


                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, detail);
                    stmt.setString(2, detail1);
                    stmt.setString(3,event);
                    stmt.setString(4,event1);
                    int set = stmt.executeUpdate();
                    if (set > 0) {
                        JOptionPane.showMessageDialog(null, "Event Added successfull");
                    } else {
                        JOptionPane.showMessageDialog(null, "Insert valid data");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };
        ActionListener at=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "oviya1002");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                detail = text.getText();
                detail1 = text1.getText();
                event = importantc.getText();
                event1 = personalc.getText();
//                          System.out.println(event+" "+event1);
                try {

                    if (importantc.isSelected()==true){
                        System.out.println("selected f1111gfd");
                        event = "important";

                    }else {
                        System.out.println("selected fgfd");
                        event = "null";
                    }
                    if (personalc.isSelected()==true){
                        System.out.println("selected dddd");
                        event1 = "personal";
                    }else {
                        System.out.println("selected fddgfd");
                        event1 = "null";
                    }
                    query = "UPDATE `todo` SET `dt` =?  ,`details`=? ,`important`=?, `personal`=? "+"WHERE `id` = ?";

//                              System.out.println(query);
//                              System.out.println(number);
                    PreparedStatement updated = con.prepareStatement(query);
                    updated.setString(1,detail);
                    updated.setString(2,detail1);
                    updated.setString(3,event);
                    updated.setString(4,event1);
                    updated.setInt(5,number);

//                                updated.setString(5,number);
                    int set = updated.executeUpdate();
                    if (set > 0) {
                        JOptionPane.showMessageDialog(null, "updated successfull");
                    } else {
                        JOptionPane.showMessageDialog(null, "insert valid data");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }




            }
        };
        if(counter==1)
        {
            System.out.println("*************"+counter);
            b1.addActionListener(at);
        }

        if(counter==99)
        {

            System.out.println("the counter 99 part"+counter);
            b1.addActionListener(at1);
        }
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dateTimePicker.dothis();
                text.setText(result);
            }
        });
    }
}