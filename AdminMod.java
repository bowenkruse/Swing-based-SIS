package GUI_PROJECT;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class AdminMod extends JFrame implements ActionListener {
    AdminMod() {
        setLayout();
        addComponents();
        decorate();
        addEvents();
        addTableLabels();

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void addEvents() {
        addCourse.addActionListener(this);
        //changeGrade.addActionListener(this);
        NewCourse.addActionListener(this);
        Save.addActionListener(this);
        Exit.addActionListener(this);
    }

    private void decorate() {

    }

    private void addTableLabels() {
        model.setValueAt("Name:",0,0);
        model.setValueAt("ID: ",0,2);
        model.setValueAt("Term:",1,0);
        model.setValueAt("Department:",0,4);
        model.setValueAt("Spring 2020",1,1);
        courseModel.setValueAt("Name",0,1);
        courseModel.setValueAt("Course ID",0,2);
    }

    private void setLayout() {

        container.setLayout(new BorderLayout());
        infoDisplay.setLayout(new BorderLayout());
        BottomPanel.setLayout(new FlowLayout());
        courseTablePanel.setLayout(new BorderLayout());
    }

    private void addComponents() {

        container.add(BottomPanel,BorderLayout.SOUTH);
        container.add(infoDisplay,BorderLayout.CENTER);
        container.add(menuBar,BorderLayout.NORTH);
        infoDisplay.add(PersonalLabel,BorderLayout.NORTH);
        infoDisplay.add(PersonalDataTable,BorderLayout.CENTER);
        infoDisplay.add(courseTablePanel,BorderLayout.SOUTH);

        courseTablePanel.add(courseTableLabel,BorderLayout.NORTH);
        courseTablePanel.add(courseDataTable,BorderLayout.CENTER);

        BottomPanel.add(addCourse);
        // BottomPanel.add(changeGrade);

        menuBar.add(FileMenu);
        FileMenu.add(NewCourse);
        FileMenu.add(Save);
        FileMenu.add(Exit);
    }

    public void addCourseToTable() {
        CourseCounter ++;

        if (CourseCounter <= maxCourses) {
            JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.setDialogTitle("Select a course");

            // Only .txt files may be used
            FileNameExtensionFilter restrictTxt = new FileNameExtensionFilter("Only .txt file","txt");
            jFileChooser.addChoosableFileFilter(restrictTxt);

            // show text from selected text file
            int r = jFileChooser.showOpenDialog(null);
            courseDataTable.repaint();

            // When/if user selects a file
            if (r == JFileChooser.APPROVE_OPTION) {
                File file = new File(jFileChooser.getSelectedFile().getAbsolutePath());
                fileToEdit = file;

                try {

                    // read basic course info from course file
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    // read after first line to get students
                    courseInfoSplit = bufferedReader.readLine().split(";");
                    CourseID = courseInfoSplit[0];
                    CourseName = courseInfoSplit[1];
                    courseModel.insertRow(courseModel.getRowCount(),new Object[]{" Course:",CourseID,CourseName});

                    // getting and inserting instructor info into personal info table
                    String[] instructorInfo = bufferedReader.readLine().split(";");
                    InstName = instructorInfo[0];
                    InstID = instructorInfo[1];
                    InstDepartment = instructorInfo[2];
                    model.setValueAt(InstName,0,1);
                    model.setValueAt(InstID,0,3);
                    model.setValueAt(InstDepartment,0,5);

                    // filling student info into button course table
                    String line1;

                    while ((line1 = bufferedReader.readLine()) != null) {
                        String[] studentSplit = line1.split(";");
                        StudID = studentSplit[0];
                        StudName = studentSplit[1];
                        StudGrade = studentSplit[2];
                        courseModel.insertRow(courseModel.getRowCount(),new Object[]{StudID,StudName,StudGrade  });

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(container,e.getMessage());
                }
            }
            courseDataTable.repaint();
        }

        else {
            Object[] options = {"Ok"};
            JOptionPane.showOptionDialog(container,"Maximum Number of Courses is 3" +
                            "","Error", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        }
    }

    public void editFile() {

        String courseID,courseName;
        String instructorName,instructorID,instructorDepartment;
        String studentID,studentName,studentGrade;

        courseName = (String) courseModel.getValueAt(1,1);
        courseID = (String) courseModel.getValueAt(1,2);
        instructorName = (String) model.getValueAt(0,1);
        instructorID = (String) model.getValueAt(0,3);
        instructorDepartment = (String) model.getValueAt(0,5);


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToEdit));
            writer.write(courseName + ";" + courseID);
            writer.newLine();
            writer.write(instructorName + ";" + instructorID + ";" + instructorDepartment);
            writer.newLine();
            writer.write("Student ID;Student Name;Student Grade");
            writer.newLine();
            writer.write("------;------;------");
            writer.newLine();
            for (int i = 2; i < courseModel.getRowCount(); i ++) {
                studentID = (String) courseModel.getValueAt(i,0);
                studentName = (String) courseModel.getValueAt(i,1);
                studentGrade = (String) courseModel.getValueAt(i,2);
                writer.write(studentID + ";" + studentName + ";" + studentGrade);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == addCourse) {
            addCourseToTable();
        }

        //else if (actionEvent.getSource() == changeGrade) {
        //   changeGradeInTable();
        //  }

        else if (actionEvent.getSource() == NewCourse) {
            addCourseToTable();
        }

        else if (actionEvent.getSource() == Save) {
            editFile();
        }

        else if (actionEvent.getSource() == Exit) {
            System.exit(0);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // instructor and course information
    String InstName;
    String InstID;
    String InstDepartment;
    String Term;
    String CourseName;
    String CourseID;

    // student information
    String StudID;
    String StudName;
    String StudGrade;

    // array for course info to be put into table
    String[] courseInfoSplit;

    // miscellaneous
    int CourseCounter = 0;
    int maxCourses = 3;
    String newGrade;
    Object targetID;
    private static Scanner x;
    File fileToEdit;

    // parent container
    Container container = getContentPane();

    // button panel
    JPanel BottomPanel = new JPanel();

    // bottom panel components
    JButton addCourse = new JButton("Add New Course");
    //JButton changeGrade = new JButton("Change Student Grade");


    // general use border
    Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

    // Information display panel
    JPanel infoDisplay = new JPanel();

    // Table of instructor personal info
    JLabel PersonalLabel = new JLabel("Personal Information:");
    DefaultTableModel model = new DefaultTableModel(2,6);
    JTable PersonalDataTable = new JTable(model);

    // table of courses instructor has
    JPanel courseTablePanel = new JPanel();
    JLabel courseTableLabel = new JLabel("Courses: ");
    DefaultTableModel courseModel = new DefaultTableModel(1,6);
    JTable courseDataTable = new JTable(courseModel) {
        // make the cells uneditable
        public boolean editCellAt (int row, int column, java.util.EventObject e) {
            return false;
        }
    };

    // 'File' JMenu and its JMenuItems
    JMenuBar menuBar = new JMenuBar();
    JMenu FileMenu = new JMenu("File");
    JMenuItem NewCourse = new JMenuItem("Add New Course");
    JMenuItem Save = new JMenuItem("Save Changes");
    JMenuItem Exit = new JMenuItem("Exit");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void start(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AdminMod Frame = new AdminMod();
                Frame.setTitle("Administrator Portal");
                Frame.setSize(600,400);

                Frame.setVisible(true);
            }
        });
    }
}
