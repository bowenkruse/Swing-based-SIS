package GUI_PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main implements Runnable {

    public void run() {
        // write your code here
        //Creating login menu
        //frame//
        JFrame loginMenu = new JFrame("Login Menu");
        loginMenu.setLocationByPlatform(true);
        loginMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginMenu.setSize(250,175);
        loginMenu.getContentPane().setLayout(new BorderLayout());
        //pane//
        JPanel loginOptions = new JPanel();
        BoxLayout options = new BoxLayout(loginOptions,BoxLayout.Y_AXIS);
        loginOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginOptions.setLayout(options);
        loginOptions.setBorder(BorderFactory.createTitledBorder("Login Page"));
        //User&&Pass -> as popup//
        JFrame userPassFrame = new JFrame(" Please login ");
        userPassFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userPassFrame.setSize(275, 150);
        userPassFrame.setResizable(false);
        userPassFrame.setLocationByPlatform(true);

        JPanel panelOrder = new JPanel(new BorderLayout(5,5));

        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("User Name", SwingConstants.RIGHT));
        labels.add(new JLabel("Password", SwingConstants.RIGHT));
        panelOrder.add(labels, BorderLayout.WEST);
        JPanel userPassPanel = new JPanel(new GridLayout(0,1,2,2));
        //user and pass objects -->
        JTextField username = new JTextField("");
        userPassPanel.add(username);
        JPasswordField password = new JPasswordField();
        userPassPanel.add(password);

        panelOrder.add(userPassPanel, BorderLayout.CENTER);
        JButton confirmButton = new JButton("Confirm");
        panelOrder.add(confirmButton,BorderLayout.PAGE_END);

        //////buttons//////
        //admin
        JButton adminButton = new JButton("Admin");
        adminButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //write actions here
                        confirmButton.addActionListener(
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        //write actions here
                                        String usernameCheck = username.getText();
                                        String passwordCheck = String.valueOf(password.getPassword());
                                        //call check function
                                        try {
                                            if (checkPass(usernameCheck,passwordCheck,1) == true){
                                                //you can let the user in from here
                                                userPassFrame.setVisible(false);
                                                loginMenu.setVisible(false);
                                                JFrame optBox = new JFrame("Options");
                                                optBox.setLayout(new GridLayout(2,0));
                                                JButton instructorS = new JButton("Instructor");

                                                JButton studentS = new JButton("Student");
                                                optBox.add(instructorS);
                                                optBox.add(studentS);
                                                instructorS.addActionListener(
                                                        new ActionListener() {
                                                            public void actionPerformed(ActionEvent e) {
                                                                AdminMod iM1 = new AdminMod();
                                                                iM1.start();
                                                                optBox.setVisible(false);
                                                            }
                                                        });
                                                studentS.addActionListener(
                                                        new ActionListener() {
                                                            public void actionPerformed(ActionEvent e) {
                                                                StudentInterface sM1 = new StudentInterface(usernameCheck);
                                                                sM1.start(usernameCheck);
                                                                optBox.setVisible(false);
                                                            }
                                                        });

                                                optBox.setSize(250, 150);
                                                optBox.setVisible(true);
                                            }

                                            else{
                                                JDialog dialog_box = new JDialog(userPassFrame, "404");
                                                JLabel invalid = new JLabel("Invalid inputs");
                                                dialog_box.add(invalid);
                                                dialog_box.setSize(100, 100);
                                                dialog_box.setVisible(true);
                                            }
                                        } catch (FileNotFoundException fileNotFoundException) {
                                            fileNotFoundException.printStackTrace();
                                        }

                                    }
                                });
                        userPassFrame.getContentPane().add(panelOrder);
                        userPassFrame.setVisible(true);

                    }
                });
        //instructor
        JButton instructorButton = new JButton("Instructor");
        instructorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructorButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //write actions here

                        confirmButton.addActionListener(
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        //write actions here

                                        String usernameCheck = username.getText();
                                        String passwordCheck = String.valueOf(password.getPassword());
                                        //call check function
                                        try {
                                            if (checkPass(usernameCheck,passwordCheck,2) == true){
                                                //you can let the user in from here
                                                InstructorInterface i1 = new InstructorInterface(usernameCheck);
                                                //i1.setID_for_File(usernameCheck);
                                                loginMenu.setVisible(false);
                                                userPassFrame.setVisible(false);
                                                i1.start(usernameCheck);
                                            }else{
                                                // invalid popup screen
                                                JDialog dialog_box = new JDialog(userPassFrame, "404");
                                                JLabel invalid = new JLabel("Invalid inputs");
                                                dialog_box.add(invalid);
                                                dialog_box.setSize(100, 100);
                                                dialog_box.setVisible(true);
                                            }
                                        } catch (FileNotFoundException fileNotFoundException) {
                                            fileNotFoundException.printStackTrace();
                                        }

                                    }
                                });
                        userPassFrame.getContentPane().add(panelOrder);
                        userPassFrame.setVisible(true);



                    }
                });
        //student
        JButton studentButton = new JButton("Student");
        studentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //write actions here

                        confirmButton.addActionListener(
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        //write actions here
                                        String usernameCheck = username.getText();
                                        String passwordCheck = String.valueOf(password.getPassword());
                                        //call check function
                                        try {
                                            if (checkPass(usernameCheck,passwordCheck,3) == true){
                                                //you can let the user in from here
                                                loginMenu.setVisible(false);
                                                StudentInterface s1 = new StudentInterface(usernameCheck);
                                                //s1.setID_for_File(usernameCheck);
                                                userPassFrame.setVisible(false);
                                                s1.start(usernameCheck);
                                            }

                                            else{
                                                // invalid popup screen
                                                JDialog dialog_box = new JDialog(userPassFrame, "404");
                                                JLabel invalid = new JLabel("Invalid inputs");
                                                dialog_box.add(invalid);
                                                dialog_box.setSize(100, 100);
                                                dialog_box.setVisible(true);
                                            }
                                        } catch (FileNotFoundException fileNotFoundException) {
                                            fileNotFoundException.printStackTrace();
                                        }

                                    }
                                });
                        userPassFrame.getContentPane().add(panelOrder);
                        userPassFrame.setVisible(true);
                        userPassFrame.setVisible(true);
                    }
                });
        //Exit
        JButton exitButton = new JButton("Exit !");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //write actions here
                        System.exit(0);
                    }
                });
        //////////////////////
        //Add button to pane//
        loginOptions.add(adminButton);
        loginOptions.add(instructorButton);
        loginOptions.add(studentButton);
        loginOptions.add(exitButton);
        //add pane to frame//
        loginMenu.getContentPane().add(loginOptions,BorderLayout.CENTER);
        //setFrame visible//
        loginMenu.setVisible(true);

    }



    public static void main(String[] args) {

        Main m1 =new Main();
        Thread t1 = new Thread(m1);
        t1.start();
    }

    public static boolean checkPass(String Id, String pass, int fileOpt) throws FileNotFoundException {
        Scanner sc;
        if (fileOpt == 1) {
            //search in admin
            ArrayList<String> userA=new ArrayList<String>();
            ArrayList<String> passA=new ArrayList<String>();

            File adminUser = new File("/home/faskyll/admin.txt");
            sc = new Scanner(new FileReader(adminUser));
            while (sc.hasNextLine()){
                userA.add(sc.next());
                passA.add(sc.next());

            }

            for (int i = 0; i < userA.size(); i++){
                if (userA.get(i).equals(Id) && passA.get(i).equals(pass)){
                    return true;
                }else{
                    return false;
                }
            }


        } else if (fileOpt == 2) {
            //search in instructor

            ArrayList<String> userA=new ArrayList<String>();
            ArrayList<String> passA=new ArrayList<String>();

            File instructerUser = new File("/home/faskyll/instructor.txt");
            sc = new Scanner(new FileReader(instructerUser));
            while (sc.hasNext()){
                userA.add(sc.next());
                passA.add(sc.next());
            }

            for (int i = 0; i < userA.size(); i++){
                if (userA.get(i).equals(Id) && passA.get(i).equals(pass)){
                    return true;
                }
                else{
                    return false;
                }
            }

        }
        else if (fileOpt == 3) {
            //search in student
            ArrayList<String> userA=new ArrayList<String>();
            ArrayList<String> passA=new ArrayList<String>();

            File studentUser = new File("/home/faskyll/student.txt");
            sc = new Scanner(new FileReader(studentUser));
            while (sc.hasNext()){
                userA.add(sc.next());
                passA.add(sc.next());
            }

            for (int i = 0; i < userA.size(); i++){
                if (userA.get(i).equals(Id) && passA.get(i).equals(pass)){
                    return true;
                } 
                else{
                    return false;
                }
            }
            sc.close();
        }
        return false;
    }
}