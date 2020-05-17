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
import java.util.ArrayList;

public class StudentInterface extends JFrame implements ActionListener {

    StudentInterface(String ID) {
        ID_for_File = ID;
        setLayout();
        addComponents();
        decorate();
        addEvents();
        addTableLabels();
        loadCourses();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addComponents() {

        // parent container
        container.add(buttonPanel, BorderLayout.NORTH);
        container.add(infoDisplay, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);

        // table
        infoDisplay.add(studentInfoTable);

        // top panel
        buttonPanel.add(addCourse, BorderLayout.CENTER);
        buttonPanel.add(menuBar, BorderLayout.NORTH);

        // Menu bar
        menuBar.add(FileMenu);
        menuBar.add(Help);
        FileMenu.add(NewCourse);
        FileMenu.add(Save);
        FileMenu.add(Exit);
        Help.add(HowTo);

        // bottom panel
        bottomPanel.add(gpaLabel);
        bottomPanel.add(gpaTextField);
    }

    public void setLayout() {
        container.setLayout(new BorderLayout());
        buttonPanel.setLayout(new BorderLayout());
        infoDisplay.setLayout(new BorderLayout());
        bottomPanel.setLayout(new FlowLayout());
    }

    public void decorate() {
        buttonPanel.setBorder(blackLine);
        infoDisplay.setBorder(blackLine);
        buttonPanel.setBorder(blackLine);
    }

    public void addEvents() {
        NewCourse.addActionListener(this);
        Exit.addActionListener(this);
        addCourse.addActionListener(this);
        HowTo.addActionListener(this);
        Save.addActionListener(this);
    }


    public void addTableLabels() {
        model.setValueAt("  Name: ",0,0);
        model.setValueAt("  Semester:",1,0);
        model.setValueAt("  Courses",2,0);
        model.setValueAt("  ID",0,2);
        model.setValueAt("  Major",0,4);
        model.setValueAt("Spring 2020",1,1);
        model.setValueAt("Course Name",2,1);
        model.setValueAt("Course Number",2,2);
        model.setValueAt("Credits",2,3);
        model.setValueAt("Grade",2,4);

    }

    public void addCourseToTable() {

        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.setDialogTitle("Select a course");

        // Only .txt files may be used
        FileNameExtensionFilter restrictTxt = new FileNameExtensionFilter("Only .txt file","txt");
        jFileChooser.addChoosableFileFilter(restrictTxt);

        // show text from selected text file
        int r = jFileChooser.showOpenDialog(null);

        // When/if user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {

            File file = new File(jFileChooser.getSelectedFile().getAbsolutePath());
            fileToEdit = file;

            StudentCourses.add(file.getName().split(".")[0]);
            try {

                // read basic course info from course file
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String courseInfo = bufferedReader.readLine();
                String[] courseInfoArray = courseInfo.split(";");
                String courseNum = String.valueOf(CourseNum);
                String Name = courseInfoArray[0];
                String courseID = courseInfoArray[1];
                String creditNum = courseInfoArray[2];

                if (CourseNum <= maxCourses) {
                    model.insertRow(model.getRowCount(),new Object[]{courseNum,Name,courseID,creditNum});
                }
                else {
                    Object[] options = {"Ok"};
                    JOptionPane.showOptionDialog(container, "Maximum Number of Courses is 5" +
                                    "", "Error", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(container,e.getMessage());
            }
        }
        studentInfoTable.repaint();
    }

    public void loadCourses() {

        try {
            profileFile = new File("/home/faskyll/" + ID_for_File + ".txt");


            FileReader fileReader = new FileReader(profileFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String[] instructorInfo = bufferedReader.readLine().split(";");
            studentName = instructorInfo[0];
            studentID = instructorInfo[1];

            // todo insert name and id into table

            String[] courses = bufferedReader.readLine().split(";");

            for (int i = 0; i < courses.length; i++) {
                StudentCourses.add(courses[i]);
                CourseNum ++;
                File file = new File("/home/faskyll/" + courses[i] + ".txt");
                fileToEdit = file;

                try {

                    // read basic course info from course file
                    FileReader fileReader2 = new FileReader(file);
                    BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
                    String courseInfo = bufferedReader2.readLine();
                    String[] courseInfoArray = courseInfo.split(";");
                    String courseNum = String.valueOf(CourseNum);
                    String Name = courseInfoArray[0];
                    String courseID = courseInfoArray[1];
                    String creditNum = courseInfoArray[2];

                    if (CourseNum <= maxCourses) {
                        model.insertRow(model.getRowCount(),new Object[]{courseNum,Name,courseID,creditNum});
                    }
                    else {
                        Object[] options = {"Ok"};
                        JOptionPane.showOptionDialog(container, "Maximum Number of Courses is 5" +
                                        "", "Error", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(container,e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editFile() {
        String studentID,studentName, studentGrade;
        studentID = (String) model.getValueAt(0,3);
        studentName = (String) model.getValueAt(0,1);
        studentGrade = (String) model.getValueAt(2,5);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToEdit,true));
            writer.write(studentID + ";" + studentName + ";" + studentGrade);
            writer.close();

            // write to course profile file
            BufferedWriter writer2 = new BufferedWriter(new FileWriter(profileFile));
            writer2.write(studentName + ";" + studentID);
            writer2.newLine();
            writer2.write(StudentCourses.get(0));
            for (int i = 1; i < StudentCourses.size(); i ++) {
                writer2.write(";" + StudentCourses.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setID_for_File(String ID) {
        ID_for_File = ID;
    }

    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == NewCourse) {
            rowCounter ++;
            CourseNum ++;
            addCourseToTable();
        }

        else if (actionEvent.getSource() == Exit) {
            System.exit(0);
        }

        // 'How To' Action Event
        else if (actionEvent.getSource() == HowTo) {
            Object[] options = {"Ok"};
            JOptionPane.showOptionDialog(container,"Select Course From" +
                            " Listed Options and press Ok button ","How To",JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        }

        else if (actionEvent.getSource() == addCourse) {
            rowCounter ++;
            CourseNum ++;
            addCourseToTable();
        }

        else if (actionEvent.getSource() == Save) {
            editFile();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // instructor and course information
    String studentID;
    String studentName;
    ArrayList<String> StudentCourses = new ArrayList<String>();


    // Student ID
    String ID_for_File;

    // File to edit
    File fileToEdit;
    File profileFile;

    // parent container
    Container container = getContentPane();

    // button panel
    JPanel buttonPanel = new JPanel();

    // add course components
    JButton addCourse = new JButton("Add New Course");

    // general use border
    Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

    // 'File' JMenu and its JMenuItems
    JMenuBar menuBar = new JMenuBar();
    JMenu FileMenu = new JMenu("File");
    JMenuItem NewCourse = new JMenuItem("Add New Course");
    JMenuItem Exit = new JMenuItem("Exit");
    JMenuItem Save = new JMenuItem("Save Changes");

    // 'Help' JMenu and its JMenuItems
    JMenu Help = new JMenu("Help");
    JMenuItem HowTo = new JMenuItem("How To");

    // Table of student info
    DefaultTableModel model = new DefaultTableModel(3,6);
    JTable studentInfoTable = new JTable(model);

    // Container for student information
    JPanel infoDisplay = new JPanel();

    // Maximum classes
    int maxCourses = 5;
    int rowCounter = 2;
    int CourseNum = 0;

    // bottom panel
    JPanel bottomPanel = new JPanel();
    JLabel gpaLabel = new JLabel("GPA: ");
    JTextField gpaTextField = new JTextField(5);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void start(String ID) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StudentInterface Frame = new StudentInterface(ID);
                Frame.setTitle("Student Portal");
                Frame.setSize(600,300);

                Frame.setVisible(true);
            }
        });
    }
}
