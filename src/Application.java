import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Collections.frequency;

public class Application extends JFrame implements ActionListener {
    private JLabel label = new JLabel("Would you print the information?");
    private JButton printFButton= new JButton("PrintFirst");
    private JButton printSButton= new JButton("PrintSecond");
    private JButton minButton = new JButton("Find Min");
    private JButton maxButton = new JButton("Find Max");
    private JButton countButton = new JButton("Count");
    private JTextArea textArea = new JTextArea(30,60);
    private JTextArea textResults = new JTextArea(10,60);

    private Container<Automobile> currentCont;

    public static void main(String[] args) {
        JFrame frame = new Application();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400,900);
        frame.setVisible(true);
    }
    public Application(){
        super("Application");
        JPanel panel = new JPanel();//создаю рабочую панель. На нее и будем добавлять компоненты

        //printFButton.addActionListener(this);//добавляю общего лисенера
        printFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"No need to lie to yourself " +
                        " ┬┴┬┴┤( ͡° ͜ʖ├┬┴┬┴","Reminder!",JOptionPane.WARNING_MESSAGE);
            }
        });
        printSButton.addActionListener(this);
        minButton.addActionListener(this);
        maxButton.addActionListener(this);
        countButton.addActionListener(this);

        Box hBox = Box.createHorizontalBox();//Создаю боксы для кнопок
        Box vBox = Box.createVerticalBox();//Создаю боксы для вертикального расположения

        hBox.add(printFButton);//добавляю кнопочки
        hBox.add(printSButton);
        hBox.add(minButton);
        hBox.add(maxButton);
        hBox.add(countButton);

        vBox.add(label);//распологаю все вертикальные составляющие
        vBox.add(textArea);
        vBox.add(hBox);
        vBox.add(textResults);

        panel.add(vBox);//добавляю на панель
        add(panel);//добавляю панель на окно(без этого действия у нас ничего не будет на экране)



    }

    //Здесь я прописываю лисенера
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            //через ифы узнаем, от какой кнопочки получили сигнал
            if(e.getSource() == printFButton){
                textArea.setText("");
                label.setText("Information about the first type:");
                currentCont = new Container();
                File fileBuses = new File("src\\buses.txt");
                readFromFile(fileBuses, currentCont,1);
                Stream stream = currentCont.stream();
                stream.sorted().forEach(x -> textArea.append(x.toString() + "\n"));
            }else if(e.getSource() == printSButton) {
                label.setText("Information about the second type:");
                textArea.setText("");
                currentCont = new Container();
                File fileCars = new File("src\\cars.txt");
                readFromFile(fileCars, currentCont, 2);
                //Здесь я создаю стрим АПИ
                Stream stream = currentCont.stream();
                //Мы выводим отсортированный контейнер!
                stream.sorted().forEach(x -> textArea.append(x.toString() + "\n"));
            }else if(e.getSource() == minButton) {
                if(currentCont != null){
                    textResults.setText("Minimum: "+ currentCont.minimum());
                }
            }else if(e.getSource() == maxButton) {
                if(currentCont != null){
                    textResults.setText("Maximum: "+ currentCont.max());
                }
            }else if(e.getSource() == countButton){
                String result = JOptionPane.showInputDialog(this,"Enter the data",
                        "title",JOptionPane.PLAIN_MESSAGE);
                if(result != null ){
                    Fuel fuel = Fuel.BENZIN;
                    String[] data = result.split(" ");
                    if(data.length >= 4){
                        if(data.length == 4){
                            Material material = Material.COTTON;
                            Car temp = new Car(data[0],data[1],
                                    fuel.getFlueType(data[2]), material.getMaterialType(data[3]));
                            if(textArea.getText(0,3).equals("Car")){
                                textResults.setText(String.valueOf(currentCont.frequency(new Car(data[0],data[1],
                                        fuel.getFlueType(data[2]), material.getMaterialType(data[3])))));
                            }else{
                                JOptionPane.showMessageDialog(this,"Wrong type of automobile",
                                        "Attention!", JOptionPane.WARNING_MESSAGE);
                            }
                        }else if(data.length == 5){
                            if(textArea.getText(0,3).equals("Bus")){
                                textResults.setText(String.valueOf(currentCont.frequency(new Bus(data[0],data[1],
                                        fuel.getFlueType(data[2]), Integer.parseInt(data[3]),Integer.parseInt(data[4])))));
                            }else{
                                JOptionPane.showMessageDialog(this,"Wrong type of automobile",
                                        "Attention!", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(this,"Wrong data!",
                                "Impossible operation", JOptionPane.WARNING_MESSAGE);
                    }
                }

            }
        } catch (EmptyException exception) {
            JOptionPane.showMessageDialog(this,"Your container is empty",
                    "Impossible operation",JOptionPane.ERROR_MESSAGE);
        } catch (IncorrectIntException exception) {
            JOptionPane.showMessageDialog(this,"You can't enter negative number of" +
                    " doors or seats!", "Impossible operation",JOptionPane.ERROR_MESSAGE);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this,"IOEx!", "Impossible operation",
                    JOptionPane.ERROR_MESSAGE);
        } catch (EnumIncorrectException exception) {
            JOptionPane.showMessageDialog(this,"Wrong type!", "Impossible operation",
                    JOptionPane.ERROR_MESSAGE);
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }

    }

    //чтение из файлика
    public static void readFromFile(File fileCars, Container automobile, int type) throws IOException, NoSuchElementException,
            EnumIncorrectException, IncorrectIntException {
        Scanner scanner = new Scanner(fileCars);
        Fuel fuel = Fuel.BENZIN;
        if(type == 1){
            while (scanner.hasNext()) {
                String name = scanner.next();
                String color = scanner.next();
                String fuelTemp = scanner.next();
                fuel = fuel.getFlueType(fuelTemp);
                int places = scanner.nextInt();
                int doors = scanner.nextInt();
                automobile.add(new Bus(name, color, fuel, places, doors));
            }
        }else{
            Material material = Material.LEATHER;
            while(scanner.hasNext()) {
                String name = scanner.next();
                String color = scanner.next();
                String fuelTemp = scanner.next();
                String materialTemp = scanner.next();
                fuel = fuel.getFlueType(fuelTemp);
                material = material.getMaterialType(materialTemp);
                automobile.add(new Car(name, color, fuel, material));
            }
        }
    }
}



/*import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

public class Application extends JFrame implements ActionListener {
    private JLabel label = new JLabel("Would you print the information?");
    private JButton printFButton= new JButton("PrintFirst");
    private JButton printSButton= new JButton("PrintSecond");
    private JButton minButton = new JButton("Find Min");
    private JButton maxButton = new JButton("Find Max");
    private JTextArea textArea = new JTextArea(30,60);
    private JTextArea textResults = new JTextArea(10,60);

    private Container<Automobile> currentCont;

    public static void main(String[] args) {
        JFrame frame = new Application();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400,900);
        frame.setVisible(true);
    }
    public Application(){
        super("Application");
        JPanel panel = new JPanel();//создаю рабочую панель. На нее и будем добавлять компоненты

        printFButton.addActionListener(this);//добавляю общего лисенера
        printSButton.addActionListener(this);
        minButton.addActionListener(this);
        maxButton.addActionListener(this);

        Box hBox = Box.createHorizontalBox();//Создаю боксы для кнопок
        Box vBox = Box.createVerticalBox();//Создаю боксы для вертикального расположения

        hBox.add(printFButton);//добавляю кнопочки
        hBox.add(printSButton);
        hBox.add(minButton);
        hBox.add(maxButton);

        vBox.add(label);//распологаю все вертикальные составляющие
        vBox.add(textArea);
        vBox.add(hBox);
        vBox.add(textResults);

        panel.add(vBox);//добавляю на панель
        add(panel);//добавляю панель на окно(без этого действия у нас ничего не будет на экране)



    }

    //Здесь я прописываю лисенера
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            //через if узнаем, от какой кнопочки получили сигнал
            if(e.getSource() == printFButton){
                textArea.setText("");
                label.setText("Information about the first type:");
                currentCont = new Container();
                File fileBuses = new File("src\\buses.txt");
                readFromFile(fileBuses, currentCont,1);
                Stream stream = currentCont.stream();
                stream.sorted().forEach(x -> textArea.append(x.toString() + "\n"));
            }else if(e.getSource() == printSButton) {
                label.setText("Information about the second type:");
                textArea.setText("");
                currentCont = new Container();
                File fileCars = new File("src\\cars.txt");
                readFromFile(fileCars, currentCont, 2);
                //Здесь я создаю стрим АПИ
                Stream stream = currentCont.stream();
                //Мы выводим отсортированный контейнер!
                stream.sorted().forEach(x -> textArea.append(x.toString() + "\n"));
            }else if(e.getSource() == minButton) {

                if(currentCont != null){
                    textResults.setText("Minimum: "+ currentCont.minimum());
                }
            }else if(e.getSource() == maxButton) {
                if(currentCont != null){
                    textResults.setText("Maximum: "+ currentCont.max());
                }
            }//Магические исключения, которые можешь потестить(Например, очисть файлик с автобусами)
        } catch (EmptyException exception) {
            JOptionPane.showMessageDialog(this,"Your container is empty",
                    "Impossible operation",JOptionPane.ERROR_MESSAGE);
        } catch (IncorrectIntException exception) {
            JOptionPane.showMessageDialog(this,"You can't enter negative number of" +
                    " doors or seats!", "Impossible operation",JOptionPane.ERROR_MESSAGE);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this,"IOEx!", "Impossible operation",
                    JOptionPane.ERROR_MESSAGE);
        } catch (EnumIncorrectException exception) {
            JOptionPane.showMessageDialog(this,"Wrong type!", "Impossible operation",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
    //чтение из файлика
    public static void readFromFile(File fileCars, Container automobile, int type) throws IOException, NoSuchElementException,
            EnumIncorrectException, IncorrectIntException {
        Scanner scanner = new Scanner(fileCars);
        Fuel fuel = Fuel.BENZIN;
        if(type == 1){
            while (scanner.hasNext()) {
                String name = scanner.next();
                String color = scanner.next();
                String fuelTemp = scanner.next();
                fuel = fuel.getType(fuelTemp);
                int places = scanner.nextInt();
                int doors = scanner.nextInt();
                automobile.add(new Bus(name, color, fuel, places, doors));
            }
        }else{
            Material material = Material.LEATHER;
            while(scanner.hasNext()) {
                String name = scanner.next();
                String color = scanner.next();
                String fuelTemp = scanner.next();
                String materialTemp = scanner.next();
                fuel = fuel.getType(fuelTemp);
                material = material.getType(materialTemp);
                automobile.add(new Car(name, color, fuel, material));
            }
        }
    }
}
*/