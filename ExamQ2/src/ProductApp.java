import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class ProductApp extends JFrame {

    private JTextField txtCustomerName;
    private JComboBox<String> cmbProduct;
    private JTextField txtPrice;

    public ProductApp() {

        setTitle("Product App");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MENU BAR
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu toolsMenu = new JMenu("Tools");

        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");

        fileMenu.add(exitItem);

        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);

        setJMenuBar(menuBar);

        // FORM
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        txtCustomerName = new JTextField();

        String[] products = {
                "Cozy Nights Monarch",
                "Restonic Barbados",
                "Sealy Phuket"
        };

        cmbProduct = new JComboBox<>(products);

        txtPrice = new JTextField();

        JButton btnProcess = new JButton("PROCESS");

        panel.add(new JLabel("Customer Name:"));
        panel.add(txtCustomerName);

        panel.add(new JLabel("Select Product:"));
        panel.add(cmbProduct);

        panel.add(new JLabel("Enter Price:"));
        panel.add(txtPrice);

        panel.add(new JLabel(""));
        panel.add(btnProcess);

        add(panel);

        // BUTTON EVENTS
        btnProcess.addActionListener(e -> processSale());

        // MENU EVENTS
        processItem.addActionListener(e -> processSale());

        clearItem.addActionListener(e -> clearForm());

        exitItem.addActionListener(e -> System.exit(0));
    }

    // PROCESS SALE
    private void processSale() {

        String customerName =
                txtCustomerName.getText().trim();

        String productName =
                (String) cmbProduct.getSelectedItem();

        if (customerName.isEmpty() ||
                txtPrice.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter customer name and price."
            );

            return;
        }

        double price;

        try {

            price = Double.parseDouble(txtPrice.getText().trim());

            if (price <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid price."
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a numerical price."
            );

            return;
        }

        // CALCULATIONS
        ProductCalculations calculations =
                new ProductCalculations(price);

        double vat = calculations.GetVat();

        double commission = calculations.GetCommission();

        double total = calculations.GetTotal();

        // DISPLAY RESULTS
        String message =
                "Customer Name: " + customerName +
                        "\nProduct: " + productName +
                        "\nVAT: R " + vat +
                        "\nCOMMISSION: R " + commission +
                        "\nTOTAL: R " + total;

        JOptionPane.showMessageDialog(
                this,
                message,
                "Message",
                JOptionPane.INFORMATION_MESSAGE
        );

        // SAVE INFORMATION
        saveData(
                customerName,
                productName,
                vat,
                commission,
                total
        );
    }

    // SAVE DATA TO TEXT FILE
    private void saveData(
            String customerName,
            String productName,
            double vat,
            double commission,
            double total
    ) {

        try (FileWriter writer =
                     new FileWriter("data.txt", true)) {

            writer.write("DATA LOG\n");

            writer.write(
                    "****************************************\n"
            );

            writer.write(
                    "Customer Name: " + customerName + "\n"
            );

            writer.write(
                    "Product: " + productName + "\n"
            );

            writer.write("VAT: R " + vat + "\n");

            writer.write(
                    "COMMISSION: R " + commission + "\n"
            );

            writer.write("TOTAL: R " + total + "\n");

            writer.write(
                    "****************************************\n\n"
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error saving data: " + e.getMessage()
            );
        }
    }

    // CLEAR FORM
    private void clearForm() {

        txtCustomerName.setText("");

        txtPrice.setText("");

        cmbProduct.setSelectedIndex(0);
    }

    // MAIN METHOD
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ProductApp app = new ProductApp();

            app.setVisible(true);

        });
    }
}
